package com.example.bogungym.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.MainActivity
import com.example.bogungym.R
import com.example.bogungym.utils.SwipeGesture
import com.example.bogungym.adapter.CustomAdapter
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.databinding.FragmentCustomBinding
import com.example.bogungym.ui.login.ProfileFragmentDirections

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObjects

class CustomFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()

    private lateinit var collectionReference: CollectionReference



    private lateinit var binding: FragmentCustomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collectionReference = viewModel.getWorkoutsReference()
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
            viewModel.updateAllFalse()
            findNavController().navigate(CustomFragmentDirections.actionCustomFragment2ToWorkoutSaveFragment())
        }


        val mainActivity = activity as MainActivity







        collectionReference
            .addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    val workouts = value.toObjects<UserWorkout>()
                    val adapter = CustomAdapter(workouts,viewModel)


                    val swipeGesture= object : SwipeGesture(mainActivity){
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                            when(direction){

                                ItemTouchHelper.LEFT ->{
                                    val builder = AlertDialog.Builder(mainActivity)
                                    builder.setPositiveButton("Yes") { _, _ ->
                                        adapter.deleteItem(viewHolder.bindingAdapterPosition)
                                    }
                                    builder.setNegativeButton("No") { _, _ ->
                                        adapter.notifyDataSetChanged()
                                    }
                                    builder.setTitle("Delete workout")
                                    builder.setMessage("Are you sure that you want to delete this workout?")
                                    builder.create().show()
                                }
                            }
                        }
                    }

                    val touchHelper = ItemTouchHelper(swipeGesture)
                    touchHelper.attachToRecyclerView(binding.customRV)
                    binding.customRV.adapter = adapter


                } else {
                    Log.e("FirebaseLog", "Error")
                }

            }




    }





}



