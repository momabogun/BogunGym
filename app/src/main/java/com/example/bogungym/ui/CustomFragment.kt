package com.example.bogungym.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.bogungym.adapter.CustomAdapter
import com.example.bogungym.data.model.Days
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.databinding.FragmentCustomBinding
import java.lang.NumberFormatException

class CustomFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()


    private lateinit var binding: FragmentCustomBinding




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
            binding.newWorkBTN.visibility = View.GONE
            binding.customRV.visibility = View.GONE
            binding.daysTV.visibility = View.VISIBLE
            binding.numberET.visibility = View.VISIBLE
            binding.myWorkET.visibility = View.VISIBLE
            binding.nameTV.visibility = View.VISIBLE
            binding.cancelBTN.visibility = View.VISIBLE
            binding.saveBTN.visibility = View.VISIBLE
            binding.divider.visibility = View.GONE

        }
        binding.cancelBTN.setOnClickListener {
            binding.newWorkBTN.visibility = View.VISIBLE
            binding.customRV.visibility = View.VISIBLE
            binding.daysTV.visibility = View.GONE
            binding.numberET.visibility = View.GONE
            binding.myWorkET.visibility = View.GONE
            binding.nameTV.visibility = View.GONE
            binding.cancelBTN.visibility = View.GONE
            binding.saveBTN.visibility = View.GONE
            binding.divider.visibility = View.VISIBLE
        }





        fun inputCheck(name: String, number: String): Boolean {
            return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(number) )

        }


        fun insertWorkoutToDatabase() {
           val name = binding.myWorkET.text.toString()
            val numberDays = binding.numberET.text.toString()


//
//
//                val number = numberDays.toInt()
//                val emptyListsDays = ArrayList<List<Days>>(number)
//
//                for (i in 0 until number){
//
//                    emptyListsDays.add(emptyList())
//                }
//

            if (inputCheck(name,numberDays)){

                val workout = UserWorkout(0,name,numberDays.toInt())
                viewModel.insertWorkout(workout)
                Toast.makeText(requireContext(),"Successfully added!", Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(requireContext(),"Please fill out all fields", Toast.LENGTH_LONG).show()
            }
        }


        binding.saveBTN.setOnClickListener {
            insertWorkoutToDatabase()
            val numberDays = binding.numberET.text.toString()

            try {
                val number = numberDays.toInt()
                val emptyListsDays = ArrayList<List<Days>>(number)

                for (i in 0 until number){

                    emptyListsDays.add(emptyList())
                }


            } catch (e: NumberFormatException){

                Toast.makeText(requireContext(), "Invalid input. Please enter a valid number.", Toast.LENGTH_LONG).show()
            }

            binding.newWorkBTN.visibility = View.VISIBLE
            binding.customRV.visibility = View.VISIBLE
            binding.daysTV.visibility = View.GONE
            binding.numberET.visibility = View.GONE
            binding.myWorkET.visibility = View.GONE
            binding.nameTV.visibility = View.GONE
            binding.cancelBTN.visibility = View.GONE
            binding.saveBTN.visibility = View.GONE
            binding.divider.visibility = View.VISIBLE
        }

        val adapter = CustomAdapter(emptyList(),viewModel)
        binding.customRV.adapter = adapter

        viewModel.workouts.observe(viewLifecycleOwner){workouts->
            adapter.newData(workouts)
        }

    }








    }
