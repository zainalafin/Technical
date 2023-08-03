/*
package com.example.myapp.portofolio.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.myapp.portofolio.model.PortofolioData

@Composable
fun TransactionDetail(portofolioData: PortofolioData, onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(portofolioData.label) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = {
                // Show transaction label and percentage
                Text(text = "Transaction Label: ${portofolioData.label}")
                Text(text = "Transaction Percentage: ${portofolioData.percentage}")

                // Show transaction history
                Text(text = "Transaction History:")
                LazyColumn {
                    items(portofolioData.data) { transaction ->
                        Text(text = "Date: ${transaction.trx_date}, Nominal: ${transaction.nominal}")
                    }
                }
            }
        )
    }
}
*/
