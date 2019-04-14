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

data class ValorFuturoCapital(
    val meses: String,
    val taxaJurosMensal: String,
    val capitalAtual: String,
    val valorFinal: String
)

data class CorrecaoIndicePreco(
    val selIndice: String,
    val dataInicial: String,
    val dataFinal: String,
    val valorCorrecao: String
)