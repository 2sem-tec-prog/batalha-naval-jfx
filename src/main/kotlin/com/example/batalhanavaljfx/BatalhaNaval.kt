package com.example.batalhanavaljfx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.random.Random

class BatalhaNaval : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(BatalhaNaval::class.java.getResource("menu-view.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "Batalha Naval"
        stage.scene = scene
        stage.icons.add(Image(BatalhaNaval::class.java.getResourceAsStream("/batalha-naval.png")))

        stage.show()
    }

}

fun main() {
    Application.launch(BatalhaNaval::class.java)
}

fun criarTabuleiro(tamanho: Int, dificuldade: String): Array<Array<Char>>{
    //aqui nao foi necessario perguntar o tamanho pois esta solicitando no menu

    //Cria o array de 10 linhas por 10 colunas com todos os valora vazio' '
    val tabuleiro = Array(tamanho) { Array(tamanho) { ' ' } }
    //cria uma lista de navio que depois serão inserido no array
    var todosNavios:List<Char>
    while (true){
        when (dificuldade) {
            "Fácil" -> {
                todosNavios =
                    List(max(1, (tamanho * tamanho * 0.2).roundToInt())) { 'P' } +
                            List(max(1, (tamanho * tamanho * 0.15).roundToInt())) { 'C' } +
                            List(max(1, (tamanho * tamanho * 0.07).roundToInt())) { 'R' }
                break
            }
            "Intermediário" -> {
                todosNavios =
                    List(max(1, (tamanho * tamanho * 0.1).roundToInt())) { 'P' } +
                            List(max(1, (tamanho * tamanho * 0.01).roundToInt())) { 'C' } +
                            List(max(1, (tamanho * tamanho * 0.02).roundToInt())) { 'R' }
                break
            }
            "Difícil" -> {
                todosNavios =
                    List(max(1, (tamanho * tamanho * 0.07).roundToInt())) { 'P' } +
                            List(max(1, (tamanho * tamanho * 0.01).roundToInt())) { 'C' } +
                            List(max(1, (tamanho * tamanho * 0.01).roundToInt())) { 'R' }
                break
            }
            else -> {
                continue
            }
        }
    }



    //para cada navio na lista ele vai pegar uma linha e coluna aleatórios para alterar
    for (navio in todosNavios) {
        var vago = false
        while (!vago) {
            val lin = Random.nextInt(tamanho) //pega numeros aleatorios
            val col = Random.nextInt(tamanho)

            if (tabuleiro[lin][col] == ' ') { //se a coluna do array estiver vazio é inserido o navio
                tabuleiro[lin][col] = navio
                vago = true
            }
        }
    }
    return tabuleiro
}

fun distanciaMaisProxima(tabuleiro: Array<Array<Char>>, x: Int, y: Int): Int? {
    val opcoes = charArrayOf('P','p','C','c','R', 'r')

    for (dist in 1..3) {
        for (dx in -dist..dist) {
            for (dy in -dist..dist) {
                if (dx == 0 && dy == 0) continue

                val novoX = x + dx
                val novoY = y + dy


                if (novoX in tabuleiro.indices && novoY in tabuleiro[novoX].indices) {
                    val celula = tabuleiro[novoX][novoY]
                    if (celula in opcoes) {
                        return dist
                    }
                }
            }
        }
    }
    return null
}

