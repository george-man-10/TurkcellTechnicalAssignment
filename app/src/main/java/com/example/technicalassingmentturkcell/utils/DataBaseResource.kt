package com.example.technicalassingmentturkcell.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.technicalassingmentturkcell.AppExecutors
import com.example.technicalassingmentturkcell.network.responses.ApiResponse

const val LOG = "NetworkBoundResource"

abstract class DataBaseResource<CacheObject, RequestObject>(executors: AppExecutors) {
    // Called to save the result of the API response into the database
    private val appExecutors: AppExecutors = executors
    private val result: MediatorLiveData<Resource<CacheObject>> = MediatorLiveData()

    init {
        //update LiveData for loading status
        //observe LiveData source from dataBase
        val dataBaseSource = loadFromDb()
        result.addSource(dataBaseSource) { data ->
            if (shouldFetch(data)) {
                //get data from Network
                fetchFromNetwork(dataBaseSource)
            } else {
                setValue(Resource.Success(data))
            }
        }
    }

    //1.observe local db
    //2. if<condition> query the network
    //3. start observing local data base
    //4.insert new data into local base
    //5.begin observing local db again and refresh data comes from network
    private fun fetchFromNetwork(databaseSource: LiveData<CacheObject>) {
        Log.d(LOG, "fetchFromNetwork: called")
        //update Live Data for loading status
//        result.addSource(databaseSource) { data -> setValue(Resource.Loading(data)) }
        val apiResponse: LiveData<ApiResponse<RequestObject>> = createCall()
        result.addSource(apiResponse) { it ->
            when (it) {
                is ApiResponse.ApiSuccessResponse -> {
                    Log.d(LOG, "onChanged: ApiSuccessResponse")
                    appExecutors.getDiskIO().execute {
                        //save response to the data base
                        saveCallResult(processResponse(it) as RequestObject)
                        appExecutors.getMainThreadExecutor().execute {
                            result.addSource(loadFromDb()) { setValue(Resource.Success(it)) }
                        }
                    }
                }
                is ApiResponse.ApiEmptyResponse -> {
                    Log.d(LOG, "onChanged: ApiEmptyResponse")
                    appExecutors.getMainThreadExecutor().execute {
                        result.addSource(loadFromDb()) { setValue(Resource.Success(it)) }
                    }
                }
                is ApiResponse.ApiErrorResponse -> {
                    Log.d(LOG, "onChanged: ApiErrorResponse")
                    result.addSource(databaseSource) {
                        setValue(Resource.Error(it, it.toString()))
                    }
                }
            }
        }
    }

    private fun processResponse(response: ApiResponse.ApiSuccessResponse<RequestObject>): CacheObject {
        return response.data as CacheObject
    }

    private fun setValue(newValue: Resource<CacheObject>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestObject)

    @MainThread
    protected abstract fun shouldFetch(data: CacheObject?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<CacheObject>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestObject>>

    fun asLiveData(): LiveData<Resource<CacheObject>> = result
}
