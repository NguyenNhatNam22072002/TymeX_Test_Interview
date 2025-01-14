package com.example.currencyconverter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.network.RetrofitInstance
import kotlinx.coroutines.launch

class CurrencyConverterViewModel : ViewModel() {
    private val _convertedAmount = MutableLiveData<String>()
    val convertedAmount: LiveData<String> get() = _convertedAmount
    private val apiKey = "Enter_your_key"
    private val _currencyList = MutableLiveData<List<String>>()
    val currencyList: LiveData<List<String>> get() = _currencyList

    fun fetchCurrencyList() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getExchangeRates(
                    baseCurrency = "EUR",
                    targetCurrencies = "",
                    apiKey = apiKey
                )

                if (response.isSuccessful) {
                    Log.d("CurrencyConverter", "Currency list fetched: ${response.body()}")
                    val rates = response.body()?.rates
                    val currencies = rates?.keys?.toList() ?: emptyList()
                    _currencyList.value = currencies
                } else {
                    Log.e("CurrencyConverter", "Error: ${response.code()} ${response.message()}")
                    _currencyList.value = emptyList()  // Trả về danh sách trống khi có lỗi
                }
            } catch (e: Exception) {
                Log.e("CurrencyConverter", "Exception: ${e.localizedMessage}")
                _currencyList.value = emptyList()  // Trả về danh sách trống khi có lỗi
                // Cập nhật LiveData lỗi
                _convertedAmount.value = "Network error: ${e.localizedMessage}"
            }
        }
    }

    fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getExchangeRates(
                    baseCurrency = "EUR",
                    targetCurrencies = "$fromCurrency,$toCurrency",
                    apiKey = apiKey
                )

                if (response.isSuccessful) {
                    Log.d("CurrencyConverter", "Response successful: ${response.body()}")
                    val fromRate = response.body()?.rates?.get(fromCurrency) ?: 1.0
                    val toRate = response.body()?.rates?.get(toCurrency) ?: 1.0
                    val amountInEuro = amount / fromRate
                    _convertedAmount.value = (amountInEuro * toRate).toString()
                } else {
                    Log.e("CurrencyConverter", "Error: ${response.code()} ${response.message()}")
                    _convertedAmount.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("CurrencyConverter", "Exception: ${e.localizedMessage}")
                _convertedAmount.value = "Error: ${e.localizedMessage}"
            }
        }
    }
}
