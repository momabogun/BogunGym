package com.example.bogungym.ui.login


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bogungym.MainActivity
import com.example.bogungym.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    val viewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBTN.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email != "" && password != "") {
                viewModel.login(email, password)

            }

            binding.goSignUpTV.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToPasswordResetFragment())
            }



            viewModel.user.observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
            }



        }

    }
}