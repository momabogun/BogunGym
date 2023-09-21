package com.example.bogungym.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogungym.data.model.FirebaseProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseViewModel: ViewModel() {

    val firebaseAuth = FirebaseAuth.getInstance()
    val fireStore = FirebaseFirestore.getInstance()


    private val _user: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val user: LiveData<FirebaseUser?>
        get() = _user


    lateinit var profileRef : DocumentReference
    init {
        setupUserEnv()
    }
    fun setupUserEnv(){
        _user.value = firebaseAuth.currentUser

        firebaseAuth.currentUser?.let {

            profileRef = fireStore.collection("Profile").document(firebaseAuth.currentUser!!.uid)



        }
    }

    fun signUp(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            setupUserEnv()
            val profile = FirebaseProfile()
            profileRef.set(profile)
        }


    }

    fun signOut(){
        firebaseAuth.signOut()
        _user.value = firebaseAuth.currentUser
    }

    fun login(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            setupUserEnv()
        }
    }










}