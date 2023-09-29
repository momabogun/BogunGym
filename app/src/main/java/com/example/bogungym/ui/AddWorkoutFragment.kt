package com.example.bogungym.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.adapter.AddWorkoutAdapter
import com.example.bogungym.adapter.CustomAdapter
import com.example.bogungym.adapter.GymAdapter
import com.example.bogungym.data.Datasource
import com.example.bogungym.databinding.FragmentAddWorkoutBinding
import com.example.bogungym.databinding.FragmentCustomBinding

class AddWorkoutFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()


    private lateinit var binding: FragmentAddWorkoutBinding

    private var wID: Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        arguments?.let { it ->
            wID = it.getLong("wID")

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bodyParts = Datasource().loadBodyParts()


        val recyclerView = binding.addWorkoutRV

        recyclerView.adapter = AddWorkoutAdapter(bodyParts)


    }




}