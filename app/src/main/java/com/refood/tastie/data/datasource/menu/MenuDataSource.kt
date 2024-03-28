package com.refood.tastie.data.datasource.menu

import com.refood.tastie.data.model.Menu

interface MenuDataSource {
    fun getMenus(): List<Menu>
}