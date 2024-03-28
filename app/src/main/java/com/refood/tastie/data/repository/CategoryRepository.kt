package com.refood.tastie.data.repository

import com.refood.tastie.data.datasource.category.CategoryDataSource
import com.refood.tastie.data.model.Category

interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategories()
}