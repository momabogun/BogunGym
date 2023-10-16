package com.example.bogungym.ui.login

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.databinding.FragmentLoginBinding
import com.example.bogungym.databinding.FragmentOnboardingBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider


class OnboardingFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
//
//    private lateinit var auth: FirebaseAuth


    val viewModel: ExercisesViewModel by activityViewModels()
    private lateinit var binding: FragmentOnboardingBinding





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }





//
//    private fun signIn(){
//
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent,RC_SIGN_IN)
//    }
//
//    companion object{
//        const val RC_SIGN_IN = 1001
//        const val EXTRA_NAME = "EXTRA NAME"
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val isDarkMode = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES


        if (isDarkMode){
            binding.onboardIV.visibility = View.GONE
            binding.imageView6.visibility = View.VISIBLE
        }else{
            binding.imageView6.visibility = View.GONE
            binding.onboardIV.visibility = View.VISIBLE
        }

        binding.signUpBTN.setOnClickListener {
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToSignUpFragment())
        }

        binding.signInBTN.setOnClickListener {
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment())
        }



        viewModel.user.observe(viewLifecycleOwner){
            if(it != null){
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment())
            }
        }





    }








    }