package com.example.technicalassingmentturkcell.network.responses

import com.example.technicalassingmentturkcell.network.models.Product
import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("products")
    val products: List<Product>
)
