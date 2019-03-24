package com.dev.peralta.calculadoracidadao.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.dev.peralta.calculadoracidadao.R
import com.dev.peralta.calculadoracidadao.model.PrestacaoFixa
import com.dev.peralta.calculadoracidadao.util.addText
import com.dev.peralta.calculadoracidadao.util.getTextWatcher
import com.dev.peralta.calculadoracidadao.util.parsePercentAndMonth
import com.dev.peralta.calculadoracidadao.util.parseValue
import kotlinx.android.synthetic.main.prestacao_fixa_fragment.*


class PrestacaoFixaFragment : Fragment() {

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.prestacao_fixa_fragment, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        viewModel.resultFormLiveData.observe(this, Observer {
            setPrestacaoFixa(it)
        })

        viewModel.errorsFormLiveData.observe(this, Observer {
            if (it != null) Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        viewModel.errorsLiveData.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        btCalcular.setOnClickListener {
            viewModel.query(getPrestacaoFixa())
        }

        valorFinanciado.addTextChangedListener(valorFinanciado.getTextWatcher())
        valorPrestacao.addTextChangedListener(valorPrestacao.getTextWatcher())

    }

    private fun getPrestacaoFixa(): PrestacaoFixa {
        val meses = numMeses.parsePercentAndMonth()
        val taxaJuroMen = taxaJuroMensal.parsePercentAndMonth()
        val valPrestacao = valorPrestacao.parseValue()
        val valFinan = valorFinanciado.parseValue()
        return PrestacaoFixa(meses, taxaJuroMen, valPrestacao, valFinan)
    }

    private fun setPrestacaoFixa(parans: Array<String>) {
        numMeses.addText(parans[0])
        taxaJuroMensal.addText(parans[1])
        valorPrestacao.setText(parans[2])
        valorFinanciado.setText(parans[3])
    }
}
