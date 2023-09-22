package com.example.bogungym.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.MainActivity
import com.example.bogungym.R
import com.example.bogungym.adapter.GymAdapter
import com.example.bogungym.data.Datasource
import com.example.bogungym.databinding.FragmentHomeBinding
import com.example.bogungym.ui.login.FirebaseViewModel

class HomeFragment : Fragment() {


    private val viewModel: FirebaseViewModel by activityViewModels()



    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.user.observe(viewLifecycleOwner){
            if (it == null){
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOnboardingFragment())
            }
        }



        val bodyParts = Datasource().loadBodyParts()


        val recyclerView = binding.gymRV

        recyclerView.adapter = GymAdapter(bodyParts)






    }
}