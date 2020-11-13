package com.example.exchangerates.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExchangeRates(
    val bank: String,
    val baseCurrency: Int,
    val baseCurrencyLit: String,
    val date: String,
    val exchangeRate: List<ExchangeRate>
): Parcelable
