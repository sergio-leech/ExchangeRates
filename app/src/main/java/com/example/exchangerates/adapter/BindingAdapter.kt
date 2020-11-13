package com.example.exchangerates.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@SuppressLint("SetTextI18n")
@BindingAdapter("setSum")
fun TextView.bindText(sum: Double) {
    val getSum = sum.toString()
    text = getSum
}