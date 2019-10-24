package com.example.technicalassingmentturkcell.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.technicalassingmentturkcell.model.room.ProductsDao
import com.example.technicalassingmentturkcell.network.models.Product

const val TAG = "DatabaseRepositoryImpl"

class DatabaseRepositoryImpl(private val productsDao: ProductsDao) : IRepository.DatabaseRepository {


    override fun getProductsListFromDB(): LiveData<List<Product>> {
        Log.d(TAG, "getProductsListFromDB called")
        return productsDao.getProducts()
    }

    override fun insetProductsToDB(products: List<Product>) {
        products.forEach {
            Log.d(TAG, "insetProductsToDB: $it")
            productsDao.insertProduct(it)
        }
    }
}