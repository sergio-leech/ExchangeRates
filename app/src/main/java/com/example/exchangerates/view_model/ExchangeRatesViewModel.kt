package com.example.exchangerates.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.api.PrivatBankService
import com.example.exchangerates.model.ExchangeRate
import kotlinx.coroutines.launch

class ExchangeRatesViewModel @ViewModelInject constructor(private val privatBankService: PrivatBankService) :
    ViewModel() {

    private val _exchangeRatesList = MutableLiveData<List<ExchangeRate>>()
    val exchangeRatesList: LiveData<List<ExchangeRate>> = _exchangeRatesList

    init {
        getListResult()
    }

    private fun getListResult() {
        viewModelScope.launch {
            try {
                _exchangeRatesList.value =
                    privatBankService.getExchangeRatesListAsync("01.12.2017").await().exchangeRate

            } catch (e: Exception) {
                e.message
            }
        }
    }

}