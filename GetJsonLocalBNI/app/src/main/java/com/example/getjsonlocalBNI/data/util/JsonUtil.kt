package com.example.getjsonlocalBNI.data.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtil {
    fun readJsonFromAssets(fileName: String, context: Context): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun fromJson(jsonString: String): List<PromoItem> {
        val listType = object : TypeToken<List<PromoItem>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}