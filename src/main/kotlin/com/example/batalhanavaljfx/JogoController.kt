package com.example.batalhanavaljfx
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import javafx.scene.text.Text
import kotlin.math.roundToInt

class JogoController {
    private var tamanho: Int = 0
    private lateinit var dificuldade: String
    private var tentativas = (tamanho * 1.5).roundToInt()

    private var jogadas: Int = 0
    private var acertos: Int = 0
    private var soma: Int = 0
    lateinit var historicoLinha: Array<String?>
    lateinit var historicoColuna: Array<String?>
    lateinit var historicoJogadas: Array<String?>

    lateinit var tabuleiro: Array<Array<Char>>

    @FXML
    private lateinit var gridTabuleiro: GridPane
    @FXML
    private lateinit var tentativasLabel: Text
    @FXML
    private lateinit var pontuacaoLabel: Text
    @FXML
    private lateinit var acertosLabel: Text



    fun receberDados(tamanho: Int, dificuldade: String) {
        this.tamanho = tamanho
        this.dificuldade = dificuldade
        this.tentativas = (tamanho*1.5).roundToInt()
        tentativasLabel.text = "Tentativas: $tentativas"

        historicoLinha = arrayOfNulls<String>(tentativas)
        historicoColuna = arrayOfNulls<String>(tentativas)
        historicoJogadas = arrayOfNulls<String>(tentativas)

        tabuleiro = criarTabuleiro(tamanho, dificuldade)


        renderizarTabuleiro(tabuleiro)

    }

    private fun renderizarTabuleiro(tabuleiro: Array<Array<Char>>) {
        gridTabuleiro.children.clear()

        for (i in 0..tamanho){
            val y = ColumnConstraints()
            y.percentWidth = 100.0 / tamanho
            gridTabuleiro.columnConstraints.add(y)

            val x = RowConstraints()
            x.percentHeight = 100.0 / tamanho
            gridTabuleiro.rowConstraints.add(x)


        }

        for (linha in tabuleiro.indices) {
            for (coluna in tabuleiro[linha].indices) {
                val celula = Button("~")
                celula.maxHeight = Double.MAX_VALUE
                celula.maxWidth = Double.MAX_VALUE


                // ação ao clicar
                celula.setOnAction {
                    tratarCliqueCelula(celula, linha, coluna)
                }
                gridTabuleiro.add(celula, coluna, linha)
            }

        }

    }

    private fun tratarCliqueCelula(celula: Button, linha: Int, coluna: Int) {
        val conteudo = tabuleiro[linha][coluna]
        when (conteudo) {
            'P' -> {
                //println("Alvo atingido! Porta-aviões abatido!") -> TROCAR POR SOM
                acertos++
                soma += 5
                historicoJogadas[jogadas] = "Porta-aviões, 5 pontos"
                celula.text = "P"
            }
            'C' -> {
                //println("Alvo atingido! Cruzador abatido!")
                acertos++
                soma += 15
                historicoJogadas[jogadas] = "Porta-aviões, 5 pontos"
                celula.text = "C"
            }
            'R' -> {
                //println("Alvo atingido! Rebocador abatido!")
                acertos++
                soma += 10
                historicoJogadas[jogadas] = "Porta-aviões, 5 pontos"
                celula.text = "R"
            }
            else -> {
                val distancia = distanciaMaisProxima(tabuleiro, linha, coluna)
                when(distancia){
                    1 -> {
                        historicoJogadas[jogadas] = "Errou, mas está a $distancia casa de distância"
                        celula.text = "1"
                    }
                    2 -> {
                        historicoJogadas[jogadas] = "Errou, mas está a $distancia casas de distância"
                        celula.text = "2"
                    }
                    3 -> {
                        historicoJogadas[jogadas] = "Errou, mas está a $distancia ou mais casas de distância"
                        celula.text = "M"
                    }

                }
            }
        }
        jogadas++
        if (jogadas == tentativas){
            //encerrarJogo()
        }

        tentativasLabel.text = "Tentativas: ${tentativas-jogadas}"
        acertosLabel.text = "Acertos: ${acertos}"
        pontuacaoLabel.text = "Pontuação: ${soma}"


        celula.isDisable = true
    }
}







