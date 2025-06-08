package com.example.kurkotasks.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentPersonalInfoBinding
import com.example.kurkotasks.databinding.FragmentProfileBinding
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.view.home.MainActivity
import com.example.kurkotasks.view.viewModel.PersonalInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfo : Fragment() {
    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<PersonalInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        communicator = requireActivity() as MainActivity
        setupView()
        return inflater.inflate(R.layout.fragment_personal_info, container, false)
    }

    private fun setupView() {
        setupObservers()
    }

    private fun setupObservers(){
        viewModel.loaderState.observe(viewLifecycleOwner){
            communicator.showLoader(it)
        }
        viewModel.operationSuccess.observe(viewLifecycleOwner){ isSuccess ->
            if(isSuccess){
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }
}