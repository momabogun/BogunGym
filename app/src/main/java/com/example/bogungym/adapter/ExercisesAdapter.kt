package com.example.bogungym.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.databinding.ListItemExerciseBinding
import com.example.bogungym.ui.ExerciseFragmentDirections
import java.util.Locale

class ExercisesAdapter(
    private var dataset: List<Exercises>
) : RecyclerView.Adapter<ExercisesAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ListItemExerciseBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun newData(newList: List<Exercises>){
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
        holder.binding.targetTV.text = item.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        holder.binding.momaTV.text = item.equipment.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }

        holder.itemView.setOnClickListener {
            val navController = holder.itemView.findNavController()
            navController.navigate(ExerciseFragmentDirections.actionExerciseFragmentToDetailFragment(item.id))
        }



    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}