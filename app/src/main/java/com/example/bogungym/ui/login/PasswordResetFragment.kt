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
import com.example.bogungym.databinding.FragmentPasswordResetBinding

class PasswordResetFragment : Fragment() {

    private lateinit var binding: FragmentPasswordResetBinding
    private val viewModel: FirebaseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordResetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBackToLogin.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btSendReset.setOnClickListener {
            val email: String = binding.tietEmail.text.toString()
            if (email != "") {
                viewModel.sendPasswordRecovery(email)
            }
        }




    }
}