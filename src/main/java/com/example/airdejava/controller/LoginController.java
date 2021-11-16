package com.example.airdejava.controller;

import com.example.airdejava.utils.Constant;
import com.example.airdejava.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.sql.*;
import com.example.airdejava.mainApplication;

public class LoginController implements Constant {
    @FXML
    private Pane loginScene;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblErrorLogin;

    @FXML
    void clickBtnSend(ActionEvent event) {
        if(!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()){
            Connection connectDB = Utils.databaseConnection();
            // Request username and role from database
            try{
                // Call stored procedure
                CallableStatement statement = connectDB.prepareCall("{CALL loginCheck(?, ?, ?, ?)}");

                // Set parameters
                statement.setString(1, txtUsername.getText());
                statement.setString(2, txtPassword.getText());
                statement.registerOutParameter(3, Types.INTEGER);
                statement.registerOutParameter(4, Types.VARCHAR);
                statement.execute();

                // Get output result
                int outRole = statement.getInt(3);
                String outUsername = statement.getString(4);

                // Close connection
                statement.close();
                System.out.println("Connection close");

                if(outUsername != "null" && outRole != 0){
                    // Set user name and role
                    USER.set_username(outUsername);
                    USER.set_role(outRole);
                    USER.set_roleName();
                    // Update login button and welcome label
                    mainApplication.getController().updateBtn(false);
                    mainApplication.getController().updateLbl("Bienvenu "+ USER.get_username() +" ! vous êtes connecté en tant que "+ USER.get_roleName());
                    if (USER.get_role() == 1) {
                        mainApplication.getController().updateAdminBtn(true);
                    } else {
                        mainApplication.getController().updateAdminBtn(false);
                    }
                    // Hide login scene
                    loginScene.getScene().getWindow().hide();
                    lblErrorLogin.setText("");
                }else {
                    lblErrorLogin.setText("Le pseudo et le mot de passe ne correspondent pas ou le compte n'existe pas.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            lblErrorLogin.setText("Requiert un pseudo et un mot de passe !");
        }
    }
}
