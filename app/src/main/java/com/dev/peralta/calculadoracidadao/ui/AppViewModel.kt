package com.dev.peralta.calculadoracidadao.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dev.peralta.calculadoracidadao.model.CorrecaoIndicePreco
import com.dev.peralta.calculadoracidadao.model.DepositoRegular
import com.dev.peralta.calculadoracidadao.model.PrestacaoFixa
import com.dev.peralta.calculadoracidadao.model.ValorFuturoCapital
import com.dev.peralta.calculadoracidadao.repository.Repository

class AppViewModel : ViewModel() {

    private val queryPrestacaoFixa = MutableLiveData<PrestacaoFixa>()
    private val queryDepositoRegular = MutableLiveData<DepositoRegular>()
    private val queryValorFuturoCapital = MutableLiveData<ValorFuturoCapital>()
    private val queryCorrecaoIndicePreco = MutableLiveData<CorrecaoIndicePreco>()
    private val repository = Repository()


    val progressLiveData = repository.progressLiveData

    val resultFormLiveDataPrestacaoFixa: LiveData<Array<String>> =
        Transformations.switchMap(queryPrestacaoFixa) {
            repository.getPrestacaoFixa(it)
            repository.resultFormLiveData
        }

    val resultFormLiveDataDepositoRegular =
        Transformations.switchMap(queryDepositoRegular) {
            repository.getDepositoRegular(it)
            repository.resultFormLiveData
        }

    val resultFormLiveDataValorFuturoCapital =
        Transformations.switchMap(queryValorFuturoCapital) {
            repository.getValorFuturo(it)
            repository.resultFormLiveData
        }

    val resultLiveDataCorrecaoIndicePreco =
        Transformations.switchMap(queryCorrecaoIndicePreco) {
            repository.getCorrecaoIndicePreco(it)
            repository.resultFormLiveData
        }

    val errorsLiveData: LiveData<String> =
        Transformations.map(repository.errorsLiveData) { it }

    val errorsFormLiveData: LiveData<String?> =
        Transformations.map(repository.errorsFormLiveData) { it }



    fun query(prestacaoFixa: PrestacaoFixa) {
        queryPrestacaoFixa.postValue(prestacaoFixa)
    }

    fun query(depositoRegular: DepositoRegular) {
        queryDepositoRegular.postValue(depositoRegular)
    }

    fun query(valorFuturoCapital: ValorFuturoCapital) {
        queryValorFuturoCapital.postValue(valorFuturoCapital)
    }
    fun query(correcaoIndicePreco: CorrecaoIndicePreco) {
        queryCorrecaoIndicePreco.postValue(correcaoIndicePreco)
    }
}
