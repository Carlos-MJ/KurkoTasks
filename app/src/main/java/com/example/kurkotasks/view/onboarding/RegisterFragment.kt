package com.example.kurkotasks.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentRegisterBinding
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RegisterViewModel>()
    var isValid: Boolean = false
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communicator = requireActivity() as OnboardingActivity
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
            binding.flecha.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

        binding.btnBoton.setOnClickListener {

            val email = binding.email.text.toString().trim()
            val password = binding.passwordTIET.text.toString().trim()

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.correo.error = "Introduce un correo válido"
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                binding.password.error = "Introduce una contraseña de al menos 6 caracteres"
                return@setOnClickListener
            }

        viewModel.requestSignUp(email, password)

        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }

        viewModel.isUserCreted.observe(viewLifecycleOwner){userId ->
            val action = RegisterFragmentDirections.actionRegisterFragmentToPersonalInfo(userId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}