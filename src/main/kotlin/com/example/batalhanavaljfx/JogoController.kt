package com.example.batalhanavaljfx

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import javafx.scene.text.Text
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import kotlin.random.Random

class JogoController {

    companion object {
        const val SPRITE_ACERTO = "/sprites/ship-hit.png"
        const val SPRITE_AGUA = "/sprites/water.png"
        const val SPRITE_NAVIO = "/sprites/ship.png"

        const val TABULEIRO_PADRAO = 10
        const val TENTATIVAS_PADRAO = 15
        const val QUANT_P = 10
        const val QUANT_C = 1
        const val QUANT_R = 2
        const val TOTAL_NAVIOS = QUANT_P + QUANT_C + QUANT_R
    }

    private var tamanho: Int = TABULEIRO_PADRAO
    private var tentativasMax: Int = TENTATIVAS_PADRAO
    private var jogadas: Int = 0
    private var acertos: Int = 0
    private var pontos: Int = 0

    private val coordenadasJogadas = mutableSetOf<Pair<Int, Int>>()
    private lateinit var tabuleiro: Array<Array<Char>>
    private val historico = mutableListOf<String>()

    @FXML
    private lateinit var mensagemLabel: Text
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
        this.tentativasMax = TENTATIVAS_PADRAO
        resetEstado()
        tabuleiro = criarTabuleiro(tamanho, dificuldade)
        atualizarLabels()
        renderizarTabuleiro()
    }

    private fun resetEstado() {
        jogadas = 0
        acertos = 0
        pontos = 0
        coordenadasJogadas.clear()
        historico.clear()
    }

    private fun criarTabuleiro(tamanho: Int, dificuldade: String): Array<Array<Char>> {
        val board = Array(tamanho) { Array(tamanho) { ' ' } }
        val frota = gerarFrotaPadrao()
        posicionarFrotaAleatoriamente(board, frota)
        return board
    }

    private fun gerarFrotaPadrao(): List<Char> {
        val lista = mutableListOf<Char>()
        repeat(QUANT_P) { lista += 'P' }
        repeat(QUANT_C) { lista += 'C' }
        repeat(QUANT_R) { lista += 'R' }
        return lista
    }

    private fun posicionarFrotaAleatoriamente(tab: Array<Array<Char>>, frota: List<Char>) {
        for (navio in frota) {
            var colocado = false
            while (!colocado) {
                val lin = Random.nextInt(tab.size)
                val col = Random.nextInt(tab.size)
                if (tab[lin][col] == ' ') {
                    tab[lin][col] = navio
                    colocado = true
                }
            }
        }
    }

    private fun renderizarTabuleiro() {
        gridTabuleiro.children.clear()
        gridTabuleiro.columnConstraints.clear()
        gridTabuleiro.rowConstraints.clear()

        for (i in 0 until tamanho) {
            val cc = ColumnConstraints()
            cc.percentWidth = 100.0 / tamanho
            gridTabuleiro.columnConstraints.add(cc)

            val rc = RowConstraints()
            rc.percentHeight = 100.0 / tamanho
            gridTabuleiro.rowConstraints.add(rc)
        }

        for (lin in 0 until tamanho) {
            for (col in 0 until tamanho) {
                val btn = Button()
                btn.styleClass.add("cell-button")
                btn.isFocusTraversable = false
                btn.text = ""

                btn.maxWidth = Double.MAX_VALUE
                btn.maxHeight = Double.MAX_VALUE

                GridPane.setFillWidth(btn, true)
                GridPane.setFillHeight(btn, true)

                btn.setOnAction {
                    tratarCliqueCelula(btn, lin, col)
                }
                gridTabuleiro.add(btn, col, lin)
            }
        }
    }

    private fun tratarCliqueCelula(botao: Button, lin: Int, col: Int) {
        if (lin !in 0 until tamanho || col !in 0 until tamanho) return
        val coord = lin to col
        if (coord in coordenadasJogadas) return
        coordenadasJogadas += coord

        val conteudo = tabuleiro[lin][col]
        when (conteudo) {
            'P' -> lidarAcerto(botao, lin, col, "Porta-aviões", 5)
            'C' -> lidarAcerto(botao, lin, col, "Cruzador", 15)
            'R' -> lidarAcerto(botao, lin, col, "Rebocador", 10)
            else -> lidarErro(botao, lin, col)
        }

        jogadas++
        atualizarLabels()
        botao.isDisable = true

        if (acertos == TOTAL_NAVIOS) {
            finalizarJogo("Parabéns! Você destruiu todos os navios!")
        } else if (jogadas >= tentativasMax) {
            finalizarJogo("Fim de jogo! Você usou todas as $tentativasMax tentativas.")
        }
    }

    private fun lidarAcerto(botao: Button, lin: Int, col: Int, tipo: String, pontosGanhos: Int) {
        tabuleiro[lin][col] = quandoNavioMarcado(tipo)
        acertos++
        pontos += pontosGanhos
        historico += "Acertou $tipo (+$pontosGanhos pontos)"
        botao.styleClass.add("cell-hit")
        aplicarSpriteSeExistir(botao, SPRITE_ACERTO)
        botao.tooltip = javafx.scene.control.Tooltip("Alvo atingido! $tipo abatido!")
        mensagemLabel.text = "Você atingiu um $tipo! (+$pontosGanhos pontos)"
    }

    private fun quandoNavioMarcado(tipo: String): Char {
        return when (tipo) {
            "Porta-aviões" -> 'p'
            "Cruzador" -> 'c'
            "Rebocador" -> 'r'
            else -> 'p'
        }
    }

    private fun lidarErro(botao: Button, lin: Int, col: Int) {
        val distancia = distanciaMaisProxima(tabuleiro, lin, col)

        val textoHistorico = when (distancia) {
            1 -> "Água, mas a 1 casa de um navio"
            2 -> "Água, mas a 2 casas de um navio"
            3 -> "Água, a 3+ casas de distância"
            else -> "Água, longe de qualquer navio"
        }
        historico += textoHistorico
        botao.styleClass.add("cell-miss")
        aplicarSpriteSeExistir(botao, SPRITE_AGUA)
        botao.tooltip = javafx.scene.control.Tooltip(textoHistorico)

        mensagemLabel.text = textoHistorico

        val distanciaTexto = when (distancia) {
            1 -> "1"
            2 -> "2"
            3 -> "M"
            else -> "~"
        }

        val label = javafx.scene.control.Label(distanciaTexto)
        label.style = "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;"

        val stack = javafx.scene.layout.StackPane()
        stack.children.addAll(botao.graphic, label)

        botao.graphic = stack
    }

    private fun atualizarLabels() {
        tentativasLabel.text = "Tentativas: ${tentativasMax - jogadas}"
        acertosLabel.text = "Acertos: $acertos"
        pontuacaoLabel.text = "Pontuação: $pontos"
    }

    private fun distanciaMaisProxima(tab: Array<Array<Char>>, x: Int, y: Int): Int? {
        val navios = charArrayOf('P', 'p', 'C', 'c', 'R', 'r')
        for (dist in 1..3) {
            for (dx in -dist..dist) {
                for (dy in -dist..dist) {
                    if (dx == 0 && dy == 0) continue
                    val nx = x + dx
                    val ny = y + dy
                    if (nx in tab.indices && ny in tab[nx].indices) {
                        if (tab[nx][ny] in navios) return dist
                    }
                }
            }
        }
        return null
    }

    private fun finalizarJogo(mensagem: String) {
        for (node in gridTabuleiro.children) {
            val col = GridPane.getColumnIndex(node) ?: 0
            val row = GridPane.getRowIndex(node) ?: 0
            if (node is Button) {
                val cel = tabuleiro[row][col]
                if (cel == 'p' || cel == 'c' || cel == 'r') {
                    node.styleClass.removeAll("cell-miss")
                    node.styleClass.add("cell-hit")
                    node.text = when (cel) {
                        'p' -> "P"
                        'c' -> "C"
                        'r' -> "R"
                        else -> node.text
                    }
                } else if (cel == 'P' || cel == 'C' || cel == 'R') {
                    node.styleClass.add("cell-ship")
                    aplicarSpriteSeExistir(node, SPRITE_NAVIO)
                    node.text = when (cel) {
                        'P' -> "P"
                        'C' -> "C"
                        'R' -> "R"
                        else -> node.text
                    }
                }
                node.isDisable = true
            }
        }

        val resumo = StringBuilder()
        resumo.appendLine(mensagem)
        resumo.appendLine("Pontuação final: $pontos")
        resumo.appendLine("Acertos totais: $acertos de $TOTAL_NAVIOS")
        resumo.appendLine("--------------- Histórico ---------------")
        historico.forEachIndexed { i, s -> resumo.appendLine("Jogada ${i + 1}: $s") }

        val alert = javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION)
        alert.title = "Resumo da Partida"
        alert.headerText = "Jogo Finalizado"
        alert.contentText = resumo.toString()
        alert.showAndWait()

        val opc = javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION)
        opc.title = "Opção"
        opc.headerText = "Deseja jogar novamente?"
        opc.contentText = "Escolha Sim para reiniciar ou Não para voltar ao menu."
        val result = opc.showAndWait()
        if (result.isPresent && result.get() == javafx.scene.control.ButtonType.OK) {
            receberDados(tamanho, "Intermediário")
        } else {
            voltarAoMenu()
        }
    }

    private fun voltarAoMenu() {
        val loader = javafx.fxml.FXMLLoader(javaClass.getResource("/com/example/batalhanavaljfx/menu-view.fxml"))
        val root = loader.load<javafx.scene.Parent>()
        val stage = (gridTabuleiro.scene.window) as javafx.stage.Stage
        stage.scene = javafx.scene.Scene(root)
        stage.scene.stylesheets.add(javaClass.getResource("/styles.css").toExternalForm())
        stage.sizeToScene()
    }

    private fun aplicarSpriteSeExistir(botao: Button, caminho: String) {
        try {
            val isr = javaClass.getResourceAsStream(caminho)
            if (isr != null) {
                val img = Image(isr)
                val iv = ImageView(img)
                iv.isPreserveRatio = true

                val cellSize = minOf(
                    botao.width.takeIf { it > 0 } ?: gridTabuleiro.width / tamanho,
                    botao.height.takeIf { it > 0 } ?: gridTabuleiro.height / tamanho
                ) * 0.8

                iv.fitWidth = cellSize
                iv.fitHeight = cellSize

                botao.graphic = iv
            }
        } catch (_: Exception) { }
    }
}
