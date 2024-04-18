package com.refood.tastie.data.mapper

import com.refood.tastie.data.model.Category
import com.refood.tastie.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() =
    Category(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        imageUrl = this?.imgUrl.orEmpty(),
    )

fun Collection<CategoryItemResponse>?.toCategories() =
    this?.map { it.toCategory() } ?: listOf()