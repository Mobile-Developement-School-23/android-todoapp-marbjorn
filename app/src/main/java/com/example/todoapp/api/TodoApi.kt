package com.example.todoapp.api

import com.example.todoapp.storage.TodoListData
import retrofit2.http.*


interface TodoApi {

    /*@Headers("X-Last-Known-Revision", "3")*/

    @GET("list") //get list of TodoItems
    suspend fun getListOfItems() : TodoListData

    @PATCH("list") //update list
    suspend fun patchListOfItems(@Header("X-Last-Known-Revision") revision : String, @Body list : TodoListData) : TodoListData

    @POST("list") //post a TodoItem
    suspend fun addItem(@Header("X-Last-Known-Revision") revision : String,
                        @Body todoItemWrapper: TodoItemWrapper ) : TodoItemWrapper

    @GET("list/{id}") //get a TodoItem
    suspend fun getItem(@Path("id") id : String) : TodoItemWrapper

    @PUT("list/{id}") //change a TodoItem
    suspend fun changeItem(@Path("id") id : String, ) : TodoItemWrapper

    @DELETE("list/{id}") //delete a TodoItem
    suspend fun deleteItem(@Header("X-Last-Known-Revision") revision : String,
                           @Path("id") id : String) : TodoItemWrapper
}