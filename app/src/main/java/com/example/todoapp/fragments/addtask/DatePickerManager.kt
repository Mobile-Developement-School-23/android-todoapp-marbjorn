package com.example.todoapp.fragments.addtask

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import androidx.lifecycle.MutableLiveData
import java.util.Calendar

class DatePickerManager(val context : Context, initialDate : Long?) : DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()
    val date = MutableLiveData<Long?>(initialDate)

    fun showDatePickerDialog() : DatePickerDialog {
        return DatePickerDialog(
            context,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(p1, p2, p3)
        date.value = calendar.timeInMillis
        Log.d("DateManager", date.toString())
    }
}