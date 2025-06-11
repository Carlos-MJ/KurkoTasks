package com.example.kurkotasks.view.home

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentNewTasksBinding
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.view.home.viewModel.NewTasksViewModel
import com.example.kurkotasks.view.onboarding.PersonalInfoArgs
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class NewTasksFragment : Fragment() {

    private var _binding: FragmentNewTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<NewTasksViewModel>()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewTasksBinding.inflate(inflater, container, false)
        communicator = requireActivity() as MainActivity
        setupView()
        return binding.root

    }

    private fun setupView(){
        binding.imageIconFlecha.setOnClickListener {
            findNavController().navigate(R.id.action_newTasksFragment_to_pendingTaskFragment)
        }

        val taskId = UUID.randomUUID().toString()

        binding.FechaTIK.apply {
            isFocusable = false
            isClickable = true
        }
        binding.buttonNewTask.setOnClickListener{
            Log.e("BOTON", "HA ENTRADO EN EL BOTON")
            if (taskId != null) {
                viewModel.createTaskInfo(taskId,
                    binding.nombreTIK.text.toString(),
                    binding.descriptionTIK.text.toString(),
                    format.parse(binding.FechaTIK.text.toString()) ?: Date())
            }
        }
        binding.FechaTIK.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                // Ajusta el mes (+1 porque empieza en 0)
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.FechaTIK.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
