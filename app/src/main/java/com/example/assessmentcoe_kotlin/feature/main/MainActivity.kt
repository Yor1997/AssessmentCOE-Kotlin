package com.example.assessmentcoe_kotlin.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assessmentcoe_kotlin.R
import com.example.assessmentcoe_kotlin.feature.compare.CompareScreen
import com.example.assessmentcoe_kotlin.ui.theme.AssessmentCOEKotlinTheme
import com.example.assessmentcoe_kotlin.util.ValidationUtils
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            AssessmentCOEKotlinTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreenContent(navController = navController)
                    }
                    composable(
                        "compare/{lazyRowItems}",
                        arguments = listOf(navArgument("lazyRowItems") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val lazyRowItems = backStackEntry.arguments?.getString("lazyRowItems")?.split(",") ?: emptyList()
                        CompareScreen(
                            lazyRowItems = lazyRowItems,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreenContent(navController: NavController) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    var lazyRowItems by remember { mutableStateOf<List<String>>(emptyList()) }
    var isErrorVisible by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    fun addToLazyRowItems() {
        if (text.text.isEmpty()) {
            isErrorVisible = true
            errorText = "Invoerveld is leeg!"
        } else {
            val cleanedLicence = text.text.replace("-", "").uppercase(Locale.ROOT)

            if (!ValidationUtils.isValidFormat(cleanedLicence)) {
                isErrorVisible = true
                errorText = "Het ingevoerde kenteken is geen geldig Nederlands kenteken!"
            } else {
                lazyRowItems = lazyRowItems + cleanedLicence
                text = TextFieldValue()
                isErrorVisible = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.rdw_red))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.rdw),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "RDW auto vergelijker",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 32.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 10.dp)
            ) {
                TextField(
                    value = text.text,
                    onValueChange = {
                        text = TextFieldValue(it.uppercase(Locale.ROOT))
                        isErrorVisible = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black),
                    label = { Text("Kenteken invoeren") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            addToLazyRowItems()
                        }
                    )
                )
            }

            if (isErrorVisible) {
                Text(
                    text = errorText,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.padding(start = 32.dp, top = 4.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 32.dp)
            ) {
                Button(
                    onClick = {
                        addToLazyRowItems()
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(0.1.dp, Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                ) {
                    Text(text = "Toevoegen")
                }

                Button(
                    onClick = {
                        if (lazyRowItems.size < 2) {
                            isErrorVisible = true
                            errorText = "U moet minimaal twee kentekens ingeven om te kunnen vergelijken!"
                        } else {
                            navController.navigate("compare/${lazyRowItems.joinToString(",")}")
                        }
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(0.1.dp, Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(text = "Vergelijken")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White)
            ) {
                item {
                    Text(
                        text = "Te vergelijken kentekens",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                    )

                    Divider(
                        color = Color.Black,
                        thickness = 0.3.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                items(lazyRowItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier
                                .weight(1f)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Kenteken verwijderen",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    lazyRowItems = lazyRowItems - item
                                }
                        )
                    }

                    if (lazyRowItems.lastOrNull() != item) {
                        Divider(
                            color = Color.Black,
                            thickness = 0.3.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}