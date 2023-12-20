package com.example.assessmentcoe_kotlin.feature.compare

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assessmentcoe_kotlin.ui.CarDetailsCard
import com.example.assessmentcoe_kotlin.ui.CombinedCarDetailsCard
import com.example.assessmentcoe_kotlin.ui.CustomAppBar
import com.example.assessmentcoe_kotlin.network.api.GekentekendeVoertuigenApiService
import com.example.assessmentcoe_kotlin.network.api.GekentekendeVoertuigenBrandstofApiService
import com.example.assessmentcoe_kotlin.model.Voertuig
import com.example.assessmentcoe_kotlin.model.VoertuigBrandstof
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Call

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://opendata.rdw.nl/resource/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

private val gekentekendeVoertuigenApiService = retrofit.create(GekentekendeVoertuigenApiService::class.java)
private val gekentekendeVoertuigenBrandstofApiService = retrofit.create(
    GekentekendeVoertuigenBrandstofApiService::class.java)

@Composable
fun CompareScreen(lazyRowItems: List<String>, onNavigateBack: () -> Unit) {
    var apiDataList by remember { mutableStateOf<List<Voertuig>>(emptyList()) }
    var apiBrandstofDataList by remember { mutableStateOf<List<VoertuigBrandstof>>(emptyList()) }

    LaunchedEffect(Unit) {
        lazyRowItems.forEach { item ->
            makeApiCall(item) { voertuigList ->
                apiDataList = apiDataList + voertuigList.orEmpty()

                if (voertuigList?.isNotEmpty() == true) {
                    makeBrandstofApiCall(item) { voertuigBrandstofList ->
                        apiBrandstofDataList = apiBrandstofDataList + voertuigBrandstofList.orEmpty()
                    }
                }
            }
        }
    }

    apiDataList = apiDataList.sortedByDescending { it.massa_ledig_voertuig }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomAppBar.AppBar(title = "RDW auto vergelijker", onNavigateBack = onNavigateBack)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(apiDataList.sortedByDescending { it.massa_ledig_voertuig }) { voertuig ->
                val voertuigBrandstof = apiBrandstofDataList.find { it.kenteken == voertuig.kenteken }
                if (voertuigBrandstof != null) {
                    CombinedCarDetailsCard(voertuig, voertuigBrandstof, isFirstCard = voertuig == apiDataList.first())
                } else {
                    CarDetailsCard(voertuig, isFirstCard = voertuig == apiDataList.first())
                }
            }
        }
    }
}

fun makeApiCall(textValue: String, onResult: (List<Voertuig>?) -> Unit) {
    val call = gekentekendeVoertuigenApiService.getVoertuigDetails(textValue)

    call.enqueue(object : retrofit2.Callback<List<Voertuig>> {
        override fun onResponse(
            call: Call<List<Voertuig>>,
            response: retrofit2.Response<List<Voertuig>>
        ) {
            if (response.isSuccessful) {
                val voertuigList = response.body()
                onResult(voertuigList)
            } else {
                println("Error: ${response.code()} - ${response.message()}")
                println("API Address: ${call.request().url()}")
                onResult(null)
            }
        }

        override fun onFailure(call: Call<List<Voertuig>>, t: Throwable) {
            println("Network failure: ${t.message}")
            println("API Address: ${call.request().url()}")
            onResult(null)
        }
    })
}

fun makeBrandstofApiCall(textValue: String, onResult: (List<VoertuigBrandstof>?) -> Unit) {
    val call = gekentekendeVoertuigenBrandstofApiService.getVoertuigBrandstofDetails(textValue)

    println("Making Brandstof API call for kenteken: $textValue")

    call.enqueue(object : retrofit2.Callback<List<VoertuigBrandstof>> {
        override fun onResponse(
            call: Call<List<VoertuigBrandstof>>,
            response: retrofit2.Response<List<VoertuigBrandstof>>
        ) {
            if (response.isSuccessful) {
                val voertuigBrandstofList = response.body()
                println("Brandstof API Response: $voertuigBrandstofList")
                println("API Address: ${call.request().url()}")
                onResult(voertuigBrandstofList)
            } else {
                println("Error: ${response.code()} - ${response.message()}")
                println("API Address: ${call.request().url()}")
                onResult(null)
            }
        }

        override fun onFailure(call: Call<List<VoertuigBrandstof>>, t: Throwable) {
            println("Network failure: ${t.message}")
            println("API Address: ${call.request().url()}")
            onResult(null)
        }
    })
}