import java.io.File

fun criaMenu(): String{
    return "\nBem vindo ao jogo das tendas\n\n1 - Novo jogo\n0 - Sair\n"
}
fun validaTamanhoMapa(numLinhas: Int, numColunas: Int): Boolean{
    return when{
        (numLinhas==6&&(numColunas==5||numColunas==6)) -> true
        (numLinhas==8&&numColunas==8) -> true
        (numLinhas==10&&numColunas==10) -> true
        (numLinhas==8&&numColunas==10) -> true
        (numLinhas==10&&numColunas==8) -> true
        else -> false
    }
}
fun validaDataNascimento(data:String?) : String?{
    val invalido = "Data invalida"
    if (data!=null && data.length == 10 && data[2].toString() =="-" && data[5].toString() =="-" ){
        val datastr= data.toString()
        val dia =datastr[0].toString() + datastr[1].toString()
        val mes = datastr[3].toString() + datastr[4].toString()
        val ano =datastr[6].toString() + datastr[7].toString() + datastr[8].toString() + datastr[9].toString()
        val diaint = dia.toInt()
        val anoint = ano.toInt()
        val menor = "Menor de idade nao pode jogar"
        return when{
            (anoint !in 1900 .. 2022) ->  invalido
            ((mes=="12")&& (diaint in 1..31)&&(anoint <= 2003)) -> null
            ((mes=="01"||mes=="03"||mes=="05"||mes=="07"||mes=="08"||mes=="10")&&(diaint in 1..31)&&(anoint <=2004))-> null
            ((mes=="11")&&(diaint in 1..30)&& (anoint <= 2003)) ->null
            ((mes=="04"||mes=="06"||mes=="09")&&(diaint in 1..30)&& (anoint <=2004)) ->null
            ((mes=="02")&&(diaint in 1..28)&&(anoint <=2004)) -> null
            ((mes=="02")&&((anoint%4==0 && anoint%100!=0)||anoint%400==0)&&(diaint==29)&&(anoint <=2004)) -> null
            ((mes=="01"||mes=="03"||mes=="05"||mes=="07"||mes=="08"||mes=="10"||mes=="12")&&(diaint in 1..31)) -> menor
            ((mes=="04"||mes=="06"||mes=="09"||mes=="11")&&(diaint in 1..30)) -> return menor
            ((mes=="02")&&(diaint in 1..28)) ->menor
            ((mes=="02")&&((anoint%4==0 && anoint%100!=0)||anoint%400==0)&& (diaint==29))-> menor
            else ->invalido
        }
    }else {
        return invalido
    }
}
fun criaLegendaHorizontal(numColunas: Int): String{

    var loop = 2
    var letra = 'A'
    var colunas = "A"
    while ((numColunas) > loop) {
        letra++
        colunas += " | $letra"
        loop++
    }
    letra++
    colunas += " | $letra"
    return colunas
}

fun processaCoordenadas(coordenadasStr: String?, numLines: Int,numColumns: Int): Pair<Int,Int>? {

    when{
        (coordenadasStr.toString().length ==3&&coordenadasStr.toString()[1].toString() ==",") -> {
            if (coordenadasStr.toString()[0].toString().toInt() in 1..numLines) {
                if (coordenadasStr.toString()[2].uppercaseChar()>='A'&&coordenadasStr.toString()[2].uppercaseChar()<=(numColumns+64).toChar()){
                    val coordenadasint = (coordenadasStr.toString()[2].uppercaseChar()-65.toChar()).toString().toInt()
                    return Pair(coordenadasStr.toString()[0].toString().toInt()-1,coordenadasint)}}
        }
        (coordenadasStr.toString().length ==4&&coordenadasStr.toString()[2].toString() ==",") -> {
            val coordenadaslinha = ((coordenadasStr.toString()[0].toString() + coordenadasStr.toString()[1].toString()).toInt())
            if ((coordenadaslinha >= 1 )&&(coordenadaslinha<= numLines)) {
                if ((coordenadasStr.toString()[3]).uppercaseChar()>='A'&& coordenadasStr.toString()[3].uppercaseChar()<=(numColumns+64).toChar()){
                    return Pair(coordenadaslinha-1,(coordenadasStr.toString()[3].uppercaseChar()-65.toChar()).toString().toInt())} }
        }
        else -> return null
    }
    return null
}

