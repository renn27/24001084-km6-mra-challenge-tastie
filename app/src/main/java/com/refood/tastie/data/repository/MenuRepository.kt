package com.refood.tastie.data.repository

import com.refood.tastie.data.datasource.menu.MenuDataSource
import com.refood.tastie.data.model.Menu

interface MenuRepository {
    fun getMenus(): List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(): List<Menu> = dataSource.getMenus()
}