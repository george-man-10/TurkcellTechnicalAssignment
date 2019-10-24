package com.example.technicalassingmentturkcell.repositories

import androidx.lifecycle.LiveData
import com.example.technicalassingmentturkcell.network.ProductsApi
import com.example.technicalassingmentturkcell.network.responses.ApiResponse
import com.example.technicalassingmentturkcell.network.responses.ProductListResponse

class NetworkProductsListRepositoryImpl : IRepository.NetworkRepository {

    override fun getProductsListFromApi(): LiveData<ApiResponse<ProductListResponse>> {
        return ProductsApi.get().getProductsList()
    }

}