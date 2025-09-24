package com.example.batalhanavaljfx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class BatalhaNaval : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(BatalhaNaval::class.java.getResource("/com/example/batalhanavaljfx/menu-view.fxml"))
        val scene = Scene(fxmlLoader.load())
        scene.stylesheets.add(BatalhaNaval::class.java.getResource("/styles.css").toExternalForm())
        stage.title = "Batalha Naval"
        stage.scene = scene

        val iconStream = BatalhaNaval::class.java.getResourceAsStream("/com/example/batalhanavaljfx/batalha-naval.png")
        if (iconStream != null) {
            stage.icons.add(Image(iconStream))
        }

        stage.show()
    }
}

fun main() {
    Application.launch(BatalhaNaval::class.java)
}
