package com.example.technicalassingmentturkcell.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.technicalassingmentturkcell.interactors.ProductsListInteractorImpl
import com.example.technicalassingmentturkcell.model.room.ProductDatabase
import com.example.technicalassingmentturkcell.model.room.ProductsDao
import com.example.technicalassingmentturkcell.network.models.Product
import com.example.technicalassingmentturkcell.utils.Resource

class ProductsListViewModel(application: Application) : AndroidViewModel(application) {

    private val productsDao: ProductsDao = ProductDatabase
        .getDatabase(application.applicationContext)
        .getProductsDao()
    private val interactor = ProductsListInteractorImpl(productsDao)

    fun fetchProductsList() {
        interactor.fetchData()
    }

    fun getRequestResult(): MutableLiveData<Resource<List<Product>>> {
        return interactor.getFetchResult()
    }

}