package com.akash.mybarcodescanner.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.mybarcodescanner.domain.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepo
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    private val _paymentSuccess = MutableStateFlow(false)
    val paymentSuccess = _paymentSuccess.asStateFlow()

    // Simpan status terbuka atau tertutupnya history menggunakan remember
    val showHistory = mutableStateOf(false)

    fun startScanning() {
        viewModelScope.launch {
            repo.startScanning().collect { barcode ->
                if (!barcode.isNullOrBlank()) {
                    val updatedSaldo = state.value.saldo - 50000
                    _state.value = state.value.copy(
                        details = barcode,
                        saldo = updatedSaldo,
                        saldoHistory = state.value.saldoHistory + listOf(updatedSaldo),
                        scanHistory = state.value.scanHistory + listOf(barcode),
                        scanSuccess = true // Setel properti scanSuccess ke true saat pemindaian berhasil
                    )
                }
            }
        }
    }

    // Implementasikan logika resetPaymentStatus() seperti sebelumnya jika diperlukan
    // Implementasikan logika toggleHistory() seperti sebelumnya jika diperlukan
}