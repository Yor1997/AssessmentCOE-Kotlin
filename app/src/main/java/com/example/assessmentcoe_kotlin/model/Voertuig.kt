package com.example.assessmentcoe_kotlin.model

data class Voertuig(
    val kenteken: String,
    val voertuigsoort: String,
    val merk: String,
    val handelsbenaming: String,
    val inrichting: String,
    val aantal_zitplaatsen: Int,
    val eerste_kleur: String,
    val massa_ledig_voertuig: Int,
    val aantal_deuren: Int
)