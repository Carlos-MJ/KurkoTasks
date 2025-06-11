package com.example.kurkotasks.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentPendingTasksBinding
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.view.home.adapters.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.firestore.FirebaseFirestore
import com.example.kurkotasks.model.Task
import com.example.kurkotasks.view.home.viewModel.PendingTaskViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.kurkotasks.view.home.viewModel.SharedTaskViewModel
import androidx.fragment.app.activityViewModels

@AndroidEntryPoint
class PendingTaskFragment : Fragment() {

    private var _binding: FragmentPendingTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel: PendingTaskViewModel by viewModels()
    private val sharedViewModel: SharedTaskViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        communicator = requireActivity() as MainActivity
        _binding = FragmentPendingTasksBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()
        setupView()

        return binding.root
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(emptyList()) { task ->
            sharedViewModel.setSelectedTask(task)
            findNavController().navigate(R.id.action_navigation_home_to_taskDetailFragment)
        }

        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }



    private fun setupView(){
        binding.iconAdd.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_taskDetailFragment)
        }
    }

    private fun observeViewModel() {

        viewModel.loaderState.observe(viewLifecycleOwner) { isLoading ->
            binding.recycleView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        viewModel.taskInfo.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateData(tasks)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskAdapter = TaskAdapter(emptyList()) { task ->
            sharedViewModel.setSelectedTask(task)
            findNavController().navigate(R.id.action_navigation_home_to_taskDetailFragment)
        }

        binding.recycleView.adapter = taskAdapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.loadTasks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}