package com.example.todoapp.api

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val BASEURL = "https://beta.mrdekk.ru/todobackend/"
    //const val BASEURL = "https://eok2ml3k8whegye.m.pipedream.net/"
    const val token = "hoosier"

    val client = OkHttpClient.Builder().addInterceptor { chain ->
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