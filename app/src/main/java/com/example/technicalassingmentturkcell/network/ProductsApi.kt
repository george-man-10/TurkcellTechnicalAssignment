package com.example.technicalassingmentturkcell.network

import androidx.lifecycle.LiveData
import com.example.technicalassingmentturkcell.BuildConfig
import com.example.technicalassingmentturkcell.network.models.Product
import com.example.technicalassingmentturkcell.network.responses.ApiResponse
import com.example.technicalassingmentturkcell.network.responses.ProductListResponse
import com.example.technicalassingmentturkcell.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsApi {
    @GET("list")
    fun getProductsList(): LiveData<ApiResponse<ProductListResponse>>

    @GET("{product_id}/detail")
    fun getProductDetails(
        @Path("product_id") id: Int
    ): LiveData<ApiResponse<Product>>

    companion object {
        fun get(): ProductsApi {
            return Retrofit
                .Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductsApi::class.java)
        }
    }
}

