package com.refood.tastie.presentation.detailmenu

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.refood.tastie.data.model.Menu
import com.refood.tastie.data.repository.CartRepository
import com.refood.tastie.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository,
) : ViewModel() {
    val product = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRA_MENU)

    val productCountLiveData =
        MutableLiveData(0).apply {
            postValue(0)
        }

    val priceLiveData =
        MutableLiveData<Double>().apply {
            postValue(0.0)
        }

    val urlLocation = product?.urlLocation

    fun add() {
        val count = (productCountLiveData.value ?: 0) + 1
        productCountLiveData.postValue(count)
        priceLiveData.postValue(product?.price?.times(count) ?: 0.0)
    }

    fun minus() {
        if ((productCountLiveData.value ?: 0) > 0) {
            val count = (productCountLiveData.value ?: 0) - 1
            productCountLiveData.postValue(count)
            priceLiveData.postValue(product?.price?.times(count) ?: 0.0)
        }
    }

    fun addToCart(): LiveData<ResultWrapper<Boolean>> {
        return product?.let {
            val quantity = productCountLiveData.value ?: 0
            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Product not found"))) }
    }
}
