module com.example.airdejava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.airdejava to javafx.fxml;
    exports com.example.airdejava;
    exports com.example.airdejava.controller;
    opens com.example.airdejava.controller to javafx.fxml;
}