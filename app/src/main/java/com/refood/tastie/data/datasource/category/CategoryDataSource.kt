package com.refood.tastie.data.datasource.category

import com.refood.tastie.data.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}