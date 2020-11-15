package com.example.exchangerates.shared_preferences

import android.annotation.SuppressLint
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SharedPreferencesUtil @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE)

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("dd.MM.yyy")
    private val currentDateAndTime: String = simpleDateFormat.format(Date())

    fun saveDate(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getDate(key: String): String {
        return sharedPreferences.getString(key, currentDateAndTime).toString()
    }
}