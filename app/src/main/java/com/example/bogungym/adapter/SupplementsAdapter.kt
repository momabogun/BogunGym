package com.example.bogungym.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bogungym.data.model.BodyPart
import com.example.bogungym.data.model.Supplements
import com.example.bogungym.databinding.ListItemBinding
import com.example.bogungym.databinding.ListItemSupplementsBinding
import com.example.bogungym.ui.HomeFragmentDirections
import com.example.bogungym.ui.SupplementsFragment
import com.example.bogungym.ui.SupplementsFragmentDirections

class SupplementsAdapter(
    private var dataset: List<Supplements>
) : RecyclerView.Adapter<SupplementsAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ListItemSupplementsBinding) : RecyclerView.ViewHolder(binding.root)





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemSupplementsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.supplementIV.setImageResource(item.picture)
        holder.itemView.setOnClickListener {
            val navController=holder.itemView.findNavController()
            navController.navigate(SupplementsFragmentDirections.actionSupplementsFragmentToSupplementDetailFragment(item.picture,item.detail,item.name,item.beginner))
        }



    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}