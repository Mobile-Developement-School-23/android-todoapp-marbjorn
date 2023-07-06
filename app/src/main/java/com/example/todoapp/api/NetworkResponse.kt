package com.example.todoapp.api

open class NetworkResponse <out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error<T>(val code: Int?, val message: String?) : NetworkResponse<T>()
}