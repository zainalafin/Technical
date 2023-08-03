package com.example.myapp.portofolio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.portofolio.util.JsonUtil.readJsonFromAssets
import com.example.myapp.portofolio.util.parseMonthPortofolio
import com.example.myapp.portofolio.model.*
import com.example.myapp.portofolio.ui.DonutChart
import com.example.myapp.portofolio.ui.theme.PortofolioTheme
import com.example.myapp.portofolio.util.*

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortofolioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    MyApp()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Composable
fun MyApp() {
    val context = LocalContext.current
    val json = remember { readJsonFromAssets(context, "userPortofolio.json") }
    val monthPortofolio = remember { parseMonthPortofolio(json.getJSONObject("monthPorto")) }
    var scale by remember { mutableStateOf(1f) }
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Portofolio") }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, gestureZoom, _ ->
                        scale *= gestureZoom
                    }
                }
        ) {
            // Gunakan NavHost untuk mengatur navigasi antar halaman
            NavHost(navController, startDestination = "main_screen") {
                composable("main_screen") {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Data Portofolio Bulan Ini")
                                DonutChart(monthPortofolio, scale = 0.6f) { newScale ->
                                    scale = newScale
                                }

                                // Tombol "Tarik Tunai" untuk berpindah ke halaman histori
                                Button(onClick = {
                                    navController.navigate("history_screen")
                                }) {
                                    Text("Tarik Tunai")
                                }
                                // Tombol "Top Up Gopay" untuk berpindah ke halaman Top Up
                                Button(onClick = {
                                    navController.navigate("topup_screen")
                                }) {
                                    Text("Top Up Gopay")
                                }
                                // Tombol "QRIS Payment" untuk berpindah ke halaman pembayaran QRIS
                                Button(onClick = {
                                    navController.navigate("qris_payment_screen")
                                }) {
                                    Text("QRIS Payment")
                                }
                                Button(onClick = {
                                    navController.navigate("lainya_screen")
                                }) {
                                    Text("Lainya")
                                }
                            }
                        }
                    }
                }
                composable("history_screen") {
                    // Berpindah ke composable HistoryScreen
                    HistoryScreen(navController)
                }
                composable("topup_screen") {
                    // Berpindah ke composable TopUpScreen (Jika belum dibuat, buat composable TopUpScreen terlebih dahulu)
                    TopUpScreen(navController)
                }
                composable("qris_payment_screen") {
                    // Berpindah ke composable QRISPaymentScreen (Jika belum dibuat, buat composable QRISPaymentScreen terlebih dahulu)
                    QRISPaymentScreen(navController)
                }
                composable("lainya_screen") {
                    // Berpindah ke composable TransferScreen (Jika belum dibuat, buat composable TransferScreen terlebih dahulu)
                    LainyaScreen(navController)
                }

            }
        }
    }
}

@Composable
fun LainyaScreen(navController: NavHostController) {

}

@Composable
fun QRISPaymentScreen(navController: NavHostController) {

}

@Composable
fun TopUpScreen(navController: NavHostController) {

}

@Composable
fun HistoryScreen(navController: NavHostController) {

}
