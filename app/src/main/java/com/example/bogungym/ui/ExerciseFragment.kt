package com.example.bogungym.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.transform.RoundedCornersTransformation
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.adapter.ExercisesAdapter
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.databinding.FragmentExerciseBinding

class ExerciseFragment : Fragment() {

    private val viewModel: ExercisesViewModel by activityViewModels()
    private lateinit var binding: FragmentExerciseBinding

    private var target: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        arguments?.let { it ->
            target = it.getString("target", "")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ExercisesAdapter(emptyList())
        binding.exerciseRV.adapter = adapter


        viewModel.getExercises(target).observe(viewLifecycleOwner) { exercises ->
            adapter.newData(exercises)

        }
    }
}

