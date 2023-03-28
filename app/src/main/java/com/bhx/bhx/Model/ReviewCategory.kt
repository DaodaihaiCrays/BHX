package com.bhx.bhx.Model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewCategory (val id: Int, val name: String, val countProducts: Int, val products: List<Product>)