package com.example.bogungym.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.R
import com.example.bogungym.databinding.FragmentLoginBinding
import com.example.bogungym.databinding.FragmentOnboardingBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class OnboardingFragment : Fragment() {

//    private lateinit var googleSignInClient: GoogleSignInClient
//
//    private lateinit var auth: FirebaseAuth


    val viewModel: FirebaseViewModel by activityViewModels()
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


//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("479279906166-httc70cg2gcr2frgkqhntstg4kq8g1gd.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//
//
//        googleSignInClient= GoogleSignIn.getClient(requireActivity(),gso)
//
//        binding.googleBTN.setOnClickListener {
//            signIn()
//        }

        binding.emailOnboardBTN.setOnClickListener {
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToSignUpFragment())
        }

        binding.loginTV.setOnClickListener {
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment())
        }



        viewModel.user.observe(viewLifecycleOwner){
            if(it != null){
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment())
            }
        }




    }

}