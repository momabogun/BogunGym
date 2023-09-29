package com.example.bogungym

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bogungym.data.AppRepository
import com.example.bogungym.data.db.getDatabase
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.FirebaseProfile
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.data.remote.ExercisesApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRegistrar
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {

    //EXERCISES
    private val database = getDatabase(application)
    private val repository = AppRepository(ExercisesApi, database)


    val exercises = repository.exercisesList


    val pickedExercises = repository.pickedList





    fun getExercises(target: String): LiveData<List<Exercises>> =
        repository.getExerciseByMuscle(target)


    fun getExerciseByID(id: String): LiveData<Exercises> = repository.getExerciseByID(id)


    init {
        loadExercises()
    }


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


    fun setupUserEnv() {
        _user.value = firebaseAuth.currentUser

        profileRef = fireStore.collection("Profile").document(firebaseAuth.currentUser?.uid!!)

    }

    var workoutName:String = ""


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
        profileRef.set(profile)
    }

    val listOfExercises = mutableListOf<String>()


    fun updateAllFalse(){
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















    fun findExercisesInWorkout(name: String){
        fireStore.collection("Profile").document(firebaseAuth.currentUser!!.uid).collection("workouts").document(name)
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


    // Funktion um Url zu neue hochgeladenem Bild im Firestore upzudaten
    private fun setImage(uri: Uri) {
        profileRef.update("profilePicture", uri.toString()).addOnFailureListener {
            Log.w("ERROR", "Error writing document: $it")
        }
    }




    fun addPickedExercise(id: String) {
        listOfExercises.add(id)
        fireStore.collection("Profile").document(firebaseAuth.currentUser!!.uid).collection("workouts").document(workoutName)
            .update("exercisesPicked", listOfExercises)
    }

    fun removePickedExercise(id: String) {
        listOfExercises.remove(id)
        fireStore.collection("Profile").document(firebaseAuth.currentUser!!.uid).collection("workouts").document(workoutName)
            .update("exercisesPicked", listOfExercises)
    }


}




