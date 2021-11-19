package com.example.airdejava.controller;

import com.example.airdejava.mainApplication;
import com.example.airdejava.utils.Constant;
import com.example.airdejava.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable, Constant {
    private ObservableList<String> data = FXCollections.observableArrayList();
    private int index;

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnAdminPanel;
    @FXML
    private Label lblWelcome;
    @FXML
    private TextField txtTitre, txtGroupe, txtDuree, txtNbGroupe, txtPaysRegion, txtRencontre, txtInstrument, txtLieuRencontre;
    @FXML
    private ComboBox<String> cbSpec;
    @FXML
    private ComboBox<String> cbbInterrogation;
    @FXML
    private ComboBox<String> cbbSigneDuree;
    @FXML
    private AnchorPane apScrollPane;
    @FXML
    private TableView<?> tvResult;

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
        if (txtDuree.getText().equals("")) txtDuree.setText("0");
        utilsRequest();
    }
    @FXML
    void keyTypedTxtPaysRegion(KeyEvent event) {
        utilsRequest();
    }
    @FXML
    void keyTypedTxtNbGroupe(KeyEvent event) {
        if (txtNbGroupe.getText().equals("")) txtNbGroupe.setText("0");
        utilsRequest();
    }
    @FXML
    void keyTypedTxtInstrument(KeyEvent event) {
        utilsRequest();
    }
    @FXML
    void keyTypedTxtLieuRencontre(KeyEvent event) {
        utilsRequest();
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
                txtLieuRencontre.setDisable(false);
                txtGroupe.setDisable(false);
                break;
        }
        data.clear();
        tvResult.getColumns().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hide admin button
        btnAdminPanel.setVisible(false);
        // Set welcome label
        updateLbl("Bienvenu ! vous êtes connecté en tant qu'"+ USER.get_roleName());
        // init comboBox
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
        txtLieuRencontre.setDisable(true);
        txtLieuRencontre.clear();
    }
    // Update welcome label
    public void updateLbl(String txt){
        this.lblWelcome.setText(txt);
    }
    // Update login button
    public void updateBtn(boolean bool){
        this.btnLogin.setDisable(bool);
    }
    // Update admin panel button
    public void updateAdminBtn(boolean bool) { this.btnAdminPanel.setVisible(bool);}

    // Call utils request sql
    private void utilsRequest() {
        // Init database connection
        Connection connection = Utils.databaseConnection();
        Utils.callProcedure(apScrollPane,connection, index, data, txtTitre, txtGroupe, txtRencontre, cbSpec.getValue(),
                cbbSigneDuree.getSelectionModel().getSelectedIndex(), txtDuree, txtPaysRegion, txtNbGroupe, txtInstrument, txtLieuRencontre, tvResult);
        // Close connection
        try {
            connection.close();
        }catch (Exception e){e.printStackTrace();}
    }

    // Open login panel
    @FXML
    void clickBtnLogin(ActionEvent event) throws IOException {
        // Open login scene
        Stage stage = new Stage();
        FXMLLoader loginScene = new FXMLLoader(mainApplication.class.getResource("loginScene.fxml"));
        Scene login = new Scene(loginScene.load());
        stage.setTitle("Connection");
        stage.setScene(login);
        stage.setResizable(false);
        stage.show();
        if(stage.isShowing()){
            btnLogin.setDisable(true);
        }

        // Enable login button when user close login stage
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                btnLogin.setDisable(false);
            }
        });
    }

    // Open admin panel
    @FXML
    void clickBtnAdminPanel(ActionEvent event) throws IOException {
        // Open adminPanel scene
        Stage stage = new Stage();
        FXMLLoader adminPanel = new FXMLLoader(mainApplication.class.getResource("adminPanel.fxml"));
        Scene admin = new Scene(adminPanel.load());
        stage.setTitle("Admin Panel");
        stage.setScene(admin);
        stage.setResizable(false);
        stage.show();
        if(stage.isShowing()){
            btnAdminPanel.setDisable(true);
        }

        // Enable admin panel button when user close admin panel stage
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                btnAdminPanel.setDisable(false);
            }
        });
    }

    // TEXTFIELD AUTO COMPLETE
    // create vbox, pos vbox, create label avec le resultat qui contient les char, insert label
    @FXML
    private VBox vBoxAutoComplete;
    @FXML
    private TextField txtAutoComplete;
    @FXML
    private Label lblAutoComplete;

    @FXML
    void txtAutoCompleteInput(KeyEvent event) throws SQLException {
        Connection connection = Utils.databaseConnection();
        CallableStatement statement = connection.prepareCall("{CALL selectTitleAutoComplete(?)}");
        statement.setString(1, "%"+txtAutoComplete.getText()+"%");
        ResultSet resultSet = statement.executeQuery();
        autoComplete(resultSet);
        connection.close();
        if(txtAutoComplete.getText().equals("")){
            vBoxAutoComplete.getChildren().clear();
            vBoxAutoComplete.setVisible(false);
        }else{
            vBoxAutoComplete.setVisible(true);
        }
    }
    private void autoComplete(ResultSet resultSet) throws SQLException {
        vBoxAutoComplete.getChildren().clear();
        while (resultSet.next()){
            for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++){
                Label lbl = new Label();
                lbl.setText(resultSet.getString(i));
                lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        lblAutoComplete.setText("");
                        lblAutoComplete.setText(lbl.getText());
                    }
                });
                lbl.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        lbl.setStyle("-fx-background-color: #2596be;");
                    }
                });
                lbl.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        lbl.setStyle("-fx-background-color: white;");
                    }
                });
                vBoxAutoComplete.getChildren().add(lbl);
            }
        }
    }
}