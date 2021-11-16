package com.example.airdejava.controller;

import com.example.airdejava.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
    @FXML
    private TextField txtNomPersonne, txtPrenomPersonne, txtCivilitePersonne, txtAdressePersonne, txtTelephonePersonne, txtFaxPersonne, txtEmailPersonne;

    @FXML
    void clickBtnAddPersonne(ActionEvent event) {
        Connection connection = Utils.databaseConnection();
        if(!txtNomPersonne.getText().equals("") && !txtTelephonePersonne.getText().equals("") && !txtEmailPersonne.getText().equals("")){
            try{
                CallableStatement statement = connection.prepareCall("{CALL insertPersonne(?, ?, ?, ?, ?, ?, ?, ?)}");
                statement.setString(1, null);
                statement.setString(2, txtNomPersonne.getText());
                statement.setString(3, txtPrenomPersonne.getText());
                statement.setString(4, txtCivilitePersonne.getText());
                statement.setString(5, txtAdressePersonne.getText());
                statement.setString(6, txtTelephonePersonne.getText());
                statement.setString(7, txtFaxPersonne.getText());
                statement.setString(8, txtEmailPersonne.getText());
                statement.execute();
                connection.close();
                txtNomPersonne.clear(); txtPrenomPersonne.clear(); txtCivilitePersonne.clear(); txtAdressePersonne.clear();
                txtTelephonePersonne.clear(); txtFaxPersonne.clear(); txtEmailPersonne.clear();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
