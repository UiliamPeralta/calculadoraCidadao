package com.dev.peralta.calculadoracidadao.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.math.BigDecimal
import java.text.NumberFormat

fun EditText.getTextWatcher(): TextWatcher {
    return object : TextWatcher {
        val format = NumberFormat.getCurrencyInstance()
        override fun afterTextChanged(s: Editable?) {
            s?.let {
                if (it.isNotEmpty()) {
                    removeTextChangedListener(this)
                    val clearString = s.replace("""\D""".toRegex(), "")
                    val parsed = BigDecimal(clearString)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN)
                        .divide(BigDecimal(100), BigDecimal.ROUND_HALF_EVEN)
                    val formatted = format.format(parsed)
                    setText(formatted)
                    setSelection(formatted.length)
                    addTextChangedListener(this)
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}

fun TextView.addText(text: String) {
    setText(text.replace(",", "."))
}

fun TextView.parseValue() =
        text.replace("[.R$]".toRegex(), "")

fun TextView.parsePercentAndMonth() =
    text.toString().replace(".", ",")

