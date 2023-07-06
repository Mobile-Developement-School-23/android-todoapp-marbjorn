package com.example.todoapp.api

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASEURL = "https://beta.mrdekk.ru/todobackend/"
    private const val token = "hoosier"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()

    val api : TodoApi by lazy{
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(TodoApi::class.java)
    }
}