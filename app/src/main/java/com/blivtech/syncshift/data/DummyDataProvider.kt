package com.blivtech.syncshift.data

import com.blivtech.syncshift.Product
import com.blivtech.syncshift.ShopModel

object DummyDataProvider {

    fun getRandomProductList(): List<ProductModel> {
        val list = mutableListOf<ProductModel>()

        for (i in 1..30) {
            list.add(
                ProductModel(
                    productName = "Product $i",
                    productMrp = (50..500).random()
                )
            )
        }
        return list
    }

    fun getRandomShopList(): List<ShopModel> {
        val list = mutableListOf<ShopModel>()

        for (i in 1..10) {
            list.add(ShopModel("Shop $i"))
        }
        return list
    }
}