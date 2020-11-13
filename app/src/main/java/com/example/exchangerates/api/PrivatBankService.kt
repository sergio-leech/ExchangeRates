package com.example.exchangerates.api

import com.example.exchangerates.model.ExchangeRates
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface PrivatBankService {

    @GET("p24api/exchange_rates?json")
    fun getExchangeRatesListAsync(
        @Query("date") date: String
    ): Deferred<ExchangeRates>
}
