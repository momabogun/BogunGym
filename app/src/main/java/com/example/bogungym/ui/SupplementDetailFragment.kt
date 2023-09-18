package com.example.bogungym.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bogungym.R
import com.example.bogungym.databinding.FragmentSupplementDetailBinding
import com.example.bogungym.databinding.FragmentSupplementsBinding


class SupplementDetailFragment : Fragment() {

    private lateinit var binding: FragmentSupplementDetailBinding
    private var imageID = 0
    private var detailID = 0
    private var nameID = ""
    private var beginID: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            imageID = it.getInt("imageID")
            detailID = it.getInt("detailID")
            nameID= it.getString("nameID","")
            beginID = it.getBoolean("beginID")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupplementDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailSuppTV.text= getString(detailID)
        binding.nameSuppTV.text = nameID
        binding.detailSuppIV.setImageResource(imageID)
        val drawable = resources.getDrawable(R.drawable.round_doctor,null)
        if (beginID){
            binding.diffTV.text = getString(R.string.beginners)
        } else{
            binding.diffTV.text = getString(R.string.consult_doctor)
            binding.diffTV.background = drawable
        }
    }



}