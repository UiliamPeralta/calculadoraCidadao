package com.dev.peralta.calculadoracidadao.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
            it?.toast()
        })

        viewModel.errorsLiveData.observe(this, Observer {
            it.toast()
        })

        valorFinanciado.addTextChangedListener(valorFinanciado.getTextWatcher())
        valorPrestacao.addTextChangedListener(valorPrestacao.getTextWatcher())

    }

    private fun String.toast() {
        Toast.makeText(activity, this, Toast.LENGTH_LONG).show()
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

    private fun limpar() {
        numMeses.setText("")
        taxaJuroMensal.setText("")
        valorPrestacao.setText("")
        valorFinanciado.setText("")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_prestacao_fixa, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_calcular -> {
                viewModel.query(getPrestacaoFixa())
                true
            }
            R.id.action_limpar -> {
                limpar()
                true
            }
            R.id.action_exemplo -> {
                getString(R.string.exemplo).toast()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
