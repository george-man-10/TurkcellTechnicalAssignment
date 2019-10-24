package com.example.technicalassingmentturkcell.interactors

import androidx.lifecycle.MediatorLiveData
import com.example.technicalassingmentturkcell.network.models.Product
import com.example.technicalassingmentturkcell.utils.Resource

interface IInteractor {
    fun fetchData()
    fun loadFromDB()
    fun shouldFetch(): Boolean
    interface IInteractorResult {
        fun getFetchResult(): MediatorLiveData<Resource<List<Product>>>
    }
}