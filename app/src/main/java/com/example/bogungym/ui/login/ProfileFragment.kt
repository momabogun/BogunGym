package com.example.bogungym.ui.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.data.model.FirebaseProfile
import com.example.bogungym.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import kotlin.math.pow


class ProfileFragment : Fragment() {

    val viewModel: ExercisesViewModel by activityViewModels()


    private lateinit var binding: FragmentProfileBinding



    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadImage(uri)
        }
    }








    private lateinit var user: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = viewModel.user.value!!


    }

    @SuppressLint("SetTextI18n")
    private fun calculateBMI(){
        val weight = binding.editTextInputWeight.text.toString().toFloatOrNull()
        val height = binding.editTextInputHeight.text.toString().toFloatOrNull()


        if (weight != null && height != null ){
            val bmi = weight/(height/100).pow(2)
            val bmiResult = String.format("%.2f", bmi)

            val bmiCategory = when {
                bmi < 18.5 -> "Underweight"
                bmi < 25 -> "Normal weight"
                bmi < 30 -> "Overweight"
                else -> "Obese"
            }


            binding.bmiTV.text = "BMI: $bmiResult\nCategory: $bmiCategory"
        } else{
            binding.bmiTV.text = "BMI: Invalid Input"
        }
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOutBTN.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                viewModel.signOut()
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            builder.setTitle("Log Out")
            builder.setMessage("Are you sure that you want to Log Out?")
            builder.create().show()

        }



        binding.bmiTV.setOnClickListener {
            calculateBMI()
        }



        binding.editProfile.setOnClickListener {
            getContent.launch("image/*")
        }




        viewModel.user.observe(viewLifecycleOwner){
            if (it == null){
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToOnboardingFragment())
            }
        }




        binding.settingsBTN.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
        }




        viewModel.profileRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {

                val updatedProfile = snapshot.toObject(FirebaseProfile::class.java)
                binding.editTextInputName.setText(updatedProfile?.name)
                binding.editTextInputAge.setText(updatedProfile?.age.toString())
                binding.editTextInputHeight.setText(updatedProfile?.height.toString())
                binding.editTextInputWeight.setText(updatedProfile?.weight.toString())
                if (updatedProfile?.profilePicture != "") {
                    binding.profilePicIV.load(updatedProfile?.profilePicture)
                }
            } else {
                Log.e("Snapshot Error", "$error")
            }
        }




        binding.editBTN.setOnClickListener {

            val firstName = binding.editTextInputName.text.toString()
            val age = binding.editTextInputAge.text.toString().toInt()
            val height = binding.editTextInputHeight.text.toString().toInt()
            val weight = binding.editTextInputWeight.text.toString().toInt()

            viewModel.updateProfile(FirebaseProfile(firstName, weight, height,age))

            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
        }




        binding.emailProfileTV.text = user.email




        }



    }

