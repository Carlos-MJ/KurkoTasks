package com.example.kurkotasks.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentLoginBinding
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.view.home.MainActivity
import com.example.kurkotasks.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()
    var isValid: Boolean = false
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        communicator = requireActivity() as OnboardingActivity
        setupObservers()
        setupView()
        return binding.root
    }

    private fun setupView() {

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnBoton.setOnClickListener {
            if (validateInputs()) {
                requestLogin()
            } else {
                Toast.makeText(activity, "Correo y contraseña son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }

        binding.email.addTextChangedListener {

            if (binding.email.text.toString().isEmpty()) {
                binding.correo.error = "Introduce un correo"
                isValid = false
            } else {
                isValid = true
            }
        }
        binding.passwordTIET.addTextChangedListener {

            if (binding.passwordTIET.text.toString().isEmpty()) {
                binding.password.error = "Introduce tu contraseña"
                isValid = false
            } else {
                isValid = true
            }
        }
        setupObservers()
    }

    private fun validateInputs(): Boolean {
        val emailNotEmpty = binding.email.text.toString().isNotEmpty()
        val passwordNotEmpty = binding.passwordTIET.text.toString().isNotEmpty()

        isValid = emailNotEmpty && passwordNotEmpty

        binding.correo.error = if (!emailNotEmpty) "Introduce un correo" else null
        binding.password.error = if (!passwordNotEmpty) "Introduce tu contraseña" else null

        return isValid
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
        viewModel.sessionValid.observe(viewLifecycleOwner) { validSession ->
            if (validSession) {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(activity, "Ingreso invalido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLogin() {
        viewModel.requestSignIn(binding.email.text.toString(),
            binding.passwordTIET.text.toString())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}