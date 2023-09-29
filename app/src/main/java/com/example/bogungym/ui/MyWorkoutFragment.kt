package com.example.bogungym.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.adapter.MyWorkoutAdapter
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.databinding.FragmentExerciseBinding
import com.example.bogungym.databinding.FragmentMyWorkoutBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObjects

class MyWorkoutFragment : Fragment() {

    private val viewModel: ExercisesViewModel by activityViewModels()
    private lateinit var binding: FragmentMyWorkoutBinding


    private lateinit var user: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MyWorkoutAdapter(emptyList(),viewModel,this)
        binding.myWorkoutRV.adapter = adapter
        viewModel.getAllPicked().observe(viewLifecycleOwner){
            adapter.newData(it)
        }




    }
}