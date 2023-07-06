package com.example.todoapp.api

import com.example.todoapp.storage.TodoListData
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


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
    suspend fun changeItem(@Header("X-Last-Known-Revision") revision: Int,
                           @Path("id") id : String,
                           @Body todoItemWrapper: TodoItemWrapper) : TodoItemWrapper

    @DELETE("list/{id}") //delete a TodoItem
    suspend fun deleteItem(@Header("X-Last-Known-Revision") revision : Int,
                           @Path("id") id : String) : TodoItemWrapper
}

inline fun <T> safeApiCall(apiCall: () -> T): NetworkResponse<T> {
    return try {
        NetworkResponse.Success(apiCall.invoke())
    } catch (e : HttpException) {
                val code = e.code()
                val errorResponse = e.message()
                NetworkResponse.Error(code, errorResponse)
    } catch (_ : Exception) {
        NetworkResponse.Error(null, null)
    }
}