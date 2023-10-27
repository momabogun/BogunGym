package com.example.bogungym.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.adapter.ExercisesAdapter
import com.example.bogungym.adapter.SupplementsAdapter
import com.example.bogungym.adapter.WorkoutAdapter
import com.example.bogungym.data.Datasource
import com.example.bogungym.databinding.FragmentExerciseBinding
import com.example.bogungym.databinding.FragmentWorkoutBinding

class WorkoutFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        val workouts = Datasource().loadWorkouts()


        val recyclerView = binding.workoutMainRV

        recyclerView.adapter = WorkoutAdapter(workouts,requireContext())







    }
}