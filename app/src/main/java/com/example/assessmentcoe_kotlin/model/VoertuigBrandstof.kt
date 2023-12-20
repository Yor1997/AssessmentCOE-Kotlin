package com.example.assessmentcoe_kotlin.model

data class VoertuigBrandstof(
    val kenteken: String,
    val brandstof_omschrijving: String,
    val brandstofverbruik_buiten: String,
    val brandstofverbruik_gecombineerd: String,
    val brandstofverbruik_stad: String,
    val co2_uitstoot_gecombineerd: String,
    val geluidsniveau_rijdend: String,
    val geluidsniveau_stationair: String,
    val nettomaximumvermogen: String,
    val uitlaatemissieniveau: String,
    val nominaal_continu_maximumvermogen: String,
    val elektrisch_verbruik_enkel_elektrisch_wltp: Int,
    val actie_radius_enkel_elektrisch_wltp: Int,
    val actie_radius_enkel_elektrisch_stad_wltp: Int,
    val netto_max_vermogen_elektrisch: Int
)