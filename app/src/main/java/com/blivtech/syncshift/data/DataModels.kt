package com.blivtech.syncshift.data

data class ProductModel(
    val productName: String,
    val productMrp: Int
)

data class ShopModel(
    val shopName: String
)

data class CartItemModel(
    val productName: String,
    var productQty: Int,
    val productPrice: Int
)
