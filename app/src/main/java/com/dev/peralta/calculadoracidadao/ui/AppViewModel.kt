package com.dev.peralta.calculadoracidadao.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dev.peralta.calculadoracidadao.model.PrestacaoFixa
import com.dev.peralta.calculadoracidadao.repository.Repository

class AppViewModel : ViewModel() {

    private val queryPrestacaoFixa = MutableLiveData<PrestacaoFixa>()


    val progressLiveData = Repository.progressLiveData

    val resultFormLiveData: LiveData<Array<String>> =
            Transformations.switchMap(queryPrestacaoFixa) {
                Repository.getPrestacaoFixa(it)
                Repository.resultFormLiveData
            }

    val errorsLiveData: LiveData<String> =
            Transformations.map(Repository.errorsLiveData) { it }

    val errorsFormLiveData: LiveData<String?> =
            Transformations.map(Repository.errorsFormLiveData) { it }



    fun query(prestacaoFixa: PrestacaoFixa) {
         queryPrestacaoFixa.postValue(prestacaoFixa)
    }
}
