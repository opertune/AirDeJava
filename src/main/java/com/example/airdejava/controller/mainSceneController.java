package com.example.airdejava.controller;

import com.example.airdejava.mainApplication;
import com.example.airdejava.utils.Constant;
import com.example.airdejava.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class mainSceneController implements Initializable, Constant {
    private Connection connection;
    private ObservableList<String> data = FXCollections.observableArrayList();
    private int index;

    @FXML
    private Button btnLogin;
    @FXML
    private Label lblWelcome;
    @FXML
    private TextField txtTitre, txtGroupe, txtDuree, txtNbGroupe, txtPaysRegion, txtRencontre, txtInstrument;
    @FXML
    private ComboBox<String> cbSpec;
    @FXML
    private ListView<String> lvResult;
    @FXML
    private ComboBox<String> cbbInterrogation;
    @FXML
    private ComboBox<String> cbbSigneDuree;

    @FXML
    void keyTypedTxtTitre(KeyEvent event) {
        utilsRequest();
    }
    @FXML
    void keyTypedTxtGroupe(KeyEvent event) {
        utilsRequest();
    }
    @FXML
    void keyTypedTxtRencontre(KeyEvent event) {
        utilsRequest();
    }
    @FXML
    void onChangeCbbSpec(ActionEvent event) {
        utilsRequest();
    }
    @FXML
    void onChangeCbbSigneDuree(ActionEvent event) {
        utilsRequest();
    }
    @FXML
    void keyTypedTxtDuree(KeyEvent event) {
        if (txtDuree.getText() == "") txtDuree.setText("0");
        utilsRequest();
    }
    @FXML
    void keyTypedTxtPaysRegion(KeyEvent event) {
        utilsRequest();
    }
    @FXML
    void keyTypedTxtNbGroupe(KeyEvent event) {
        if (txtNbGroupe.getText() == "") txtNbGroupe.setText("0");
        utilsRequest();
    }
    @FXML
    void keyTypedTxtInstrument(KeyEvent event) {
        utilsRequest();
    }
    @FXML
    void clickBtnLogin(ActionEvent event) throws IOException {
        // Open login scene
        Stage stage = new Stage();
        FXMLLoader loginScene = new FXMLLoader(mainApplication.class.getResource("loginScene.fxml"));
        Scene login = new Scene(loginScene.load());
        stage.setTitle("Login");
        stage.setScene(login);
        stage.show();
        if(stage.isShowing()){
            btnLogin.setDisable(true);
        }
    }

    // Get selected instruction index
    @FXML
    void cbbInterrogationEvent(ActionEvent event) {
        index = cbbInterrogation.getSelectionModel().getSelectedIndex();
        switch (index){
            case 0 :
                disableTextField();
                txtTitre.setDisable(false);
                break;
            case 1 :
                disableTextField();
                txtTitre.setDisable(false);
                txtGroupe.setDisable(false);
                break;
            case 2 :
                disableTextField();
                cbSpec.setDisable(false);
                txtRencontre.setDisable(false);
                break;
            case 3 :
                disableTextField();
                txtDuree.setDisable(false);
                txtDuree.setText("0");
                txtPaysRegion.setDisable(false);
                cbbSigneDuree.setDisable(false);
                break;
            case 4 :
                disableTextField();
                txtNbGroupe.setDisable(false);
                txtNbGroupe.setText("0");
                break;
            case 5 :
                disableTextField();
                txtInstrument.setDisable(false);
                break;
            case 6 :
                disableTextField();
                txtPaysRegion.setDisable(false);
                txtGroupe.setDisable(false);
                break;
        }
        data.clear();
        lvResult.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init database connection
        connection = Utils.databaseConnection();
        // Set welcome label
        updateLbl("Welcome ! you are loged in with "+ USER.get_roleName() +" role");
        // init comboBox
        ObservableList<String> options = FXCollections.observableArrayList("soliste", "choriste", "musicien", "chanteur");
        cbSpec.setItems(FXCollections.observableArrayList("soliste", "choriste", "musicien", "chanteur"));
        cbbInterrogation.setItems(FXCollections.observableArrayList("Groupe par titre", "Rencontre par titre + groupe", "Membre par spécialité + rencontre",
                "Titre par durée + pays/région", "Rencontre par nombre de groupe", "Rencontre par instrument", "Planning rencontre par lieu + groupe"));
        cbbSigneDuree.setItems(FXCollections.observableArrayList("==", ">=", "<="));
        cbbSigneDuree.getSelectionModel().select(1);
    }

    // Disable and clear all textfield
    private void disableTextField(){
        cbSpec.setDisable(true);
        cbSpec.getSelectionModel().select(-1);
        txtGroupe.setDisable(true);
        txtGroupe.clear();
        txtTitre.setDisable(true);
        txtTitre.clear();
        txtDuree.setDisable(true);
        txtDuree.clear();
        txtInstrument.setDisable(true);
        txtInstrument.clear();
        txtPaysRegion.setDisable(true);
        txtPaysRegion.clear();
        txtNbGroupe.setDisable(true);
        txtNbGroupe.clear();
        txtRencontre.setDisable(true);
        txtRencontre.clear();
        cbbSigneDuree.setDisable(true);
        cbbSigneDuree.getSelectionModel().select(1);
    }
    // Update welcome label
    public void updateLbl(String txt){
        this.lblWelcome.setText(txt);
    }

    // Update login button
    public void updateBtn(boolean bool){
        this.btnLogin.setDisable(bool);
    }

    // call utils request sql
    private void utilsRequest(){
        Utils.callProcedure(index, data, connection, lvResult, txtTitre, txtGroupe, txtRencontre, cbSpec.getValue(), cbbSigneDuree.getSelectionModel().getSelectedIndex(), txtDuree, txtPaysRegion, txtNbGroupe, txtInstrument);
    }
}
