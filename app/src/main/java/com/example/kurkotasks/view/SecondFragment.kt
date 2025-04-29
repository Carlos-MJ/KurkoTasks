package com.example.kurkotasks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentNewTasksBinding
import com.example.kurkotasks.utils.FragmentCommunicator


class SecondFragment : Fragment() {

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
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}