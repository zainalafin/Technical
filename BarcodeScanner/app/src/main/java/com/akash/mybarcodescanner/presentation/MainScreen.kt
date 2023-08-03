package com.akash.mybarcodescanner.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()

    // Simpan status terbuka atau tertutupnya history menggunakan remember
    val showHistory = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Details: ${state.value.details}")
        }
        // Tampilkan saldo di bawah tombol
        Text(text = "Saldo: ${state.value.saldo}")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(onClick = { viewModel.startScanning() }) {
                Text(text = "Mulai Pindai")
            }
        }

        // Tampilkan history dalam button
        Button(onClick = { showHistory.value = !showHistory.value }) {
            Text(text = "History")
        }

        // Tampilkan pesan "Payment Berhasil" jika pemindaian berhasil
        if (state.value.scanSuccess) {
            Text(
                text = "Payment Berhasil",
                color = Color.Green,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Tampilkan history jika tombol "History" ditekan
        if (showHistory.value) {
            Column {
                Text(text = "History Pengurangan Saldo:")
                for (saldo in state.value.saldoHistory) {
                    Text(text = saldo.toString())
                }

                Text(text = "History Hasil Scan:")
                for (scanResult in state.value.scanHistory) {
                    Text(text = scanResult)
                }
            }
        }
    }
}

