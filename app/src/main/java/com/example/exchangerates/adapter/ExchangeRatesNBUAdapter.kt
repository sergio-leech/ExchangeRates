package com.example.exchangerates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.databinding.ExchangeItemListNbuBinding
import com.example.exchangerates.model.ExchangeRate

class ExchangeRatesNBUAdapter :
    ListAdapter<ExchangeRate, ExchangeRatesNBUAdapter.ExchangeRatesNBUViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesNBUViewHolder {
        return ExchangeRatesNBUViewHolder(
            ExchangeItemListNbuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExchangeRatesNBUViewHolder, position: Int) {
        getItem(position)?.let { exchange ->
            holder.bind(exchange)
        }
    }

    class ExchangeRatesNBUViewHolder(private val binding: ExchangeItemListNbuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exchangeRateItem: ExchangeRate) {
            binding.apply {
                exchangeRate = exchangeRateItem
                executePendingBindings()
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ExchangeRate>() {
        override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
            return oldItem.purchaseRateNB == newItem.purchaseRateNB
        }
    }
}