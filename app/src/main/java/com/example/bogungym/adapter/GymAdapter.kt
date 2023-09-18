package com.example.bogungym.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bogungym.data.model.BodyPart
import com.example.bogungym.databinding.ListItemBinding
import com.example.bogungym.ui.HomeFragmentDirections

class GymAdapter(
    private var dataset: List<BodyPart>
) : RecyclerView.Adapter<GymAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.nameMuscleTV.text = item.part
        holder.binding.muscleIV.setImageResource(item.image)
        holder.itemView.setOnClickListener {
            val navController = holder.itemView.findNavController()
            navController.navigate(HomeFragmentDirections.actionHomeFragment2ToExerciseFragment(item.part))
        }



    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}