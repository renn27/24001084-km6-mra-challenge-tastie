package com.refood.tastie.data.source.network.model.menus

import com.google.gson.annotations.SerializedName

data class MenuItemResponse(
    @SerializedName("id")
    val id : String?,
    @SerializedName("image_url")
    val imgUrl : String?,
    @SerializedName("nama")
    val name : String?,
    @SerializedName("harga_format")
    val priceFormat : String?,
    @SerializedName("harga")
    val price : Double?,
    @SerializedName("detail")
    val desc : String?,
    @SerializedName("alamat_resto")
    val location : String?,
)