fun criaLegendaContadoresHorizontal(contadoresHorizontal: Array<Int?>): String {

    var criaLegendaContadoresHorizontal = ""
    val size = contadoresHorizontal.size - 1
    for(number in 0 until size){

        if(contadoresHorizontal[number]==null){
            criaLegendaContadoresHorizontal += "    "
        }else{
            criaLegendaContadoresHorizontal+= contadoresHorizontal[number].toString() + "   "
        }
    }
    criaLegendaContadoresHorizontal += contadoresHorizontal[size].toString()

    return criaLegendaContadoresHorizontal
}

fun leContadoresDoFicheiro(numLines: Int, numColumns: Int, verticais: Boolean): Array<Int?> {

    val linhas = File("$numLines" + "x" + "$numColumns.txt").readLines()
    val leContadoresDoFicheiros : Array<Int?>
    val leContadoresDoFicheiro: List<String>

    if (verticais) {
        leContadoresDoFicheiro = linhas[0].split(",")
        leContadoresDoFicheiros = arrayOfNulls(numColumns)
    } else{
        leContadoresDoFicheiro = linhas[1].split(",")
        leContadoresDoFicheiros = arrayOfNulls(numLines)
    }

    for (number in 0 until leContadoresDoFicheiros.size) {
        if (leContadoresDoFicheiro[number] != "0") {
            leContadoresDoFicheiros[number] = leContadoresDoFicheiro[number].toInt()
        }
    }
    return leContadoresDoFicheiros
}

fun leTerrenoDoFicheiro(numLines: Int, numColumns: Int): Array<Array<String?>>{

    val terreno:Array<Array<String?>> = Array(numLines) { Array(numColumns){null} }
    val file = File("$numLines"+"x"+"$numColumns.txt").readLines()

    for (number in 2 until file.size) {
        for (linha in 0 until numLines) {
            val line = file[number]
            val partes = line.split(",")
            if (partes[0].toInt() == linha) {
                for (coluna in 0 until numColumns) {
                    if (partes[1].toInt() == coluna) {
                        terreno[linha][coluna] = "A"
                    }
                }
            }
        }
    }
    return terreno
}

fun criaTerreno(terreno: Array<Array<String?>>,
                contadoresVerticais: Array<Int?>?, contadoresHorizontais: Array<Int?>?,
                mostraLegendaHorizontal: Boolean, mostraLegendaVertical: Boolean): String {

    var criaTerreno = ""

    if(contadoresVerticais!=null) {
        criaTerreno += "       " + criaLegendaContadoresHorizontal(contadoresVerticais) + "\n"
    }

    if(mostraLegendaHorizontal){
        criaTerreno += "     | " + criaLegendaHorizontal(terreno[0].size) + "\n"
    }

    for(count1 in 1 until terreno.size+1){
        var numVerticais = " "

        if(contadoresHorizontais!=null) {
            if (contadoresHorizontais[count1 - 1] != null) {
                numVerticais = contadoresHorizontais[count1 - 1].toString()
            }
        }
        if (mostraLegendaVertical) {
            if (count1 == 10) {
                criaTerreno += "$numVerticais $count1"
            } else {
                criaTerreno += "$numVerticais  $count1"
            }
        } else {
            criaTerreno += "$numVerticais   "
        }

        for (count2 in 0 until terreno[0].size) {

                if (terreno[count1 - 1][count2] == null) {
                    criaTerreno += " |  "
                } else if (terreno[count1 - 1][count2] == "A") {
                    criaTerreno += " | △"
                } else {
                    criaTerreno += " | T"
                }
            }
        if(count1 <= terreno.size) {
            criaTerreno += "\n"
        }
    }
    return criaTerreno
}

