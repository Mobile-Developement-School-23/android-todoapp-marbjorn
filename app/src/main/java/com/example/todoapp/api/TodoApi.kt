package com.example.todoapp.api

import android.util.Log
import com.example.todoapp.storage.TodoListData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.*


interface TodoApi {

    /*@Headers("X-Last-Known-Revision", "3")*/

    @GET("list") //get list of TodoItems
    suspend fun getListOfItems() : TodoListData // TodoListData

    @PATCH("list") //compare list
    suspend fun patchListOfItems(@Header("X-Last-Known-Revision") revision : Int,
                                 @Body list : TodoListData) : TodoListData //: TodoListData

    @POST("list") //post a TodoItem
    suspend fun addItem(@Header("X-Last-Known-Revision") revision: Int,
                        @Body todoItemWrapper: TodoItemWrapper ) : TodoItemWrapper

    @GET("list/{id}") //get a TodoItem
    suspend fun getItem(@Path("id") id : String) : TodoItemWrapper

    @PUT("list/{id}") //change a TodoItem
    suspend fun changeItem(@Header("X-Last-Known-Revision") revision: Int, @Path("id") id : String, @Body todoItemWrapper: TodoItemWrapper) : TodoItemWrapper

    @DELETE("list/{id}") //delete a TodoItem
    suspend fun deleteItem(@Header("X-Last-Known-Revision") revision : Int,
                           @Path("id") id : String) : TodoItemWrapper

}

inline suspend fun <T> safeApiCall(apiCall: () -> T): NetworkResponse<T> {
    return try {
        NetworkResponse.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        Log.d("ERROR", throwable.toString())
        when (throwable) {
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = throwable.message()
                NetworkResponse.Error(code, errorResponse)
            }
            else -> {
                NetworkResponse.Error(null, null)
            }
        }
    }
}