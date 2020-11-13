package com.example.exchangerates.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExchangeRate(
    var baseCurrency: String,
    val currency: String,
    val purchaseRate: Double,
    val purchaseRateNB: Double,
    val saleRate: Double,
    val saleRateNB: Double
): Parcelable
