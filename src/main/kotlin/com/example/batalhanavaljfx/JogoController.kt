package com.example.batalhanavaljfx
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.GridPane

class JogoController {
    private var tamanho: Int = 0
    private lateinit var dificuldade: String

    lateinit var tabuleiro: Array<Array<Char>>

    @FXML
    private lateinit var gridTabuleiro: GridPane

    fun receberDados(tamanho: Int, dificuldade: String) {
        this.tamanho = tamanho
        this.dificuldade = dificuldade
        tabuleiro = criarTabuleiro(tamanho, dificuldade)

        renderizarTabuleiro(tabuleiro)

    }

    private fun renderizarTabuleiro(tabuleiro: Array<Array<Char>>) {
        gridTabuleiro.children.clear()

        for (linha in tabuleiro.indices) {
            for (coluna in tabuleiro[linha].indices) {
                val celula = Button("~")
                celula.setMinSize(40.0, 40.0)
                celula.setMaxSize(40.0, 40.0)

                // ação ao clicar
                celula.setOnAction {
                    val conteudo = tabuleiro[linha][coluna]
                    when(conteudo) {
                        'P' -> {
                            celula.text = "P"
                        }
                        'C' -> {
                            celula.text = "C"
                        }
                        'R' -> {
                            celula.text = "R"
                        }
                        'p' -> {
                            celula.text = "p"
                        }
                        'c' -> {
                            celula.text = "c"
                        }
                        'r' -> {
                            celula.text = "r"
                        }
                        '~' -> {
                            celula.text = "🌊"
                        }
                        '1' -> {
                            celula.text = "1"
                        }
                        '2' -> {
                            celula.text = "2"
                        }
                        'M' -> {
                            celula.text = "M"
                        }
                        else -> {
                            celula.text = "🌊"
                        }


                    }

                    celula.isDisable = true
                }

                gridTabuleiro.add(celula, coluna, linha)

            }

        }

    }



}






