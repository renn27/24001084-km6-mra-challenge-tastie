package com.refood.tastie.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.refood.tastie.data.repository.CartRepository
import com.refood.tastie.data.repository.MenuRepository
import com.refood.tastie.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val menuRepository: MenuRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    private val user = userRepository.getCurrentUser()?.fullName.orEmpty()

    fun checkoutCart() =
        checkoutData.value?.payload?.let { (carts, priceItems, totalPrice) ->
            menuRepository.createOrder(
                carts,
                totalPrice,
                user,
            ).asLiveData(Dispatchers.IO)
        }

    fun deleteAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAllCart().collect()
        }
    }
}
