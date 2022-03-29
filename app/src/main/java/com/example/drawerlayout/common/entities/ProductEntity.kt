package com.example.drawerlayout.common.entities

data class ProductEntity(
    var _id: String?,
    var coverImage: String,
    var price: Int,
    var description: String,
    var stock: Int,
    var discount: Int,
    var productsImages: List<ImageEntity>,
    var name: String,
    var brand: String,
    var comments: List<CommentEntity>?
)
