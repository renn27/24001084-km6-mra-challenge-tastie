package com.refood.tastie.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.refood.tastie.R
import com.refood.tastie.data.repository.UserRepositoryImpl
import com.refood.tastie.data.source.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.refood.tastie.databinding.ActivityMainBinding
import com.refood.tastie.presentation.cart.CartFragment
import com.refood.tastie.presentation.login.LoginActivity
import com.refood.tastie.utils.GenericViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
//    private val isLogin = false

    private val viewModel: MainViewModel by viewModel()
//    private val viewModel: MainViewModel by viewModels {
//        GenericViewModelFactory.create(createViewModel())
//    }
//
//    private fun createViewModel(): MainViewModel {
//        val firebaseAuth = FirebaseAuth.getInstance()
//        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
//        val repo = UserRepositoryImpl(dataSource)
//        return MainViewModel(repo)
//    }

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