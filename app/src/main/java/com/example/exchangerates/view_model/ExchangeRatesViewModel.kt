package com.example.exchangerates.view_model

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.exchangerates.api.PrivatBankService
import com.example.exchangerates.model.ExchangeRate
import com.example.exchangerates.shared_preferences.Keys
import com.example.exchangerates.shared_preferences.SharedPreferencesUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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

    init {
        getListResult()
        setDate()
    }

    private fun setDate() {
        _setDatePrivat.value = datePrivat ?: sharedPreferencesUtil.getDate(Keys.datePrivatKey)
        _setDateNBU.value = dateNBU ?: sharedPreferencesUtil.getDate(Keys.dateNbyKey)
    }

    private fun getListResult() {
        viewModelScope.launch {
            try {
                if (datePrivat != sharedPreferencesUtil.getDate(Keys.datePrivatKey)) {
                    datePrivat?.let {
                        sharedPreferencesUtil.saveDate(Keys.datePrivatKey, it)
                    }
                    val listPrivat = async {
                        privatBankService.getExchangeRatesListAsync(
                            datePrivat ?: sharedPreferencesUtil.getDate(Keys.datePrivatKey)
                        )
                    }
                    val exchangeRatesPrivat = listPrivat.await()
                    val exchangeRatePrivat = exchangeRatesPrivat.await().exchangeRate
                    _exchangeRatesListPrivatBank.value =
                        exchangeRatePrivat.filter { exchangeRate -> exchangeRate.saleRate != 0.00000 }
                }

                if (dateNBU != sharedPreferencesUtil.getDate(Keys.dateNbyKey)) {
                    dateNBU?.let {
                        sharedPreferencesUtil.saveDate(Keys.dateNbyKey, it)
                    }
                    val listNbu = async {
                        privatBankService.getExchangeRatesListAsync(
                            dateNBU ?: sharedPreferencesUtil.getDate(Keys.dateNbyKey)
                        )
                    }
                    val exchangeRatesNbu = listNbu.await()
                    val exchangeRateNbu = exchangeRatesNbu.await().exchangeRate
                    _exchangeRatesListNBU.value =
                        exchangeRateNbu.filter { exchangeRate -> exchangeRate.currency != null }
                }

            } catch (e: Exception) {
                e.message
            }
        }
    }
}


