package com.example.exchangerates.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.R
import com.example.exchangerates.adapter.ExchangeRatesAdapter
import com.example.exchangerates.databinding.FragmentExchangeRatesBinding
import com.example.exchangerates.model.ExchangeRate
import com.example.exchangerates.view_model.ExchangeRatesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExchangeRatesFragment : Fragment() {
    private val viewModel: ExchangeRatesViewModel  by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentExchangeRatesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = ExchangeRatesAdapter()
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val recyclerView = binding.privatRecyclerView
        recyclerView.adapter = adapter

        viewModel.exchangeRatesList.observe(viewLifecycleOwner){list->
            adapter.submitList(list)
        }
       // adapter.submitList(list)
        return binding.root
    }
}
//val list = listOf<ExchangeRate>(ExchangeRate("UIO","9.005",9.005,9.909,5.005,9.90),ExchangeRate("UIO","9.005",9.005,9.909,5.005,9.90))