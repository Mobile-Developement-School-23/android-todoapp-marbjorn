package com.example.todoapp.api

import retrofit2.Response

open class NetworkResponse <out T> {
        data class Success<out T>(val data: T): NetworkResponse<T>()
        data class Error<T>(val code : Int?, val message : String?): NetworkResponse<T>()
    }
fun <T> Response<T>.parseResponse(): NetworkResponse<T> {
    return if (this.isSuccessful && this.body() != null) {
        val responseBody = this.body()
        NetworkResponse.Success(responseBody!!)
    } else {
        NetworkResponse.Error(this.code(), this.message())
    }
}