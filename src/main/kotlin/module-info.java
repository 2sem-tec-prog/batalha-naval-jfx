module com.example.batalhanavaljfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.example.batalhanavaljfx to javafx.fxml;
    exports com.example.batalhanavaljfx;
}