fun temArvoreAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>) : Boolean{

    val parte1 = coords.first
    val parte2 = coords.second
    val tamanhoLin = terreno.size-1
    val tamanhoCol = terreno[0].size-1

    if(terreno.size==1){
        return when{
            (parte2==0)-> (terreno[0][1] == "A")
            (parte2==terreno[0].size-1)-> (terreno[0][parte2-1] == "A")
            else -> (terreno[0][parte2-1] == "A"|| terreno[0][parte2+1] == "A")
        }
    }
    return when{
        (parte1==0&&parte2==0)-> (terreno[1][parte2]=="A"|| terreno[parte1][1]=="A")
        (parte1==tamanhoLin&&parte2==tamanhoCol)->(terreno[parte1-1][parte2]=="A"||terreno[parte1][parte2-1]=="A")
        (parte1==0&&parte2==tamanhoCol)-> (terreno[1][parte2]=="A"||terreno[parte1][parte2-1]=="A")
        (parte1==tamanhoLin&&parte2==0)-> (terreno[parte1-1][parte2]=="A"||terreno[parte1][1]=="A")
        (parte1==0)-> (terreno[1][parte2]=="A"||terreno[parte1][parte2-1]=="A"||terreno[parte1][parte2+1]=="A")
        (parte2==0)-> (terreno[parte1-1][parte2]=="A"||terreno[parte1+1][parte2]=="A"||terreno[parte1][1]=="A")
        (parte1==tamanhoLin)-> (terreno[parte1][parte2+1]=="A"||terreno[parte1-1][parte2]=="A"||terreno[parte1][parte2-1]=="A")
        (parte2==tamanhoCol)-> (terreno[parte1-1][parte2]=="A"||terreno[parte1+1][parte2]=="A"||terreno[parte1][parte2-1]=="A")
        else->(terreno[parte1-1][parte2]=="A"||terreno[parte1+1][parte2]=="A"||terreno[parte1][parte2-1]=="A"||terreno[parte1][parte2+1]=="A")
    }
}
fun temTendaAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>) : Boolean {

    val parte1 = coords.first
    val parte2 = coords.second
    val tamanhoLin = terreno.size-1
    val tamanhoCol = terreno[0].size-1

    if(terreno.size==1){
        return when{
            (parte2==0)-> (terreno[0][1] == "T")
            (parte2==terreno[0].size-1)-> (terreno[0][parte2-1] == "T")
            else -> (terreno[0][parte2-1] == "T"|| terreno[0][parte2+1] == "T")
        }
    }
    return when{
        (parte1==0&&parte2==0)-> (terreno[1][parte2]=="T"|| terreno[parte1][1]=="T"||terreno[1][1]=="T")
        (parte1==tamanhoLin&&parte2==tamanhoCol)->(terreno[parte1-1][parte2]=="T"||terreno[parte1][parte2-1]=="T"||terreno[parte1-1][parte2-1]=="T")
        (parte1==0&&parte2==tamanhoCol)-> (terreno[1][parte2]=="T"||terreno[parte1][parte2-1]=="T"||terreno[1][parte2-1]=="T")
        (parte1==tamanhoLin&&parte2==0)-> (terreno[parte1-1][parte2]=="T"||terreno[parte1][1]=="T"||terreno[parte1-1][1]=="T")
        (parte1==0)-> (terreno[1][parte2]=="T"||terreno[parte1][parte2-1]=="T"||terreno[parte1][parte2+1]=="T"||
                terreno[1][parte2-1]=="T"||terreno[1][parte2+1]=="T")
        (parte2==0)-> (terreno[parte1-1][parte2]=="T"||terreno[parte1+1][parte2]=="T"||terreno[parte1][1]=="T"||
                terreno[parte1-1][1]=="T"||terreno[parte1+1][1]=="T")
        (parte1==tamanhoLin)-> (terreno[parte1][parte2+1]=="T"||terreno[parte1-1][parte2]=="T"||terreno[parte1][parte2-1]=="T"
                ||terreno[parte1-1][parte2-1]=="T"||terreno[parte1-1][parte2+1]=="T")
        (parte2==tamanhoCol)-> (terreno[parte1-1][parte2]=="T"||terreno[parte1+1][parte2]=="T"||terreno[parte1][parte2-1]=="T"||
                terreno[parte1-1][parte2-1]=="T"||terreno[parte1+1][parte2-1]=="T")
        else->(terreno[parte1-1][parte2]=="T"||terreno[parte1+1][parte2]=="T"||
                terreno[parte1][parte2-1]=="T"||terreno[parte1][parte2+1]=="T"|| terreno[parte1+1][parte2+1]=="T"||
                terreno[parte1+1][parte2-1]=="T"||terreno[parte1-1][parte2+1]=="T"||terreno[parte1-1][parte2-1]=="T")
    }
}

fun contaTendasColuna(terreno:Array<Array<String?>>, coluna: Int): Int {

    if(coluna>terreno[0].size-1||coluna<0){
        return 0
    }
    var count = 0
    for(colunas in 0 until terreno.size) {
        if (terreno[colunas][coluna]=="T"){
            count++
        }
    }
    return count
}

fun contaTendasLinha(terreno: Array<Array<String?>>, linha: Int): Int {

    if(linha !in 0..terreno.size-1){
        return 0
    }
    var count = 0
    for(linhas in 0 until terreno[linha].size){
        if(terreno[linha][linhas]=="T") {
            count++
        }
    }
    return count
}

