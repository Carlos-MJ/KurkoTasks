package com.example.kurkotasks.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kurkotasks.databinding.FragmentProfileBinding
import com.example.kurkotasks.R
import com.example.kurkotasks.model.User
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.view.home.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        communicator = requireActivity() as MainActivity
        setupView()
        return binding.root
    }

    private fun setupView() {
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userInfo.observe(viewLifecycleOwner) { user ->
            updateUI(user)
        }
        viewModel.loaderState.observe(viewLifecycleOwner) {loaderState ->
            communicator.showLoader(loaderState)
        }
    }

    private fun updateUI(user: User){
        binding?.apply {
            userNameLabel.text = user.name + " "
            userNameLabel.text = user.name
            emailTextView.text = user.email.toString()
            passwordTextView.text = user.password.toString()
        }
    }

}