package com.example.currencyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.currencyconverter.viewmodel.CurrencyConverterViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CurrencyConverterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val amountEditText = findViewById<EditText>(R.id.etAmount)
        val fromCurrencySpinner = findViewById<Spinner>(R.id.spinnerFrom)
        val toCurrencySpinner = findViewById<Spinner>(R.id.spinnerTo)
        val convertButton = findViewById<Button>(R.id.btnConvert)
        val resultTextView = findViewById<TextView>(R.id.tvResult)

        // Quan sát danh sách các loại tiền tệ và cập nhật spinner
        viewModel.currencyList.observe(this, Observer { currencies ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
            fromCurrencySpinner.adapter = adapter
            toCurrencySpinner.adapter = adapter
        })

        // Gửi yêu cầu để lấy danh sách các loại tiền tệ từ API khi ứng dụng khởi động
        viewModel.fetchCurrencyList()

        // Xử lý khi nhấn nút chuyển đổi
        convertButton.setOnClickListener {
            val amountString = amountEditText.text.toString()
            if (amountString.isNotEmpty()) {
                val amount = amountString.toDoubleOrNull()
                if (amount != null) {
                    val fromCurrency = fromCurrencySpinner.selectedItem.toString()
                    val toCurrency = toCurrencySpinner.selectedItem.toString()
                    viewModel.convertCurrency(amount, fromCurrency, toCurrency)
                } else {
                    // Hiển thị thông báo lỗi cho người dùng nếu số tiền không hợp lệ
                    Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Hiển thị thông báo lỗi khi không nhập số tiền
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            }
        }

            // Quan sát kết quả và cập nhật UI
            viewModel.convertedAmount.observe(this, Observer { result ->
                if (result.startsWith("Error")) {
                    // Nếu có lỗi, hiển thị thông báo lỗi cho người dùng
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                } else {
                    resultTextView.text = "Converted Amount: $result"
                }
            })
    }
}