fun colocaTenda(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {

    val parte1 = coords.first
    val parte2 = coords.second


    if(terreno[parte1][parte2]==null&&temArvoreAdjacente(terreno,coords)&&(!temTendaAdjacente(terreno,coords))){
        terreno[parte1][parte2] = "T"
        return true
    }else if(terreno[parte1][parte2] == "T"){
        terreno[parte1][parte2] = null
        return true
    }
    return false
}

fun terminouJogo(terreno: Array<Array<String?>>, contadoresVerticais: Array<Int?>, contadoresHorizontais: Array<Int?>): Boolean {

    var count1 = 0
    var count2 = 0

    for (linhas in 0 until terreno.size) {
        for (colunas in 0 until terreno[0].size ) {
            if (terreno[linhas][colunas] == "T") {
                count1++
            } else if (terreno[linhas][colunas] == "A") {
                count2++
            }
        }
    }
    if(count1==count2) {
        for (linhas in 0 until terreno.size - 1) {
            if (!(contaTendasLinha(terreno, linhas) == contadoresHorizontais[linhas] ||
                        contaTendasLinha(terreno, linhas) == 0 && contadoresHorizontais[linhas] == null)) {
                return false
            }
        }
        for (colunas in 0 until terreno[0].size - 1) {
            if (!(contaTendasColuna(terreno, colunas) == contadoresVerticais[colunas] ||
                        contaTendasColuna(terreno, colunas) == 0 && contadoresVerticais[colunas] == null)) {
                return false
            }
        }
        return true
    }
    return false
}

fun main() {
    var opcaomenu: Int?
    var numLinhas: Int?
    var numColunas: Int?
    var data: String?
    var menorDeIdade = 0
    val invalido = "Data invalida"
    do {
        do {
            do {
                println(criaMenu())
                do {
                    opcaomenu = readlnOrNull()?.toIntOrNull()
                    when (opcaomenu) {
                        0 -> return
                        1 -> println("Quantas linhas?")
                        else -> {
                            println("Opcao invalida")
                            println(criaMenu()) } }
                } while (opcaomenu != 1)
                numLinhas = readlnOrNull()?.toIntOrNull()
                while (numLinhas == null || (numLinhas < 0)) {
                    println("Resposta invalida")
                    println("Quantas linhas?")
                    numLinhas = readlnOrNull()?.toIntOrNull() }
                println("Quantas colunas?")
                numColunas = readlnOrNull()?.toIntOrNull()
                while (numColunas == null || (numColunas < 0)) {
                    println("Resposta invalida")
                    println("Quantas colunas?")
                    numColunas = readlnOrNull()?.toIntOrNull() }
                if (!validaTamanhoMapa(numLinhas, numColunas)) {
                    println("Terreno invalido") }
            } while (numLinhas != null && numColunas != null && !validaTamanhoMapa(numLinhas.toInt(), numColunas))
            if (numLinhas == 10 && numColunas == 10) {
                do {
                    println("Qual a sua data de nascimento? (dd-mm-yyyy)")
                    data = readlnOrNull()?.toString()
                    validaDataNascimento(data)
                    if (validaDataNascimento(data) == invalido) {
                        println(validaDataNascimento(data))
                    }
                } while (validaDataNascimento(data) == invalido)

                if (validaDataNascimento(data) == "Menor de idade nao pode jogar") {
                    println(validaDataNascimento(data))
                    menorDeIdade = 1 } }
        } while (menorDeIdade == 1)
        if (numLinhas != null && numColunas != null) {
            val terreno = leTerrenoDoFicheiro(numLinhas, numColunas)
            println("\n" + criaTerreno(terreno, leContadoresDoFicheiro(numLinhas, numColunas, true),
                    leContadoresDoFicheiro(numLinhas, numColunas, false), true, true))
            do {
                println("Coordenadas da tenda? (ex: 1,B)")
                val coords: String? = readlnOrNull().toString()
                if (coords == "sair") {
                    return }
                val processaCoordenada = processaCoordenadas(coords, numLinhas, numColunas)
                val contadoresVerticais = leContadoresDoFicheiro(terreno.size, terreno[0].size, true)
                val contadoresHorizontais = leContadoresDoFicheiro(terreno.size, terreno[0].size, false)
                if (processaCoordenada != null) {
                    if (colocaTenda(terreno, processaCoordenada)) {
                        println("\n" + criaTerreno(terreno, contadoresVerticais, contadoresHorizontais, true, true))
                    } else {
                        println("Tenda nao pode ser colocada nestas coordenadas") } }
            } while (!(terminouJogo(terreno, contadoresVerticais, contadoresHorizontais)))
            println("Parabens! Terminou o jogo!") }
    } while(true) }
