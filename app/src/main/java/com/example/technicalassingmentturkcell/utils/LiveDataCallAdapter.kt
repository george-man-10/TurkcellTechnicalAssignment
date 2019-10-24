package com.example.technicalassingmentturkcell.utils

import androidx.lifecycle.LiveData
import com.example.technicalassingmentturkcell.network.responses.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            override fun onActive() {
                super.onActive()
                call.enqueue(object : Callback<R> {
                    override fun onFailure(call: Call<R>, t: Throwable) {
                        postValue(ApiResponse.ApiErrorResponse(t.message!!))
                    }

                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        postValue(ApiResponse.ApiSuccessResponse(response.body()!!))
                    }
                })
            }
        }
    }

    override fun responseType() = responseType
}

