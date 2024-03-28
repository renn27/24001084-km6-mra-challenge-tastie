package com.refood.tastie.presentation.home

import androidx.lifecycle.ViewModel
import com.refood.tastie.data.repository.CategoryRepository
import com.refood.tastie.data.repository.MenuRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    fun getMenus() = menuRepository.getMenus()
    fun getCategories() = categoryRepository.getCategories()
}