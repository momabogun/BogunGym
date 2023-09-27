package com.example.bogungym.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bogungym.adapter.ChoosingAdapter
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.databinding.FragmentChoosingBinding
import com.example.bogungym.databinding.FragmentExerciseBinding
import com.example.bogungym.ui.login.FirebaseViewModel
import com.google.firebase.firestore.DocumentReference


class ChoosingFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()
    private val firebaseViewModel: FirebaseViewModel by activityViewModels()

    private lateinit var workoutIdentifier: String


    private var target: String = ""


    private lateinit var binding: FragmentChoosingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        arguments?.let { it ->
            target = it.getString("target", "")
            workoutIdentifier = it.getString("workoutIdentifier", "")

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoosingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val adapter = ChoosingAdapter(emptyList(),this,viewModel)
        binding.chooseRV.adapter = adapter

        viewModel.getExercises(target).observe(viewLifecycleOwner) { exercises ->
            adapter.newData(exercises)

        }

        binding.doneBTN.setOnClickListener {
            val id : String = "56665656"
            val name: String = "bla bla"
            val gifUrl: String = ""
            val equipment: String = "dadasda"
            val target: String = "dasdasda"
            firebaseViewModel.addExercisesToWorkout(Exercises(id,name,gifUrl,equipment,target,false),workoutIdentifier)

        }




    }



    }
