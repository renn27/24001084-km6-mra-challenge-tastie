package com.refood.tastie.data.mapper


import com.refood.tastie.data.model.Cart
import com.refood.tastie.data.source.local.database.entity.CartEntity


fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    menuId = this?.menuId.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    menuImgUrl = this?.menuImgUrl.orEmpty(),
    itemNotes = this?.itemNotes
)

fun CartEntity?.toCart() = Cart(
    id = this?.id,
    menuId = this?.menuId.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0.0,
    menuImgUrl = this?.menuImgUrl.orEmpty(),
    itemNotes = this?.itemNotes
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }