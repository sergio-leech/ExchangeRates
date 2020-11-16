package com.example.exchangerates.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerates.MainActivity
import com.example.exchangerates.R
import com.example.exchangerates.adapter.ExchangeRatesAdapter
import com.example.exchangerates.adapter.ExchangeRatesNBUAdapter
import com.example.exchangerates.databinding.FragmentExchangeRatesBinding
import com.example.exchangerates.view_model.ExchangeRatesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeRatesFragment : Fragment() {
    private val viewModel: ExchangeRatesViewModel by viewModels()
    var index: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentExchangeRatesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapterNBU = ExchangeRatesNBUAdapter()
        adapterNBU.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val recyclerViewNBU = binding.nbuRecyclerView
        recyclerViewNBU.adapter = adapterNBU
        viewModel.exchangeRatesListNBU.observe(viewLifecycleOwner) { list ->
            adapterNBU.submitList(list)
        }

        val adapterPrivatBank = ExchangeRatesAdapter { privatCurrance ->
            index?.let {
                recyclerViewNBU.findViewHolderForAdapterPosition(it)?.itemView?.background =
                    Color.WHITE.toDrawable()
            }
            viewModel.exchangeRatesListNBU.observe(viewLifecycleOwner) { list ->
                list.forEachIndexed { _index, exchangeRate ->
                    if (exchangeRate.currency == privatCurrance) {
                        index = _index
                        recyclerViewNBU.smoothScrollToPosition(_index)
                        Handler(Looper.getMainLooper()).postDelayed({
                            val visible =recyclerViewNBU.findViewHolderForAdapterPosition(_index)?.itemView?.visibility
                            if (visible == 0){
                                recyclerViewNBU.findViewHolderForAdapterPosition(_index)?.itemView?.setBackgroundResource(
                                    R.color.teal_200
                                )
                            }
                        }, 40)
                        return@forEachIndexed
                    }
                }
            }
        }

        adapterPrivatBank.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val recyclerView = binding.privatRecyclerView
        recyclerView.adapter = adapterPrivatBank
        viewModel.exchangeRatesListPrivatBank.observe(viewLifecycleOwner) { list ->
            adapterPrivatBank.submitList(list)
        }

        binding.pickerDataNBU.setOnClickListener {
            startDialogPickerDateFragment(2)
        }
        binding.pickerData.setOnClickListener {
            startDialogPickerDateFragment(1)
        }

        return binding.root
    }

    private fun startDialogPickerDateFragment(key: Int) {
        val bundle = bundleOf("key" to key)
        (activity as MainActivity).navController.navigate(
            R.id.action_exchangeRatesFragment_to_datePickerFragment,
            bundle
        )
    }
}
