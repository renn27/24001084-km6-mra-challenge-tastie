package com.refood.tastie.data.mapper

import com.refood.tastie.data.model.Menu
import com.refood.tastie.data.source.network.model.menus.MenuItemResponse

fun MenuItemResponse?.toMenu() =
    Menu(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        price = this?.price ?: 0.0,
        imagePictUrl = this?.imgUrl.orEmpty(),
        description = this?.desc.orEmpty(),
        location = this?.location.orEmpty(),
        urlLocation = "test",
    )

fun Collection<MenuItemResponse>?.toMenus() =
    this?.map {
        it.toMenu()
    } ?: listOf()
