package com.example.bogungym.ui

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.example.bogungym.R
import com.example.bogungym.adapter.ExercisesAdapter
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.databinding.FragmentDetailBinding
import com.example.bogungym.databinding.FragmentExerciseBinding
import java.util.Locale


class DetailFragment : Fragment() {



    private val viewModel: ExercisesViewModel by activityViewModels()

    private lateinit var binding: FragmentDetailBinding



    private var id: String = ""

    private lateinit var exercise: Exercises


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            id = it.getString("id","")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel.getExerciseByID(id).observe(viewLifecycleOwner) { exercise ->

            binding.detailTV.text = exercise.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }

            val imgUri = exercise.gifUrl.toUri().buildUpon().scheme("https").build()

            Glide
                .with(this)
                .load(imgUri)
                .into(binding.exerciseIV)





        }
    }
}