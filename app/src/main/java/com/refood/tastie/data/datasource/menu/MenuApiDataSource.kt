package com.refood.tastie.data.datasource.menu

import com.refood.tastie.data.source.network.model.checkout.CheckoutRequestPayload
import com.refood.tastie.data.source.network.model.checkout.CheckoutResponse
import com.refood.tastie.data.source.network.model.menus.MenuResponse
import com.refood.tastie.data.source.network.services.TastieApiService

class MenuApiDataSource(
    private val service: TastieApiService,
) : MenuDataSource {
    override suspend fun getMenus(category: String?): MenuResponse {
        return service.getMenus(category)
    }

    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return service.createOrder(payload)
    }
}
