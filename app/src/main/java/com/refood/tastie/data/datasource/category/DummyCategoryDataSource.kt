package com.refood.tastie.data.datasource.category

import com.refood.tastie.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategories(): List<Category> {
        return mutableListOf(
            Category(
                name = "Nasi",
                imageUrl = "https://github.com/renn27/refood-assets/blob/main/category_img/icons8-rice-64.png?raw=true"
            ), Category(
                name = "Mie",
                imageUrl = "https://github.com/renn27/refood-assets/blob/main/category_img/icons8-noodles-64.png?raw=true"
            ), Category(
                name = "Snack",
                imageUrl = "https://github.com/renn27/refood-assets/blob/main/category_img/icons8-popcorn-64.png?raw=true"
            ), Category(
                name = "Minuman",
                imageUrl = "https://github.com/renn27/refood-assets/blob/main/category_img/icons8-mulled-wine-64.png?raw=true"
            ), Category(
                name = "Kue",
                imageUrl = "https://github.com/renn27/refood-assets/blob/main/category_img/ic_cake.png?raw=true"
            )
        )
    }
}