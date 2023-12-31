package com.example.bogungym.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.databinding.FragmentSupplementsBinding
import com.example.bogungym.databinding.FragmentWorkoutSaveBinding


class WorkoutSaveFragment : Fragment() {


    private lateinit var binding: FragmentWorkoutSaveBinding

    private val viewModel: ExercisesViewModel by activityViewModels()





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutSaveBinding.inflate(inflater, container, false)
        return binding.root


    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {





        val isDarkMode = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES

        if (isDarkMode) {
            binding.imageView5.visibility = View.VISIBLE
            binding.imageView.visibility = View.GONE
        } else {
            binding.imageView.visibility = View.VISIBLE
            binding.imageView5.visibility = View.GONE
        }




       binding.saveBTN.setOnClickListener {
           val name = binding.myWorkET.text.toString()
           if (name == ""){
               Toast.makeText(requireContext(),"Please give name to your workout.", Toast.LENGTH_LONG).show()
               return@setOnClickListener
           }
           val workout = UserWorkout(name)
           viewModel.addWorkout(workout,name)
           findNavController().navigate(WorkoutSaveFragmentDirections.actionWorkoutSaveFragmentToAddWorkoutFragment())
        }








       binding.cancelBTN.setOnClickListener {
            findNavController().navigateUp()
       }







    }
    }
