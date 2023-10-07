package com.example.bogungym.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.MainActivity
import com.example.bogungym.adapter.ChoosingAdapter
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.databinding.FragmentChoosingBinding
import com.example.bogungym.databinding.FragmentExerciseBinding
import com.google.firebase.firestore.DocumentReference
import java.util.Locale




class ChoosingFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()


    private var target: String = ""



    private lateinit var binding: FragmentChoosingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        arguments?.let { it ->
            target = it.getString("target", "")
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



        val mainActivity = activity as MainActivity

        mainActivity.toolbarChange(target.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        })


        val adapter = ChoosingAdapter(emptyList(), this, viewModel)
        binding.chooseRV.adapter = adapter

        viewModel.getExercises(target).observe(viewLifecycleOwner) { exercises ->
            adapter.newData(exercises)
        }


        binding.doneBTN.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                viewModel.updateAllFalse()
                findNavController().navigate(ChoosingFragmentDirections.actionChoosingFragmentToCustomFragment2())
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            builder.setTitle("Save")
            builder.setMessage("Are you sure that you want to save this workout?")
            builder.create().show()



        }


    }


}
