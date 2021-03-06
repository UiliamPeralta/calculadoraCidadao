package com.dev.peralta.calculadoracidadao.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.peralta.calculadoracidadao.model.CorrecaoIndicePreco
import com.dev.peralta.calculadoracidadao.model.DepositoRegular
import com.dev.peralta.calculadoracidadao.model.PrestacaoFixa
import com.dev.peralta.calculadoracidadao.model.ValorFuturoCapital
import getMsgError
import getParamsAplicacaoDepositosRegulares
import getParamsCorrecaoIndicePreco
import getParamsPrestacoesFixas
import getParamsValorFuturoCapital
import getResponse
import urlPrestacoesFixas
import getValuesForm
import urlCorrigirPorIndice
import urlDepositosRegulares
import urlValorFuturoCapital
import java.io.IOException


class Repository {

    private var resultForm: MutableLiveData<Array<String>> = MutableLiveData()
    private var errors: MutableLiveData<String> = MutableLiveData()
    private var errorsForm: MutableLiveData<String> = MutableLiveData()
    private var progress: MutableLiveData<Boolean> = MutableLiveData()
    private var isRequest = false

    val resultFormLiveData: LiveData<Array<String>>
        get() = resultForm

    val errorsLiveData: LiveData<String>
        get() = errors

    val errorsFormLiveData: LiveData<String>
        get() = errorsForm

    val progressLiveData: LiveData<Boolean>
        get() = progress

    fun getPrestacaoFixa(prestacaoFixa: PrestacaoFixa) {
        val parans = getParamsPrestacoesFixas(
            prestacaoFixa.meses,
            prestacaoFixa.taxaJurosMensal,
            prestacaoFixa.valorPrestacao,
            prestacaoFixa.valorFinanciado
        )

        if (!isRequest) {
            isRequest = true
            TaskForm(urlPrestacoesFixas, this).execute(parans)
        }
    }

    fun getDepositoRegular(depositoRegular: DepositoRegular) {
        val parans = getParamsAplicacaoDepositosRegulares(
            depositoRegular.meses,
            depositoRegular.taxaJurosMensal,
            depositoRegular.valorDepositoRegular,
            depositoRegular.valorFinal
        )

        if (!isRequest) {
            isRequest = true
            TaskForm(urlDepositosRegulares, this).execute(parans)
        }
    }

    fun getValorFuturo(valorFuturoCapital: ValorFuturoCapital) {
        val params = getParamsValorFuturoCapital(
            valorFuturoCapital.meses,
            valorFuturoCapital.taxaJurosMensal,
            valorFuturoCapital.capitalAtual,
            valorFuturoCapital.valorFinal
        )

        if (!isRequest) {
            isRequest = true
            TaskForm(urlValorFuturoCapital, this).execute(params)
        }
    }

    fun getCorrecaoIndicePreco(correcaoIndicePreco: CorrecaoIndicePreco) {
        with(correcaoIndicePreco) {
            val params = getParamsCorrecaoIndicePreco(
                selIndice, dataInicial, dataFinal, valorCorrecao
            )
            if (!isRequest) {
                isRequest = true
                TaskForm(urlCorrigirPorIndice, this@Repository).execute(params)
            }
        }
    }

    private class TaskForm(private val url: String, private val repository: Repository) : AsyncTask<Map<String, String>, Unit, Boolean>() {
        override fun doInBackground(vararg params: Map<String, String>?): Boolean {
            params[0]?.let {
                    repository.progress.postValue(true)
                    try {
                        val doc = url.getResponse(it).parse()
                        repository.resultForm.postValue(doc.getValuesForm())
                        repository.errorsForm.postValue(doc.getMsgError())

                    } catch (ex: IOException) {
                        repository.errors.postValue( ex.message ?: "Fail Connection")
                    }
                }

            return false
        }

        override fun onPostExecute(result: Boolean) {
            repository.isRequest = result
            repository.progress.value = result
        }

    }
}