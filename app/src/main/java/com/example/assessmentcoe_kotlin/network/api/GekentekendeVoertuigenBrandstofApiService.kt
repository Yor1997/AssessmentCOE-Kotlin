package com.example.assessmentcoe_kotlin.network.api

import com.example.assessmentcoe_kotlin.model.VoertuigBrandstof
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GekentekendeVoertuigenBrandstofApiService {
    @GET("8ys7-d773.json")
    fun getVoertuigBrandstofDetails(@Query("kenteken") kenteken: String): Call<List<VoertuigBrandstof>>
}