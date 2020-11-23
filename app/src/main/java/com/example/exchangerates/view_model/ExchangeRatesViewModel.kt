package com.example.exchangerates.view_model

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.exchangerates.api.PrivatBankService
import com.example.exchangerates.model.ExchangeRate
import com.example.exchangerates.shared_preferences.Keys
import com.example.exchangerates.shared_preferences.SharedPreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ExchangeRatesViewModel @ViewModelInject constructor(
    private val privatBankService: PrivatBankService,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    @Assisted savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val datePrivat = savedStateHandle.get<String>("datePrivat")
    private val dateNBU = savedStateHandle.get<String>("dateNBU")

    private val _setDatePrivat = MutableLiveData<String>()
    val setDatePrivat: LiveData<String> = _setDatePrivat

    private val _setDateNBU = MutableLiveData<String>()
    val setDateNBU: LiveData<String> = _setDateNBU


    private val _exchangeRatesListPrivatBank = MutableLiveData<List<ExchangeRate>>()
    val exchangeRatesListPrivatBank: LiveData<List<ExchangeRate>> = _exchangeRatesListPrivatBank

    private val _exchangeRatesListNBU = MutableLiveData<List<ExchangeRate>>()
    val exchangeRatesListNBU: LiveData<List<ExchangeRate>> = _exchangeRatesListNBU
    val mutex = Mutex()

    init {
        getListResult()
        setDate()
    }

    private fun setDate() {
        _setDatePrivat.value = datePrivat ?: sharedPreferencesUtil.getDate(Keys.datePrivatKey)
        _setDateNBU.value = dateNBU ?: sharedPreferencesUtil.getDate(Keys.dateNbyKey)
    }

    private fun getListResult() {
        viewModelScope.launch(Dispatchers.IO) {
            mutex.withLock {
                _exchangeRatesListPrivatBank.postValue(
                    getExchangeRateList(
                        datePrivat ?: sharedPreferencesUtil.getDate(Keys.datePrivatKey)
                    ).filter { exchangeRate -> exchangeRate.saleRate != 0.00000 })
                _exchangeRatesListNBU.postValue(
                    getExchangeRateList(
                        dateNBU ?: sharedPreferencesUtil.getDate(Keys.dateNbyKey)
                    ).filter { exchangeRate -> exchangeRate.currency != null })
            }
        }
    }

    private suspend fun getExchangeRateList(date: String): List<ExchangeRate> {
        return try {
            privatBankService.getExchangeRatesListAsync(date).await().exchangeRate
        } catch (e: Exception) {
            e.message
            listOf()
        }
    }
}


