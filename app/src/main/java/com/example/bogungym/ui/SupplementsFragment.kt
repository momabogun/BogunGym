package com.example.bogungym.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.adapter.ExercisesAdapter
import com.example.bogungym.adapter.GymAdapter
import com.example.bogungym.adapter.SupplementsAdapter
import com.example.bogungym.data.Datasource
import com.example.bogungym.databinding.FragmentHomeBinding
import com.example.bogungym.databinding.FragmentSupplementsBinding

class SupplementsFragment : Fragment() {


    private val viewModel: ExercisesViewModel by activityViewModels()



    private lateinit var binding: FragmentSupplementsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupplementsBinding.inflate(inflater, container, false)
        return binding.root


    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        val adapter = SupplementsAdapter(emptyList())


        binding.supplementsRV.adapter = adapter

        viewModel.supplements.observe(viewLifecycleOwner){
            adapter.newData(it)
        }




        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.userInput(newText.orEmpty())
                return true
            }
        })





    }


}