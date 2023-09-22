package com.example.bogungym.ui.login

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
import com.example.bogungym.data.model.FirebaseProfile
import com.example.bogungym.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects


class ProfileFragment : Fragment() {

    val viewModel: FirebaseViewModel by activityViewModels()


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
            viewModel.signOut()
        }

        binding.editProfile.setOnClickListener {
            getContent.launch("image/*")
        }

        viewModel.user.observe(viewLifecycleOwner){
            if (it == null){
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToOnboardingFragment())
            }
        }

//        binding.editBTN.setOnClickListener {
//
//            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
//            val name = binding.editTextInputName.text.toString()
//            val weight = binding.editTextInputWeight.text.toString().toInt()
//            val height = binding.editTextInputHeight.text.toString().toInt()
//            val age = binding.editTextInputAge.text.toString().toInt()
//
//            val updateMap = mapOf(
//                "name" to name,
//                "weight" to weight,
//                "height" to height,
//                "age" to age
//            )
//            viewModel.profileRef.update(updateMap)
//
//            Toast.makeText(requireContext(),"Successfully Edited",Toast.LENGTH_LONG).show()
//        }



        binding.editBTN.setOnClickListener {
            val firstName = binding.editTextInputName.text.toString()
            val age = binding.editTextInputAge.text.toString().toInt()
            val height = binding.editTextInputHeight.text.toString().toInt()
            val weight = binding.editTextInputWeight.text.toString().toInt()
            viewModel.updateProfile(FirebaseProfile(firstName, weight, height,age))

            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
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

//        viewModel.profileRef.addSnapshotListener { value, error ->
//
//            value?.let {
//                val p = it.toObject<FirebaseProfile>()
//                binding.editTextInputName.setText(p?.name)
//                binding.editTextInputAge.setText(p?.age.toString())
//                binding.editTextInputHeight.setText(p?.height.toString())
//                binding.editTextInputWeight.setText(p?.weight.toString())
//            }
//
//        }

        binding.emailProfileTV.text = user.email







            val isNightMode =
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            binding.darkSW.isChecked = isNightMode

            binding.darkSW.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                activity?.let { ActivityCompat.recreate(it) }
            }





        }



    }

