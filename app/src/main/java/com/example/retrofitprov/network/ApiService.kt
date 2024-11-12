package com.example.retrofitprov.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("list_propinsi.json?print=pretty")
    fun getProvinsi(): Call<Map<String, String>>

}