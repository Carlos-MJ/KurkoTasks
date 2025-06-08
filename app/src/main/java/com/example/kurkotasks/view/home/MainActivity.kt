package com.example.kurkotasks.view.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.kurkotasks.R
import com.example.kurkotasks.utils.FragmentCommunicator
import com.example.kurkotasks.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHost = supportFragmentManager.findFragmentById(R.id.homeContainerView) as NavHostFragment
        val navController = navHost.navController
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (!isUserLoggedIn() || destination.id == R.id.loginFragment2 || destination.id == R.id.registerFragment2) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
        loginSuccess()
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun loginSuccess() {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", true)
            apply()
        }
    }


    override fun showLoader(value: Boolean) {
        binding.loader.visibility = if (value) View.VISIBLE else View.GONE
    }
}

