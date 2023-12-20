package com.example.assessmentcoe_kotlin.network.api

import com.example.assessmentcoe_kotlin.model.Voertuig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GekentekendeVoertuigenApiService {
    @GET("m9d7-ebf2.json")
    fun getVoertuigDetails(@Query("kenteken") kenteken: String): Call<List<Voertuig>>
}