package com.example.kurkotasks.view.onboarding

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kurkotasks.R
import com.example.kurkotasks.databinding.FragmentPersonalInfoBinding
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.view.home.MainActivity
import com.example.kurkotasks.viewModel.PersonalInfoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class PersonalInfo : Fragment() {
    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<PersonalInfoViewModel>()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        communicator = requireActivity() as OnboardingActivity
        setupView()
        return binding.root
    }

    private fun setupView() {
        val userId = arguments?.let {
            PersonalInfoArgs.fromBundle(it).userId
        }
        binding.dateUserTelt.apply {
            isFocusable = false
            isClickable = true
        }
        binding.btnPersonalInfo.setOnClickListener{
            Log.e("BOTON", "HA ENTRADO EN EL BOTON")
            if (userId != null) {
                viewModel.createUserInfo(userId,
                    binding.firstNameTelt.text.toString(),
                    binding.secondNameTelt.text.toString(),
                    binding.userNameTelt.text.toString(),
                    format.parse(binding.dateUserTelt.text.toString()) ?: Date())
            }
        }
            binding.dateUserTelt.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                // Ajusta el mes (+1 porque empieza en 0)
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.dateUserTelt.setText(fechaSeleccionada)
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
}