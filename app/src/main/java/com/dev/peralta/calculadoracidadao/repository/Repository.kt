package com.dev.peralta.calculadoracidadao.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.peralta.calculadoracidadao.TAG
import com.dev.peralta.calculadoracidadao.model.PrestacaoFixa
import getMsgError
import getParamsPrestacoesFixas
import getResponse
import urlPrestacoesFixas
import getValuesForm
import java.io.IOException


object Repository {

    private var resultForm: MutableLiveData<Array<String>> = MutableLiveData()
    private var errors: MutableLiveData<String> = MutableLiveData()
    private var errorsForm: MutableLiveData<String> = MutableLiveData()
    private var isRequest = false

    val resultFormLiveData: LiveData<Array<String>>
        get() = resultForm

    val errorsLiveData: LiveData<String>
        get() = errors

    val errorsFormLiveData: LiveData<String>
        get() = errorsForm

    fun getPrestacaoFixa(prestacaoFixa: PrestacaoFixa) {
        val parans = getParamsPrestacoesFixas(
            prestacaoFixa.meses,
            prestacaoFixa.taxaJurosMensal,
            prestacaoFixa.valorPrestacao,
            prestacaoFixa.valorFinanciado
        )

        if (!isRequest) {
            isRequest = true
            TaskForm(urlPrestacoesFixas).execute(parans)
        }
    }

    private class TaskForm(private val url: String) : AsyncTask<Map<String, String>, Unit, Boolean>() {
        override fun doInBackground(vararg params: Map<String, String>?): Boolean {
            Log.i(TAG, "em execução $isRequest")
            params[0]?.let {

                    try {
                        val doc = url.getResponse(it).parse()
                        resultForm.postValue(doc.getValuesForm())
                        errorsForm.postValue(doc.getMsgError())

                    } catch (ex: IOException) {
                        errors.postValue( ex.message ?: "Fail Connection")
                    }
                }

            return false
        }

        override fun onPostExecute(result: Boolean) {
            isRequest = result
            Log.i(TAG, "pos execução $isRequest")
        }

    }
}