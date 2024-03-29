package com.refood.tastie.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.refood.tastie.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {

    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

}