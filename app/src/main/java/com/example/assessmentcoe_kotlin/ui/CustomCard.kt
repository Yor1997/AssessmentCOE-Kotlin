package com.example.assessmentcoe_kotlin.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assessmentcoe_kotlin.model.Voertuig
import com.example.assessmentcoe_kotlin.model.VoertuigBrandstof
import com.example.assessmentcoe_kotlin.ui.theme.RdwRed

@Composable
fun CustomCard(voertuig: Voertuig, isFirstCard: Boolean, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = if (isFirstCard) BorderStroke(2.dp, RdwRed) else null
    ) {
        content()
    }
}

@Composable
fun CarDetailsCard(voertuig: Voertuig, isFirstCard: Boolean) {
    CustomCard(voertuig, isFirstCard) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Kenteken: ${voertuig.kenteken}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Merk: ${voertuig.merk}")
            Text(text = "Model: ${voertuig.handelsbenaming}")
            Text(text = "Soort: ${voertuig.inrichting}")
            Text(text = "Aantal zitplaatsen: ${voertuig.aantal_zitplaatsen}")
            Text(text = "Kleur: ${voertuig.eerste_kleur}")
            Text(text = "Massa: ${voertuig.massa_ledig_voertuig}")
            Text(text = "Aantal deuren: ${voertuig.aantal_deuren}")
        }
    }
}

@Composable
fun CombinedCarDetailsCard(voertuig: Voertuig, voertuigBrandstof: VoertuigBrandstof, isFirstCard: Boolean) {
    CustomCard(voertuig, isFirstCard) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Kenteken: ${voertuig.kenteken}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Soort: ${voertuig.voertuigsoort}")
            Text(text = "Merk: ${voertuig.merk}")
            Text(text = "Brandstof: ${voertuigBrandstof.brandstof_omschrijving}")
            Text(text = "Brandstofverbruik (gecombineerd): ${voertuigBrandstof.brandstofverbruik_gecombineerd}")
        }
    }
}