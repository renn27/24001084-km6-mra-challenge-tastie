package com.refood.tastie.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.refood.tastie.R
import com.refood.tastie.databinding.ActivityMainBinding
import com.refood.tastie.presentation.cart.CartFragment

class MainActivity : AppCompatActivity() {
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

        val navigateToCart = intent.getBooleanExtra("navigate_to_cart", false)
        if (navigateToCart) {
            val cartDestination = navController.graph.findNode(R.id.menu_tab_cart)
            cartDestination?.let { destination ->
                binding.navView.selectedItemId = destination.id
            }
        }
    }

}