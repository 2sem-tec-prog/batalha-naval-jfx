package com.example.batalhanavaljfx

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.Node
import javafx.event.ActionEvent
import javafx.scene.control.ComboBox

class MenuController {
    @FXML
    private lateinit var dificuldade: ComboBox<String>

    @FXML
    private lateinit var tamanho: ComboBox<Int>

    @FXML
    fun initialize() {
        dificuldade.items.addAll("Fácil", "Intermediário", "Difícil")
        dificuldade.value = "Intermediário"
        tamanho.items.addAll(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        tamanho.value = 10
    }

    @FXML
    fun onJogarClick(event: ActionEvent) {
        val loader = FXMLLoader(javaClass.getResource("/com/example/batalhanavaljfx/jogo-view.fxml"))
        val root: Parent = loader.load()
        val tam = tamanho.value ?: 10
        val dif = dificuldade.value ?: "Intermediário"

        val jogoController = loader.getController<JogoController>()
        jogoController.receberDados(tam, dif)

        val stage = (event.source as Node).scene.window as Stage
        stage.scene = Scene(root)
        stage.scene.stylesheets.add(javaClass.getResource("/styles.css").toExternalForm())
        stage.show()
    }
}
