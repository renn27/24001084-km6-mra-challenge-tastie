package com.refood.tastie.presentation.main

import androidx.lifecycle.ViewModel
import com.refood.tastie.data.repository.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repository.isLoggedIn()

}