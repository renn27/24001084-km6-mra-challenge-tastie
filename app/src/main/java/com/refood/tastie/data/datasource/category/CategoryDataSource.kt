package com.refood.tastie.data.datasource.category

import com.refood.tastie.data.model.Category
import com.refood.tastie.data.source.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategories(): CategoriesResponse
}