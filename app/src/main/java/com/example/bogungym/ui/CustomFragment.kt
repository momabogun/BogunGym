package com.example.bogungym.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.adapter.CustomAdapter
import com.example.bogungym.adapter.ExercisesAdapter
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.databinding.FragmentCustomBinding

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObjects

class CustomFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()



    private lateinit var binding: FragmentCustomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.newWorkBTN.setOnClickListener {
            findNavController().navigate(CustomFragmentDirections.actionCustomFragment2ToAddWorkoutFragment())

        }



        val adapter = CustomAdapter(emptyList())
        binding.customRV.adapter = adapter









    }


}

