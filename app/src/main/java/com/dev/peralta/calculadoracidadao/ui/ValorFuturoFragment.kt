package com.dev.peralta.calculadoracidadao.ui


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.dev.peralta.calculadoracidadao.R
import com.dev.peralta.calculadoracidadao.model.ValorFuturoCapital
import com.dev.peralta.calculadoracidadao.util.addText
import com.dev.peralta.calculadoracidadao.util.getTextWatcher
import com.dev.peralta.calculadoracidadao.util.parsePercentAndMonth
import com.dev.peralta.calculadoracidadao.util.parseValue
import kotlinx.android.synthetic.main.fragment_valor_futuro.*

class ValorFuturoFragment : Fragment() {
    private lateinit var viewModel: AppViewModel
    private var showMsg = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_valor_futuro, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        viewModel.resultFormLiveDataValorFuturoCapital.observe(this, Observer {
            setValorFuturoCapital(it)
        })

        viewModel.errorsFormLiveData.observe(this, Observer {
            it?.toast()
        })

        viewModel.errorsLiveData.observe(this, Observer {
            it.toast()
        })

        viewModel.progressLiveData.observe(this, Observer {
            if (it) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.INVISIBLE
        })

        capitalAtual.addTextChangedListener(capitalAtual.getTextWatcher())
        valorFinal.addTextChangedListener(valorFinal.getTextWatcher())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_calcular -> {
                viewModel.query(getValorFuturoCapital())
                showMsg = true
                true
            }
            R.id.action_limpar -> {
                limpar()
                true
            }
            R.id.action_exemplo -> {
                val url = Bundle()
                url.putString("url", getString(R.string.EX_VALOR_FUTURO_URL))
                findNavController().navigate(R.id.action_valorFuturoFragment_to_exemploFragment, url)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun limpar() {
        numMeses.setText("")
        taxaJuroMensal.setText("")
        capitalAtual.setText("")
        valorFinal.setText("")
    }

    private fun getValorFuturoCapital(): ValorFuturoCapital {
        val meses = numMeses.parsePercentAndMonth()
        val taxaJuroMen = taxaJuroMensal.parsePercentAndMonth()
        val capAtual = capitalAtual.parseValue()
        val valorFinal = valorFinal.parseValue()
        return ValorFuturoCapital(meses, taxaJuroMen, capAtual, valorFinal)
    }

    private fun setValorFuturoCapital(parans: Array<String>) {
        numMeses.addText(parans[0])
        taxaJuroMensal.addText(parans[1])
        capitalAtual.setText(parans[2])
        valorFinal.setText(parans[3])
    }

    private fun String.toast() {
        if (showMsg) {
            Toast.makeText(activity, this, Toast.LENGTH_LONG).show()
            showMsg = false
        }
    }
}
