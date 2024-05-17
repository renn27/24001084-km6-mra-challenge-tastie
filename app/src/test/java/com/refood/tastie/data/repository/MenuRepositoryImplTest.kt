package com.refood.tastie.data.repository

import app.cash.turbine.test
import com.refood.tastie.data.datasource.menu.MenuDataSource
import com.refood.tastie.data.model.Cart
import com.refood.tastie.data.source.network.model.checkout.CheckoutItemPayload
import com.refood.tastie.data.source.network.model.checkout.CheckoutRequestPayload
import com.refood.tastie.data.source.network.model.checkout.CheckoutResponse
import com.refood.tastie.data.source.network.model.menus.MenuItemResponse
import com.refood.tastie.data.source.network.model.menus.MenuResponse
import com.refood.tastie.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    @MockK
    lateinit var ds: MenuDataSource
    private lateinit var repo: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = MenuRepositoryImpl(ds)
    }

    @Test
    fun `get menus loading`() {
        val c1 =
            MenuItemResponse(
                id = "7129",
                name = "akadka",
                price = 10000.0,
                priceFormat = "Rp.10000",
                desc = "jhsakjdwakd",
                imgUrl = "jdhakwjad",
                location = "jhadjw",
            )
        val c2 =
            MenuItemResponse(
                id = "7129",
                name = "opopop",
                price = 10000.0,
                priceFormat = "Rp.10000",
                desc = "jhsakjdwakd",
                imgUrl = "jdhakwjad",
                location = "jhadjw",
            )
        val mockListMenu = listOf(c1, c2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { ds.getMenus() } returns mockResponse
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.getMenus() }
            }
        }
    }

    @Test
    fun `get menus success`() {
        val c1 =
            MenuItemResponse(
                id = "7129",
                name = "akadka",
                price = 10000.0,
                priceFormat = "Rp.10000",
                desc = "jhsakjdwakd",
                imgUrl = "jdhakwjad",
                location = "jhadjw",
            )
        val c2 =
            MenuItemResponse(
                id = "7129",
                name = "opopop",
                price = 10000.0,
                priceFormat = "Rp.10000",
                desc = "jhsakjdwakd",
                imgUrl = "jdhakwjad",
                location = "jhadjw",
            )
        val mockListMenu = listOf(c1, c2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { ds.getMenus() } returns mockResponse
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.getMenus() }
            }
        }
    }

    @Test
    fun `get menus error`() {
        runTest {
            coEvery { ds.getMenus() } throws IllegalStateException("Mock Error")
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.getMenus() }
            }
        }
    }

    @Test
    fun `get menus empty`() {
        val mockListMenu = listOf<MenuItemResponse>()
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { ds.getMenus() } returns mockResponse
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { ds.getMenus() }
            }
        }
    }

    @Test
    fun createOrder_success() =
        runTest {
            // Mock data
            val mockCarts =
                listOf(
                    Cart(
                        id = 1,
                        menuId = "1",
                        menuName = "Pizza",
                        menuImgUrl = "img1",
                        menuPrice = 10.0,
                        itemQuantity = 2,
                        itemNotes = "No cheese",
                    ),
                    Cart(
                        id = 2,
                        menuId = "2",
                        menuName = "Burger",
                        menuImgUrl = "img2",
                        menuPrice = 5.0,
                        itemQuantity = 1,
                        itemNotes = "Extra sauce",
                    ),
                )
            val totalPrice = 25.0
            val username = "testUser"

            // Mock response
            val mockResponse = CheckoutResponse(200, "succes", true)
            val mockFlow = flow { emit(mockResponse) }
            coEvery { ds.createOrder(any()) } returns mockResponse

            // Run test
            repo.createOrder(mockCarts, totalPrice, username).test {
                delay(100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)

                coVerify {
                    ds.createOrder(
                        CheckoutRequestPayload(
                            username = username,
                            total = totalPrice,
                            orders =
                                mockCarts.map {
                                    CheckoutItemPayload(
                                        name = it.menuName,
                                        quantity = it.itemQuantity,
                                        notes = it.itemNotes.orEmpty(),
                                        price = it.menuPrice,
                                    )
                                },
                        ),
                    )
                }
            }
        }
}
