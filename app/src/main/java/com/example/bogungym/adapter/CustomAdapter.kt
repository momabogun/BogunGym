package com.example.bogungym.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bogungym.data.model.BodyPart
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.databinding.ListItemBinding
import com.example.bogungym.databinding.ListItemExerciseBinding
import com.example.bogungym.ui.ExercisesViewModel
import com.example.bogungym.ui.HomeFragmentDirections

class CustomAdapter (
    private var dataset: List<UserWorkout>,
    private val viewModel: ExercisesViewModel
) : RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ListItemExerciseBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun newData(newList: List<UserWorkout>){
        dataset = newList
        notifyDataSetChanged()

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.targetTV.text = item.name
        holder.binding.imageView4.setOnClickListener {
            viewModel.deleteWorkout(item)
        }




    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}