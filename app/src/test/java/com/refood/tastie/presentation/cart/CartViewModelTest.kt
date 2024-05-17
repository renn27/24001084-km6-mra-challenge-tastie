package com.refood.tastie.presentation.cart

import androidx.lifecycle.ViewModel
import com.refood.tastie.data.repository.CartRepository
import com.refood.tastie.data.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before

class CartViewModelTest {
    @MockK
    private lateinit var cartRepo: CartRepository
    private lateinit var userRepo: UserRepository
    private lateinit var vm: ViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }
}
