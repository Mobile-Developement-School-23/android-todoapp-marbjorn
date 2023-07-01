package com.example.todoapp.api

sealed class NetworkResponse <T>(var data : T? = null, val message : String? = null) {
    class Success<T>(data : T) : NetworkResponse<T>(data)
    class Error<T>(message : String?, data : T? = null) : NetworkResponse<T>(data)
    class Loading<T> : NetworkResponse<T>()
}