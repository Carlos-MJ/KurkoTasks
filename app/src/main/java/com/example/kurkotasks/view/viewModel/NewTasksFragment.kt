package com.example.kurkotasks.view.viewModel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentNewTasksBinding
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.view.home.MainActivity


class NewTasksFragment : Fragment() {

    private var _binding: FragmentNewTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        communicator = requireActivity() as MainActivity
        _binding = FragmentNewTasksBinding.inflate(inflater, container, false)
        setupView()
        return binding.root

    }

    private fun setupView(){
        binding.imageIconFlecha.setOnClickListener {
            findNavController().navigate(R.id.action_newTasksFragment_to_pendingTaskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}