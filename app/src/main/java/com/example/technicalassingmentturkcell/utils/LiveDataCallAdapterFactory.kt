package com.example.technicalassingmentturkcell.utils

import androidx.lifecycle.LiveData
import com.example.technicalassingmentturkcell.network.responses.ApiResponse
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class LiveDataCallAdapterFactory : Factory() {
    override fun get(
        returnType: Type?,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): CallAdapter<*, *>? {
        // Check #1 Make sure the CallAdapter is returning a type of LiveData
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        // Check #2 Type that LiveData is wrapping
        val observableType: Type = getParameterUpperBound(0, returnType as ParameterizedType?)
        // Check if it's of Type ApiResponse
        val rawObservableType: Type = getRawType(observableType)
        require(!(rawObservableType !== ApiResponse::class.java)) { "type must be a defined resource" }
        // Check #3
        // Check if ApiResponse is parameterized. AKA: Does ApiResponse<T> exist? (must wrap around T)
        // FYI: T is either ProductListResponse in this app. But T can be anything theoretically.
        require(observableType is ParameterizedType) { "resource must be parameterized" }
        // get the Response type. (ProductListResponse )
        val bodyType: Type =
            getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Type>(bodyType)
    }
}





