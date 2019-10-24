package com.example.technicalassingmentturkcell.interactors

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.technicalassingmentturkcell.AppExecutors
import com.example.technicalassingmentturkcell.glide.DispatchingProgressManager
import com.example.technicalassingmentturkcell.model.room.ProductsDao
import com.example.technicalassingmentturkcell.network.models.Product
import com.example.technicalassingmentturkcell.network.responses.ApiResponse
import com.example.technicalassingmentturkcell.repositories.DatabaseRepositoryImpl
import com.example.technicalassingmentturkcell.repositories.NetworkProductsListRepositoryImpl
import com.example.technicalassingmentturkcell.utils.Resource

const val TAG = "ProductsListInteractor"

class ProductsListInteractorImpl(productDao: ProductsDao) : IInteractor {
    private val result: MediatorLiveData<Resource<List<Product>>> = MediatorLiveData()
    private val networkRepository = NetworkProductsListRepositoryImpl()
    private val dataBaseRepository = DatabaseRepositoryImpl(productDao)

    override fun fetchData() {
        if (shouldFetch()) {
            doApiRequest()
        } else {
            loadFromDB()
        }
    }

    private fun doApiRequest() {
        val networkRepositorySource = networkRepository.getProductsListFromApi()
        result.addSource(networkRepositorySource) {
            result.removeSource(networkRepositorySource)
            when (it) {
                is ApiResponse.ApiSuccessResponse -> {
                    Log.d(TAG, "onChanged: ApiSuccessResponse")
                    AppExecutors.getExecutors().getDiskIO().execute {
                        Log.d(TAG, "Insert into Dist Thread: "+ Thread.currentThread().name)
                        it.data?.let {
                            dataBaseRepository.insetProductsToDB(it.products)
                            AppExecutors.getExecutors().getMainThreadExecutor().execute {
                                Log.d(TAG, "Add to the mediatorLiveData Thread: "+ Thread.currentThread().name)
                                result.addSource(dataBaseRepository.getProductsListFromDB(),
                                    Observer {
                                        setValue(Resource.Success(it))
                                    })
                            }
                        }
                    }
                }
                is ApiResponse.ApiEmptyResponse -> {
                    Log.d(TAG, "onChanged: ApiEmptyResponse")
                    AppExecutors.getExecutors().getMainThreadExecutor().execute {
                        Log.d(TAG, "EmptyApiResponse thread: "+ Thread.currentThread().name)
                        result.addSource(dataBaseRepository.getProductsListFromDB(),
                            Observer {
                                setValue(Resource.Success(it))
                            })
                    }
                }
                is ApiResponse.ApiErrorResponse -> {
                    Log.d(TAG, "onChanged: ApiErrorResponse")
                    result.addSource(dataBaseRepository.getProductsListFromDB()) {
                        Log.d(TAG, "Error Response thread: "+ Thread.currentThread().name)
                        setValue(Resource.Error(it, it.toString()))
                    }
                }
            }
        }
    }

    private fun setValue(newValue: Resource<List<Product>>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun getFetchResult(): MediatorLiveData<Resource<List<Product>>> = result

    override fun loadFromDB() {
        AppExecutors.getExecutors().getMainThreadExecutor().execute {
           result.addSource(dataBaseRepository.getProductsListFromDB()){
               setValue(Resource.Success(it))
           }
        }
    }

    override fun shouldFetch(): Boolean {
        //TODO Implement some logic
        return true
    }
}