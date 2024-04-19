package com.refood.tastie.data.repository

import com.refood.tastie.data.source.network.model.checkout.CheckoutRequestPayload
import com.refood.tastie.data.source.network.model.checkout.CheckoutItemPayload
import com.refood.tastie.data.datasource.menu.MenuDataSource
import com.refood.tastie.data.model.Menu
import com.refood.tastie.data.mapper.toMenus
import com.refood.tastie.data.model.Cart
import com.refood.tastie.utils.ResultWrapper
import com.refood.tastie.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(category : String? = null): Flow<ResultWrapper<List<Menu>>>
    fun createOrder(products: List<Cart>, totalPrice : Double, username: String ): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(
    private val dataSource: MenuDataSource
) : MenuRepository {
    override fun getMenus(category : String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenus(category).data.toMenus()
        }
    }
    override fun createOrder(products: List<Cart>, totalPrice : Double, username: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(CheckoutRequestPayload(
                username = username,
                total = totalPrice,
                orders = products.map {
                    CheckoutItemPayload(
                        name = it.menuName,
                        quantity = it.itemQuantity,
                        notes = it.itemNotes.orEmpty(),
                        price = it.menuPrice
                    )
                }
            )).status ?: false
        }
    }
}