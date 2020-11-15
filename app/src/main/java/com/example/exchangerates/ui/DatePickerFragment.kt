package com.example.exchangerates.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import com.example.exchangerates.MainActivity
import com.example.exchangerates.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DatePickerFragment : AppCompatDialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(this.requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = "$dayOfMonth.$month.$year"
        val bundle = when (arguments?.getInt("key")) {
            1 -> bundleOf("datePrivat" to date)
            else -> bundleOf("dateNBU" to date)
        }
        (activity as MainActivity).navController.navigate(
            R.id.action_datePickerFragment_to_exchangeRatesFragment,
            bundle
        )
    }
}