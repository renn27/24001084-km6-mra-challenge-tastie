package com.refood.tastie.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.refood.tastie.R
import com.refood.tastie.databinding.ActivityMainBinding
import com.refood.tastie.presentation.login.LoginActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.menu_tab_profile -> {
                    lifecycleScope.launch {
                        if (!viewModel.isUserLoggedIn()) {
                            navigateToLogin()
                            controller.navigate(R.id.menu_tab_home)
                        }
                    }
                }
            }
        }

        val navigateToCart = intent.getBooleanExtra("navigate_to_cart", false)
        if (navigateToCart) {
            val cartDestination = navController.graph.findNode(R.id.menu_tab_cart)
            cartDestination?.let { destination ->
                binding.navView.selectedItemId = destination.id
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
