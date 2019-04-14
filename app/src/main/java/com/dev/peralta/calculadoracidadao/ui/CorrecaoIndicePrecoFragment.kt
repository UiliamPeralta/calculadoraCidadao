package com.dev.peralta.calculadoracidadao.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

import com.dev.peralta.calculadoracidadao.R
import kotlinx.android.synthetic.main.fragment_correcao_indice_preco.*
import java.text.SimpleDateFormat
import java.util.*

class CorrecaoIndicePrecoFragment : Fragment() {

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_correcao_indice_preco, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[AppViewModel::class.java]
        setDateInicial()
        setDateFinal()
    }

    private fun setDateInicial() {
        val sdf = SimpleDateFormat("MM/yyyy", Locale("pt", "BR"))
        dataInicial.setOnClickListener { v ->
            fragmentManager?.let {
                DatePickerFragment.show(it)
                DatePickerFragment.interaction = { date ->
                    if (v is TextView) v.text = sdf.format(date)
                }
                DatePickerFragment.cancel = {
                    if (v is TextView) v.text = ""
                }
            }
        }
    }
    private fun setDateFinal() {
        val sdf = SimpleDateFormat("MM/yyyy", Locale("pt", "BR"))
        dataFinal.setOnClickListener { v ->
            fragmentManager?.let {
                DatePickerFragment.show(it)
                DatePickerFragment.interaction = { date ->
                    if (v is TextView) v.text = sdf.format(date)
                }
                DatePickerFragment.cancel = {
                    if (v is TextView) v.text = ""
                }
            }
        }
    }
}

