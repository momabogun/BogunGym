package com.example.bogungym.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutSaveBinding.inflate(inflater, container, false)
        return binding.root


    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


//        fun insertWorkout(){
//
//            val name = binding.myWorkET.text.toString()
//            var list: List<Exercises> = listOf()
//            viewModel.pickedExercises.observe(viewLifecycleOwner){exercises->
//                if (exercises != null) {
//                    list = exercises
//                }
//            }
//
//            viewModel.insertWorkout(UserWorkout(0,name,list))
//        }
//
//
//        binding.saveBTN.setOnClickListener {
//            insertWorkout()
//            findNavController().navigate(WorkoutSaveFragmentDirections.actionWorkoutSaveFragmentToHomeFragment())
//        }
//
//        binding.cancelBTN.setOnClickListener {
//            findNavController().navigate(WorkoutSaveFragmentDirections.actionWorkoutSaveFragmentToHomeFragment())
//        }







    }
    }
