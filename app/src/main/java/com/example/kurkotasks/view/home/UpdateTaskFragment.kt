package com.example.kurkotasks.view.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentUpdateTaskBinding
import com.example.kurkotasks.model.Task
import com.example.kurkotasks.view.home.viewModel.UpdateTaskViewModel
import com.example.kurkotasks.view.home.viewModel.SharedTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class UpdateTaskFragment : Fragment() {
    private var _binding: FragmentUpdateTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UpdateTaskViewModel>()
    private val sharedViewModel: SharedTaskViewModel by activityViewModels() // ✅ Usamos ViewModel compartido
    private val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        sharedViewModel.selectedTask.observe(viewLifecycleOwner) { task -> // ✅ Usamos la tarea del ViewModel compartido
            task?.let {
                binding.nombreTIK.setText(it.name)
                binding.descriptionTIK.setText(it.description)
                binding.DateTIK.setText(format.format(it.bornDate))
            }
        }

        binding.DateTIK.apply {
            isFocusable = false
            isClickable = true
        }

        binding.buttonUpdateTask.setOnClickListener {
            sharedViewModel.selectedTask.value?.let { task ->
                val updatedTask = task.copy(
                    name = binding.nombreTIK.text.toString(),
                    description = binding.descriptionTIK.text.toString(),
                    bornDate = format.parse(binding.DateTIK.text.toString()) ?: Date()
                )
                viewModel.updateTaskInfo(updatedTask)
            }
        }



        binding.DateTIK.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.DateTIK.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.operationSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Log.d("Firestore", "Redirigiendo a HomeFragment")
                findNavController().navigate(R.id.action_updateTaskFragment_to_navigation_home)
            }
        }
    }
}