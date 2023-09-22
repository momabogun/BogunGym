package com.example.bogungym.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.R
import com.example.bogungym.databinding.FragmentLoginBinding
import com.example.bogungym.databinding.FragmentOnboardingBinding


class OnboardingFragment : Fragment() {

    val viewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentOnboardingBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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