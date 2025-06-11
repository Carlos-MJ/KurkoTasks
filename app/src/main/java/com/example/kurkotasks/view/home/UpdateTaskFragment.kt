package com.example.kurkotasks.view.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentUpdateTaskBinding
import com.example.kurkotasks.view.home.viewModel.SharedTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class UpdateTaskFragment : Fragment() {

    private var _binding: FragmentUpdateTaskBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedTaskViewModel by activityViewModels() // ✅ Se usa correctamente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedTask.observe(viewLifecycleOwner) { task ->
            task?.let {
                binding.nombreTIK.setText(it.name)
                binding.descriptionTIK.setText(it.description)
                binding.DateTIK.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it.bornDate))
            }
        }

        binding.buttonUpdateTask.setOnClickListener {
            updateTask()
            findNavController().navigate(R.id.action_updateTaskFragment_to_navigation_home)
        }
    }

    private fun updateTask() {
        val updatedTask = sharedViewModel.selectedTask.value?.copy(
            name = binding.nombreTIK.text.toString(),
            description = binding.descriptionTIK.text.toString(),
            bornDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(binding.DateTIK.text.toString())
        )

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

        updatedTask?.let {
            sharedViewModel.setSelectedTask(it)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}