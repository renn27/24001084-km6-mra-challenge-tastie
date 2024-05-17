package com.refood.tastie.data.datasource.menu

import com.refood.tastie.data.source.network.model.checkout.CheckoutRequestPayload
import com.refood.tastie.data.source.network.model.checkout.CheckoutResponse
import com.refood.tastie.data.source.network.model.menus.MenuResponse
import com.refood.tastie.data.source.network.services.TastieApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuApiDataSourceTest {
    @MockK
    lateinit var service: TastieApiService

    private lateinit var ds: MenuDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = MenuApiDataSource(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<MenuResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val actualResponse = ds.getMenus("komputer")
            coVerify { service.getMenus(any()) }
            assertEquals(actualResponse, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestPayload>(relaxed = true)
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResponse = ds.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(actualResponse, mockResponse)
        }
    }
}
