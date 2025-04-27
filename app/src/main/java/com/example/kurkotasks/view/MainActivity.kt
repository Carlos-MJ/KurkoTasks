package com.example.kurkotasks.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kurkotasks.R
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun showLoader(value: Boolean) {
        binding.loader.visibility = if (value) View.VISIBLE else View.GONE
    }
}

