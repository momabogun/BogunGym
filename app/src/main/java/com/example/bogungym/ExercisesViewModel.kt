package com.example.bogungym

import android.app.Application
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.load
import com.example.bogungym.data.AppRepository
import com.example.bogungym.data.Datasource
import com.example.bogungym.data.db.getDatabase
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.FirebaseProfile
import com.example.bogungym.data.model.Supplements
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.data.remote.ExercisesApi
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRegistrar
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = AppRepository(ExercisesApi, database)

    private var supplementsList = Datasource().loadSupplements()

    //EXERCISES

    val exercises = repository.exercisesList


    val pickedExercises = repository.pickedList

    private var _supplements = MutableLiveData(supplementsList)
    val supplements: LiveData<List<Supplements>>
        get() = _supplements


    fun userInput(text: String) {

        if (text.isEmpty()) {
            _supplements.value = supplementsList
            return
        }


        supplementsList.filter {
            it.name.lowercase().contains(text)
        }.also {
            _supplements.value = it
        }
    }


    fun getExercises(target: String): LiveData<List<Exercises>> =
        repository.getExerciseByMuscle(target)


    fun getExerciseByID(id: String): LiveData<Exercises> = repository.getExerciseByID(id)




    fun loadExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getExercises()
        }
    }


    fun updatePick(liked: Boolean, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePick(liked, id)
        }
    }


    //FIREBASE


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()


    private var _user = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val user: LiveData<FirebaseUser?>
        get() = _user


    lateinit var profileRef: DocumentReference

    private val storageRef = firebaseStorage.reference


    init {
        if (firebaseAuth.currentUser != null) {
            setupUserEnv()
        }
    }


    fun deleteProfileCollection() {
        firebaseAuth.currentUser!!.delete()
        firebaseAuth.signOut()
        _user.value = firebaseAuth.currentUser

    }

    //Google login

//    fun updateUI(account: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//            if (it.isSuccessful) {
//                val name = account.displayName!!
//                val id = firebaseAuth.uid!!
//                val email = account.email!!
//                _user.value = firebaseAuth.currentUser
//            }
//        }
//
//    }


    fun setupUserEnv() {
        _user.value = firebaseAuth.currentUser

        profileRef = fireStore.collection("Profile").document(firebaseAuth.currentUser?.uid!!)

    }


    fun deleteWorkout(name: String) {
        fireStore
            .collection("Profile")
            .document(firebaseAuth.currentUser?.uid!!)
            .collection("workouts").document(name).delete()
    }

    var workoutName: String = ""


    fun addWorkout(workout: UserWorkout, name: String) {
        workoutName = name
        val workoutReference =
            fireStore
                .collection("Profile")
                .document(firebaseAuth.currentUser?.uid!!)
                .collection("workouts").document(name)
        workoutReference.set(workout)


    }

    fun getWorkoutsReference(): CollectionReference {
        return fireStore
            .collection("Profile")
            .document(firebaseAuth.currentUser?.uid!!)
            .collection("workouts")
    }


    fun signUp(email: String, password: String) {


        if (email.isEmpty() || password.isEmpty()) {

            val toast = Toast.makeText(
                getApplication(),
                "Please enter your email and password!",
                Toast.LENGTH_LONG
            )
            toast.show()
            return

        } else if (password.length < 6) {
            val toast = Toast.makeText(
                getApplication(),
                "Your Password must have at least 6 characters!",
                Toast.LENGTH_LONG
            )
            toast.show()
            return


        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    firebaseAuth.currentUser?.sendEmailVerification()
                    setupUserEnv()
                    setupNewProfile()
                    signOut()
                    val toast = Toast.makeText(
                        getApplication(),
                        "Please verify your Password!",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                } else {
                    if (authResult.exception is FirebaseAuthUserCollisionException) {
                        val toast = Toast.makeText(
                            getApplication(),
                            "The Email is already in use!",
                            Toast.LENGTH_LONG
                        )
                        toast.show()

                    }
                }
            }


    }

    fun sendPasswordRecovery(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
        }
    }


    private fun setupNewProfile() {
        profileRef.set(FirebaseProfile())
    }

    fun updateProfile(profile: FirebaseProfile) {
        viewModelScope.launch {
            val existingProfile = profileRef.get().await()
            val existingProfilePicture = existingProfile.getString("profilePicture")
            val updatedProfile = profile.copy(profilePicture = existingProfilePicture!!)

            profileRef.set(updatedProfile)

        }

    }


    val listOfExercises = mutableListOf<String>()


    fun updateAllFalse() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAllFalse()
        }

        listOfExercises.clear()

    }

    fun signOut() {
        updateAllFalse()
        firebaseAuth.signOut()
        _user.value = firebaseAuth.currentUser
    }


    fun findExercisesInWorkout(name: String) {
        fireStore.collection("Profile").document(firebaseAuth.currentUser!!.uid)
            .collection("workouts").document(name)
            .get().addOnSuccessListener {
                val workout = it.toObject(UserWorkout::class.java)
                if (workout?.exercisesPicked != null) {
                    for (exercise in workout.exercisesPicked) {
                        updatePick(true, exercise)
                    }
                }
            }
    }


    fun login(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                getApplication(),
                "Please enter your email and password!",
                Toast.LENGTH_LONG
            ).show()
            return


        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginResult ->
                if (loginResult.isSuccessful) {
                    if (firebaseAuth.currentUser!!.isEmailVerified) {
                        setupUserEnv()

                    } else {


                        when (loginResult.exception) {
                            is FirebaseAuthInvalidUserException -> {
                                Toast.makeText(
                                    getApplication(),
                                    "Email not registrated.Please verify your Email!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            is FirebaseAuthInvalidCredentialsException -> {
                                Toast.makeText(
                                    getApplication(),
                                    "Wrong Password. Please try again!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    getApplication(),
                                    "Fehler bei der Anmeldung. Bitte versuchen Sie es erneut.",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }
                    }

                }
            }
    }


    fun setProfile(image: ShapeableImageView) {
        profileRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {

                val updatedProfile = snapshot.toObject(FirebaseProfile::class.java)
                if (updatedProfile != null) {
                    image.load(updatedProfile.profilePicture)

                } else {
                    Log.e("Snapshot Error", "$error")
                }
            }
        }
    }



        fun uploadImage(uri: Uri) {
            val imageRef = storageRef.child("images/${user.value?.uid}/profilePic")
            val uploadTask = imageRef.putFile(uri)


            uploadTask.addOnCompleteListener {
                imageRef.downloadUrl.addOnCompleteListener {
                    if (it.isSuccessful) {
                        setImage(it.result)
                    }
                }
            }
        }

        private fun setImage(uri: Uri) {
            profileRef.update("profilePicture", uri.toString()).addOnFailureListener {
                Log.w("ERROR", "Error writing document: $it")
            }
        }


        fun addPickedExercise(id: String) {
            listOfExercises.add(id)
            fireStore.collection("Profile").document(firebaseAuth.currentUser!!.uid)
                .collection("workouts").document(workoutName)
                .update("exercisesPicked", listOfExercises)
        }

        fun removePickedExercise(id: String) {
            listOfExercises.remove(id)
            fireStore.collection("Profile").document(firebaseAuth.currentUser!!.uid)
                .collection("workouts").document(workoutName)
                .update("exercisesPicked", listOfExercises)
        }


    }





