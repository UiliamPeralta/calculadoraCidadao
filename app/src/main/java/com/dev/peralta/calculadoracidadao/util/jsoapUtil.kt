import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

const val urlPrestacoesFixas = "https://www3.bcb.gov.br/CALCIDADAO/publico/calcularFinanciamentoPrestacoesFixas.do"
const val urlDepositosRegulares = "https://www3.bcb.gov.br/CALCIDADAO/publico/calcularAplicacaoDepositosRegulares.do"
const val urlValorFuturoCapital = "https://www3.bcb.gov.br/CALCIDADAO/publico/calcularValorFuturoCapital.do"
const val urlCorrigirPorIndice = "https://www3.bcb.gov.br/CALCIDADAO/publico/corrigirPorIndice.do?method=corrigirPorIndice"
const val urlTR = "https://www3.bcb.gov.br/CALCIDADAO/publico/corrigirPelaTR.do?method=corrigirPelaTR"
const val urlPoupanca = "https://www3.bcb.gov.br/CALCIDADAO/publico/corrigirPelaPoupanca.do?method=corrigirPelaPoupanca"
const val urlSelic = "https://www3.bcb.gov.br/CALCIDADAO/publico/corrigirPelaSelic.do?method=corrigirPelaSelic"
const val urlCDI = "https://www3.bcb.gov.br/CALCIDADAO/publico/corrigirPeloCDI.do?method=corrigirPeloCDI"

fun String.getResponse(parans: Map<String, String>): Connection.Response =
    Jsoup
        .connect(this)
        .method(Connection.Method.POST)
        .userAgent("Mozilla/5.0")
        .timeout(10 * 1000)
        .data(parans)
        .followRedirects(true)
        .execute()


fun getParamsPrestacoesFixas(vararg values: String): Map<String, String> {
    val params = mutableMapOf<String, String>()
    params["method"] = "calcularFinanciamentoPrestacoesFixas"
    params["opcao"] = ""
    params["meses"] = values[0]
    params["taxaJurosMensal"] = values[1]
    params["valorPrestacao"] = values[2]
    params["valorFinanciado"] = values[3]
    return params
}

fun getParamsAplicacaoDepositosRegulares(vararg values: String): Map<String, String> {
    val params = mutableMapOf<String, String>()
    params["method"] = "calcularAplicacaoDepositosRegulares"
    params["opcao"] = ""
    params["meses"] = values[0]
    params["taxaJurosMensal"] = values[1]
    params["valorDepositoRegular"] = values[2]
    params["valorFinal"] = values[3]
    return params
}

fun getParamsValorFuturoCapital(vararg values: String): Map<String, String> {
    val params = mutableMapOf<String, String>()
    params["method"] = "calcularValorFuturoCapital"
    params["opcao"] = ""
    params["meses"] = values[0]
    params["taxaJurosMensal"] = values[1]
    params["capitalAtual"] = values[2]
    params["valorFinal"] = values[3]
    return params
}

fun Document.getValuesForm(): Array<String> {
    val elements = getElementsByTag("input")
    return arrayOf(
        elements[2].attr("value"),
        elements[3].attr("value"),
        elements[4].attr("value"),
        elements[5].attr("value"))
}


// Correção de valores
// Correção de valor por índices de preços

fun getParamsCorrecaoIndicePreco(vararg values: String): Map<String, String> {
    val params = mutableMapOf<String, String>()
    params["selIndice"] = values[0]
    params["dataInicial"] = values[1]
    params["dataFinal"] = values[2]
    params["valorCorrecao"] = values[3]
    return params
}

fun getParamsTR(vararg values: String): Map<String, String> {
    val params = mutableMapOf<String, String>()
    params["dataInicioSerie"] = values[0]
    params["dataVencimentoSerie"] = values[1]
    params["dataEfetivoPagamento"] = values[2]
    params["valorCorrecao"] = values[3]
    return params
}

fun getParamsPoupanca(vararg values: String): Map<String, String> {
    val params = mutableMapOf<String, String>()
    params["dataInicial"] = values[0]
    params["dataFinal"] = values[1]
    params["valorCorrecao"] = values[2]
    params["regraNova"] = values[3]
    return params
}

fun getParamsSelic(vararg values: String): Map<String, String> {
    val params = mutableMapOf<String, String>()
    params["dataInicial"] = values[0]
    params["dataFinal"] = values[1]
    params["valorCorrecao"] = values[2]
    return params
}

fun getParamsCDI(vararg values: String): Map<String, String> {
    val params = mutableMapOf<String, String>()
    params["dataInicial"] = values[0]
    params["dataFinal"] = values[1]
    params["valorCorrecao"] = values[2]
    params["percentualCorrecao"] = values[3]
    return params
}

inline fun Document.getTabelaCorrecao(data: (tableTitle: String, dataTitle: String, values: List<String>) -> Unit) {
    val values = mutableListOf<String>()
    val elements = getElementsByTag("table")
    val table = elements[3]
    val tbody = table.getElementsByTag("tbody")
    val lines = tbody.first().children()
    for (line in lines) {
        for (column in line.children()) {
            values.add(column.text())
        }
    }
    data(
        table.getElementsByTag("strong").first().text(),
        table.getElementsByTag("th").first().text(),
        values
    )
}

fun Document.getMsgError(): String? = try {
    this.getElementsByClass("msgErro").first().text()
} catch (ex: NullPointerException) {
    null
}

