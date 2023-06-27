package com.example.todoapp.api

import com.example.todoapp.storage.TodoItem
import retrofit2.Response
import retrofit2.http.*


interface TodoApi {

    /*@Headers("X-Last-Known-Revision", "3")*/

    @GET("list") //get list of TodoItems
    suspend fun getListOfItems() : TodoListData

    @PATCH("list") //update list
    suspend fun patchListOfItems(@Header("X-Last-Known-Revision") revision : String) : TodoListData

    @POST("list") //post a TodoItem
    suspend fun addItem(@Header("X-Last-Known-Revision") revision : String,
                        @Body todoItemResponseData: TodoItemResponseData ) : TodoItemResponseData

    @GET("list/{id}") //get a TodoItem
    suspend fun getItem(@Path("id") id : String) : TodoItemResponseData

    @PUT("list/{id}") //change a TodoItem
    suspend fun changeItem(@Path("id") id : String, ) : TodoItemResponseData

    @DELETE("list/{id}") //delete a TodoItem
    suspend fun deleteItem(@Header("X-Last-Known-Revision") revision : String,
                           @Path("id") id : String) : TodoItemResponseData
}