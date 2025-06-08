package com.example.kurkotasks.view

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
import com.example.kurkotasks.view.home.MainActivity
import com.example.kurkotasks.viewModel.RegisterViewModel


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
        communicator = requireActivity() as MainActivity
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
            binding.flecha.setOnClickListener {

                //viewModel.requestSignUp(binding.email.text.toString(),
                    //binding.passwordTIET.text.toString())
                findNavController().navigate(R.id.action_registerFragment2_to_loginFragment2)
            }

        binding.btnBoton.setOnClickListener {
        val email = binding.email.text.toString().trim()
        val password = binding.passwordTIET.text.toString().trim()
            val name = binding.nombreTIET.text.toString().trim()

            if (name.isEmpty()) {
                binding.nombreTIET.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.correo.error = "Introduce un correo válido"
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                binding.password.error = "Introduce una contraseña de al menos 6 caracteres"
                return@setOnClickListener
            }

        viewModel.requestSignUp(email, password)
        findNavController().navigate(R.id.action_registerFragment2_to_loginFragment2)

        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}