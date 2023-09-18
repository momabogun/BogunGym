package com.example.bogungym.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bogungym.R
import com.example.bogungym.adapter.GymAdapter
import com.example.bogungym.adapter.SupplementsAdapter
import com.example.bogungym.data.Datasource
import com.example.bogungym.databinding.FragmentHomeBinding
import com.example.bogungym.databinding.FragmentSupplementsBinding

class SupplementsFragment : Fragment() {




    private lateinit var binding: FragmentSupplementsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupplementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val supplements = Datasource().loadSupplements()


        val recyclerView = binding.supplementsRV

        recyclerView.adapter = SupplementsAdapter(supplements)





    }
}