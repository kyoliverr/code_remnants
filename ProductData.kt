package com.example.buscalog.data

import com.google.gson.annotations.SerializedName

data class Product(
    var id: Int,
    var name: String,
    var protocol: String,
    var manufacturer: Manufacturer,

)

data class Manufacturer(
    var id: Int,
    var name: String
)

data class NewProduct(
    val name: String,
    val protocol: String,
    val munufactory_id: Int,
    val manufactory_label: String? = null
) //ProductPayLoad

data class ProductSelect(
    val id: Int,
    val name: String
)