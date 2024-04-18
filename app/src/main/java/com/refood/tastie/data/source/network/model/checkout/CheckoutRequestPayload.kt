package com.refood.tastie.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName
import com.refood.tastie.data.source.network.model.checkout.CheckoutItemPayload


data class CheckoutRequestPayload(
    @SerializedName("username")
    val username: String?,
    @SerializedName("total")
    val total: Double?,
    @SerializedName("orders")
    val orders : List<CheckoutItemPayload>
)
