package com.example.bogungym.ui.login

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogungym.data.Datasource
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.FirebaseProfile
import com.example.bogungym.data.model.UserWorkout
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()


    private var _user = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val user: LiveData<FirebaseUser?>
        get() = _user


    var workouts: MutableLiveData<List<UserWorkout>> = MutableLiveData<List<UserWorkout>>()


    lateinit var profileRef: DocumentReference

    private val storageRef = firebaseStorage.reference


    init {
        if (firebaseAuth.currentUser != null) {
            setupUserEnv()
        }
    }


    fun getWorkoutsReference(): CollectionReference {
        return fireStore
            .collection("Profile")
            .document(firebaseAuth.currentUser?.uid!!)
            .collection("workouts")
    }

    fun addWorkoutToUser(workout: UserWorkout) {
        fireStore
            .collection("Profile")
            .document(firebaseAuth.currentUser?.uid!!)
            .collection("workouts")
            .add(workout)

    }


    fun addExercisesToWorkout(exercise: Exercises, workoutIdentifier: String) {
        fireStore
            .collection("workouts")
            .document()
            .collection("userWorkouts")
            .document(workoutIdentifier)
            .collection("exercises")
            .add(exercise)
    }


    fun setupUserEnv() {
        _user.value = firebaseAuth.currentUser

        profileRef = fireStore.collection("Profile").document(firebaseAuth.currentUser?.uid!!)

    }

    fun signUp(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    firebaseAuth.currentUser?.sendEmailVerification()
                    setupUserEnv()
                    setupNewProfile()
                    signOut()
                } else {
                    Log.e("REGISTER", "${authResult.exception}")
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


    fun signOut() {
        firebaseAuth.signOut()
        _user.value = firebaseAuth.currentUser
    }


    fun login(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginResult ->
                if (loginResult.isSuccessful) {
                    if (firebaseAuth.currentUser!!.isEmailVerified) {
                        setupUserEnv()
                    } else {
                        Log.e("UNVERIFIED", "Email address has not been verified")
                        signOut()
                    }
                } else {
                    Log.e("LOGIN", "${loginResult.exception}")
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


}