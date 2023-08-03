package com.example.myapp.portofolio.util

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.myapp.portofolio.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import kotlin.random.Random

fun readJsonFromAssets(context: Context, fileName: String): JSONObject {
    val json = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return JSONObject(json)
}

fun parseMonthPortofolio(json: JSONObject): MonthPortofolio {
    val type = json.getString("type")
    val dataArray = json.getJSONArray("data")
    val dataList = mutableListOf<PortofolioData>()

    for (i in 0 until dataArray.length()) {
        val dataObj = dataArray.getJSONObject(i)
        val label = dataObj.getString("label")
        val percentage = dataObj.getString("percentage").toFloat() // Change to Float
        val transactionArray = dataObj.getJSONArray("data")
        val transactionList = mutableListOf<TransactionData>()

        for (j in 0 until transactionArray.length()) {
            val transactionObj = transactionArray.getJSONObject(j)
            val trxDate = transactionObj.getString("trx_date")
            val nominal = transactionObj.getInt("nominal")
            val transactionData = TransactionData(trxDate, nominal)
            transactionList.add(transactionData)
        }
        val color = when (label) {
            "Tarik Tunai" -> Color(android.graphics.Color.parseColor("#E81029"))
            "QRIS Payment" -> Color(android.graphics.Color.parseColor("#E89510")) // Warna cerah dengan kode hex
            "Topup Gopay" -> Color(android.graphics.Color.parseColor("#2105F5"))
            "Lainnya" -> Color(android.graphics.Color.parseColor("#01803E"))
            else -> Color.White
        }

        // Buat objek PortofolioData dengan data yang telah diparsing
        val portofolioData = PortofolioData(label, percentage, transactionList, color)
        dataList.add(portofolioData)
    }

    return MonthPortofolio(type, dataList)
}


fun parseTarikTunaiTransactions(json: JSONObject): List<TarikTunaiTransaction> {
    val jsonArray = json.getJSONArray("data")
    val transactions = mutableListOf<TarikTunaiTransaction>()

    for (i in 0 until jsonArray.length()) {
        val item = jsonArray.getJSONObject(i)
        val trxDate = item.getString("trx_date")
        val nominal = item.getInt("nominal")
        transactions.add(TarikTunaiTransaction(trxDate, nominal))
    }

    return transactions
}

object JsonUtil {
    fun readJsonFromAssets(fileName: String, context: Context): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun fromJson(jsonString: String): List<TransactionData> {
        val listType = object : TypeToken<List<TransactionData>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}