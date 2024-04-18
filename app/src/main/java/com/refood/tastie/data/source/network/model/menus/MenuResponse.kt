package com.refood.tastie.data.source.network.model.menus

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("status")
    val status : Boolean?,
    @SerializedName("code")
    val code : Int?,
    @SerializedName("message")
    val message : String?,
    @SerializedName("data")
    val data : List<MenuItemResponse>?,
)
