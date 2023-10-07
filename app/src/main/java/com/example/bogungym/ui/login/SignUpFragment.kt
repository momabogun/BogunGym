package com.example.bogungym.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.ExercisesViewModel
import com.example.bogungym.R
import com.example.bogungym.databinding.FragmentLoginBinding
import com.example.bogungym.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {


    val viewModel: ExercisesViewModel by activityViewModels()
    private lateinit var binding: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isDarkMode = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES


        if (isDarkMode){
            binding.lightIV.visibility = View.GONE
            binding.imageView2.visibility = View.VISIBLE
        }else{
            binding.imageView2.visibility = View.GONE
            binding.lightIV.visibility = View.VISIBLE
        }



        binding.signUpBTN.setOnClickListener {
            val email = binding.signUpEmail.text.toString()
            val password = binding.signUpPassword.text.toString()
            val confirmPassword = binding.editTextTextPassword.text.toString()
            if (password == confirmPassword){
                viewModel.signUp(email, password)
            } else {
                Toast.makeText(requireContext(),"Both passwords must be the same.",Toast.LENGTH_LONG).show()
            }

        }



        binding.goLoginTV.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }


        viewModel.user.observe(viewLifecycleOwner){
            if(it != null){
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
            }
        }

    }

}