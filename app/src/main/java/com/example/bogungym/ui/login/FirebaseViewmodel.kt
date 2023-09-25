package com.example.bogungym.ui.login

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogungym.data.model.FirebaseProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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

    fun signUp(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    // Senden der Email-Bestätigung
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
                        // Wenn Email noch nicht bestätigt wird muss User wieder ausgeloggt werden
                        Log.e("UNVERIFIED", "Email address has not been verified")
                        signOut()
                    }
                } else {
                    Log.e("LOGIN", "${loginResult.exception}")
                }
            }
    }


    fun uploadImage(uri: Uri) {
        // Erstellen einer Referenz und des Upload Tasks
        val imageRef = storageRef.child("images/${user.value?.uid}/profilePic")
        val uploadTask = imageRef.putFile(uri)

        // Ausführen des UploadTasks
        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    // Wenn Upload erfolgreich, speichern der Bild-Url im User-Profil
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