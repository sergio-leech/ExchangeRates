package com.example.exchangerates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.databinding.ExchangeItemListBinding
import com.example.exchangerates.model.ExchangeRate
import kotlinx.android.synthetic.main.exchange_item_list.view.*

open class ExchangeRatesAdapter(val itemClick: (String) -> Unit) :
    ListAdapter<ExchangeRate, ExchangeRatesAdapter.ExchangeRatesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesViewHolder {
        return ExchangeRatesViewHolder(
            ExchangeItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExchangeRatesViewHolder, position: Int) {
        getItem(position)?.let { exchange ->
            holder.bind(exchange)
        }
    }

    inner class ExchangeRatesViewHolder(private val binding: ExchangeItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                itemClick(it.currency.text.toString())

            }
        }

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
            return oldItem.purchaseRate == newItem.purchaseRate
        }
    }
}