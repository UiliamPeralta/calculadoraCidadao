package com.dev.peralta.calculadoracidadao.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var calendar: Calendar

    companion object {
        var interaction: ((Date) -> Unit)? = null
        var cancel: (() -> Unit)? = null
        fun show(fm: FragmentManager) = DatePickerFragment().show(fm, "datePicker")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val ctx = activity ?: return super.onCreateDialog(savedInstanceState)
        calendar = Calendar.getInstance()
        val dia = calendar.get(Calendar.DAY_OF_MONTH)
        val mes = calendar.get(Calendar.MONTH)
        val ano = calendar.get(Calendar.YEAR)
        return DatePickerDialog(ctx,this, ano, mes, dia)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        interaction?.invoke(calendar.time)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        cancel?.invoke()
    }
}