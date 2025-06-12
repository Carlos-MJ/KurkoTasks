package com.example.kurkotasks.view.home

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import  androidx.navigation.Navigation
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentTaskDetailBinding
import com.example.kurkotasks.view.home.viewModel.TaskDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.kurkotasks.view.home.viewModel.SharedTaskViewModel
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import android.text.method.ScrollingMovementMethod
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private val viewModel: TaskDetailViewModel by viewModels()
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedTaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)

        binding.deleteButton.setOnClickListener {
            sharedViewModel.selectedTask.value?.let { task ->
                viewModel.deleteTask(task.id)
                binding.deleteButton.visibility = View.GONE
                findNavController().navigateUp()
            }
        }

        binding.updateButton.setOnClickListener {
            findNavController().navigate(R.id.action_taskDetailFragment_to_updateTaskFragment)
        }

        setupObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedTask.observe(viewLifecycleOwner) { task ->
            task?.let {
                binding.nombreTIK.text = it.name
                binding.descriptionTIK.text = it.description
                binding.FechaTIK.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it.bornDate)
            }
        }
    }

    private fun setupObservers() {
        viewModel.taskInfo.observe(viewLifecycleOwner) { task ->
            task?.let {
                binding.nombreTIK.text = Editable.Factory.getInstance().newEditable(it.name)
                binding.descriptionTIK.text = Editable.Factory.getInstance().newEditable(it.description)
                binding.FechaTIK.text = Editable.Factory.getInstance().newEditable(
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it.bornDate)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}