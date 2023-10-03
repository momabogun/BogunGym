package com.example.bogungym.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bogungym.data.model.Workout
import com.example.bogungym.databinding.ListItemWorkoutsBinding

class WorkoutAdapter(
    private var dataset:List<Workout>,
    private val context: Context)
    :RecyclerView.Adapter<WorkoutAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ListItemWorkoutsBinding) : RecyclerView.ViewHolder(binding.root)


    @SuppressLint("NotifyDataSetChanged")
    fun newData(newList: List<Workout>) {
        dataset = newList
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemWorkoutsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.workoutIV.setImageResource(item.image)
        holder.binding.workoutUpperTV.text = context.getString(item.titleWorkout)
        holder.binding.workoutUnderTV.text = context.getString(item.descriptionWorkout)



    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
