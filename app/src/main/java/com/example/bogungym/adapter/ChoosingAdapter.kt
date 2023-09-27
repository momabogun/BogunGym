package com.example.bogungym.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bogungym.R
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.databinding.ListItemCustomBinding
import com.example.bogungym.ui.ChoosingFragment
import com.example.bogungym.ui.ExercisesViewModel
import java.util.Locale

class ChoosingAdapter(
    private var dataset: List<Exercises>,
    private val context: ChoosingFragment,
    val viewModel: ExercisesViewModel
) : RecyclerView.Adapter<ChoosingAdapter.ItemViewHolder>() {


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
            .load(imgUri)
            .into(holder.binding.exerIV)
        holder.binding.chooseBTN.setImageResource(R.drawable.baseline_circle_24)
        if (item.userPick) {
            holder.binding.imageView3.visibility = View.VISIBLE
        } else {
            holder.binding.imageView3.visibility = View.GONE

        }


        holder.binding.chooseBTN.setOnClickListener {
            item.userPick = !item.userPick
            viewModel.updatePick(if (item.userPick) 1 else 0, item.id)


            if (item.userPick) {
                holder.binding.imageView3.visibility = View.VISIBLE
            } else {
                holder.binding.imageView3.visibility = View.GONE
            }

        }
    }

        override fun getItemCount(): Int {
            return dataset.size
        }
    }
