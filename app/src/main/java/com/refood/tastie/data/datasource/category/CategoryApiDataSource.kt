package com.refood.tastie.data.datasource.category

import com.refood.tastie.data.source.network.model.category.CategoriesResponse
import com.refood.tastie.data.source.network.services.TastieApiService

class CategoryApiDataSource(
    private val service: TastieApiService,
) : CategoryDataSource {
    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }
}
