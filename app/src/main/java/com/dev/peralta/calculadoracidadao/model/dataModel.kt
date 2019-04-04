package com.dev.peralta.calculadoracidadao.model

data class PrestacaoFixa(
    val meses: String,
    val taxaJurosMensal: String,
    val valorPrestacao: String,
    val valorFinanciado: String
)

data class DepositoRegular(
    val meses: String,
    val taxaJurosMensal: String,
    val valorDepositoRegular: String,
    val valorFinal: String
)