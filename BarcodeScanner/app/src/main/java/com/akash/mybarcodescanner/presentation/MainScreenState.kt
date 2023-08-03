package com.akash.mybarcodescanner.presentation
data class MainScreenState(
    val details: String = "",
    val saldo: Int = 100000,
    val saldoHistory: List<Int> = emptyList(),
    val scanHistory: List<String> = emptyList(),
    val scanSuccess: Boolean = false // Properti untuk menandai status pemindaian berhasil
)
