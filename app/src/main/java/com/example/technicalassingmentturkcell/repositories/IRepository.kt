package com.example.technicalassingmentturkcell.repositories

import androidx.lifecycle.LiveData
import com.example.technicalassingmentturkcell.network.models.Product
import com.example.technicalassingmentturkcell.network.responses.ApiResponse
import com.example.technicalassingmentturkcell.network.responses.ProductListResponse

interface IRepository {

    interface NetworkRepository {
        fun getProductsListFromApi(): LiveData<ApiResponse<ProductListResponse>>
    }

    interface DatabaseRepository {
        fun getProductsListFromDB(): LiveData<List<Product>>
        fun insetProductsToDB(products: List<Product>)
    }
}