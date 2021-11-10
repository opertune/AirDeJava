package com.example.airdejava;

import com.example.airdejava.controller.mainSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainApplication extends Application {
    private static mainSceneController controller1;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainScene = new FXMLLoader(mainApplication.class.getResource("mainScene.fxml"));
        Scene main = new Scene(mainScene.load());
        controller1 = mainScene.getController();
        stage.setResizable(false);
        stage.setTitle("AirDeJava");
        stage.setScene(main);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static mainSceneController getController(){
        return controller1;
    }
}