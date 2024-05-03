package com.refood.tastie.data.datasource.menu

import com.refood.tastie.data.source.network.model.checkout.CheckoutRequestPayload
import com.refood.tastie.data.source.network.model.checkout.CheckoutResponse
import com.refood.tastie.data.source.network.model.menus.MenuResponse

interface MenuDataSource {
    suspend fun getMenus(category: String? = null): MenuResponse
    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse

}