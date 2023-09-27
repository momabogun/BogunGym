package com.example.bogungym.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.adapter.CustomAdapter
import com.example.bogungym.adapter.ExercisesAdapter
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.databinding.FragmentCustomBinding
import com.example.bogungym.ui.login.FirebaseViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObjects

class CustomFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()

    private val fireViewModel: FirebaseViewModel by activityViewModels()

    private lateinit var collectionReference: CollectionReference
    private lateinit var user: FirebaseUser


    private lateinit var binding: FragmentCustomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionReference = fireViewModel.getWorkoutsReference()
        user = fireViewModel.user.value!!
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
            binding.newWorkBTN.visibility = View.GONE
            binding.customRV.visibility = View.GONE
            binding.myWorkET.visibility = View.VISIBLE
            binding.nameTV.visibility = View.VISIBLE
            binding.cancelBTN.visibility = View.VISIBLE
            binding.saveBTN.visibility = View.VISIBLE
            binding.divider.visibility = View.GONE

        }
        binding.cancelBTN.setOnClickListener {
            binding.newWorkBTN.visibility = View.VISIBLE
            binding.customRV.visibility = View.VISIBLE
            binding.myWorkET.visibility = View.GONE
            binding.nameTV.visibility = View.GONE
            binding.cancelBTN.visibility = View.GONE
            binding.saveBTN.visibility = View.GONE
            binding.divider.visibility = View.VISIBLE
        }


        binding.saveBTN.setOnClickListener {
            val name = binding.myWorkET.text.toString()
            val id = fireViewModel.documentId
            val workout = UserWorkout(name, id)
            fireViewModel.addWorkoutToUser(workout)
            binding.myWorkET.setText("Custom Workout")

            findNavController().navigate(CustomFragmentDirections.actionCustomFragment2ToAddWorkoutFragment())

        }


            collectionReference
                .addSnapshotListener { value, error ->
                    if (error == null && value != null) {
                        val workouts = value.toObjects<UserWorkout>()
                        val adapter = CustomAdapter(emptyList())
                        binding.customRV.adapter = adapter
                        adapter.newData(workouts)


                    } else {
                        Log.e("FirebaseLog", "Error")
                    }

                }


        collectionReference.document()



    }


}

