package com.example.kurkotasks.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentPendingTasksBinding
import com.example.kurkotasks.utils.FragmentCommunicator


class PendingTaskFragment : Fragment() {

    private var _binding: FragmentPendingTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        communicator = requireActivity() as MainActivity
        _binding = FragmentPendingTasksBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView(){
        binding.iconAdd.setOnClickListener {
            findNavController().navigate(R.id.action_pendingTaskFragment_to_newTasksFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}