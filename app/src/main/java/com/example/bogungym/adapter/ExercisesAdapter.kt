package com.example.bogungym.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.databinding.ListItemCustomBinding
import com.example.bogungym.ui.ExerciseFragment
import com.example.bogungym.ui.ExerciseFragmentDirections
import com.example.bogungym.ui.MyWorkoutFragmentDirections
import java.util.Locale

class ExercisesAdapter(
    private var dataset: List<Exercises>,
    val viewModel: ExercisesViewModel,
    val context: Context
) : RecyclerView.Adapter<ExercisesAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ListItemCustomBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun newData(newList: List<Exercises>) {
        dataset = newList
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemCustomBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.exerTV.text = item.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        holder.binding.equipTV.text = item.equipment.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        val imgUri = item.gifUrl.toUri().buildUpon().scheme("https").build()
        Glide
            .with(context)
            .asBitmap()
            .load(imgUri)
            .into(holder.binding.exerIV)





        holder.itemView.setOnClickListener {
            val navController = holder.itemView.findNavController()
            navController.navigate(ExerciseFragmentDirections.actionExerciseFragmentToDetailFragment(item.id))
        }





        holder.binding.chooseBTN.visibility = View.GONE


    }



    override fun getItemCount(): Int {
        return dataset.size
    }
}