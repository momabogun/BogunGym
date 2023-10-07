package com.example.bogungym.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.data.model.FirebaseProfile
import com.example.bogungym.databinding.FragmentHomeBinding
import com.example.bogungym.databinding.FragmentSettingsBinding
import com.example.bogungym.ui.login.ProfileFragmentDirections
import com.google.firebase.FirebaseApp


class SettingsFragment : Fragment() {


    private lateinit var binding: FragmentSettingsBinding

    private val viewModel:ExercisesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        FirebaseApp.initializeApp(requireContext())



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val isDarkMode = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES
        binding.darkModeSW.isChecked = isDarkMode



        binding.darkModeSW.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            activity?.let { ActivityCompat.recreate(it) }
        }

        binding.deleteAcc.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                viewModel.deleteProfileCollection()
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete account")
            builder.setMessage("Are you sure you want to delete your profile?\nYour Data will be lost.")
            builder.create().show()


        }

        viewModel.user.observe(viewLifecycleOwner){
            if (it == null){
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToOnboardingFragment())
            }
        }

        binding.aboutUs.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToAboutUsFragment())
        }




    }





}