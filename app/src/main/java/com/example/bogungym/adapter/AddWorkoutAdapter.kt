package com.example.bogungym.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bogungym.data.model.BodyPart
import com.example.bogungym.databinding.ListItemExerciseBinding
import com.example.bogungym.ui.AddWorkoutFragmentDirections
import java.util.Locale

class AddWorkoutAdapter(
    private var dataset: List<BodyPart>
) : RecyclerView.Adapter<AddWorkoutAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ListItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root)





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.targetTV.text = item.part.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        holder.itemView.setOnClickListener {
            val navController = holder.itemView.findNavController()
            navController.navigate(AddWorkoutFragmentDirections.actionAddWorkoutFragmentToChoosingFragment(item.part))
        }



    }

    override fun getItemCount(): Int {

        return dataset.size
    }
}