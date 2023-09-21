package com.example.bogungym.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bogungym.data.model.Days
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.databinding.ListItemExerciseBinding
import com.example.bogungym.ui.ExercisesViewModel

class DaysAdapter(
    private var dataset: List<Days>,
    private val viewModel: ExercisesViewModel
) : RecyclerView.Adapter<DaysAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ListItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]



    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}