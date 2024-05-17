package com.refood.tastie.data.repository

import com.refood.tastie.data.datasource.category.CategoryDataSource
import com.refood.tastie.data.mapper.toCategories
import com.refood.tastie.data.model.Category
import com.refood.tastie.utils.ResultWrapper
import com.refood.tastie.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategories().data.toCategories() }
    }
}
