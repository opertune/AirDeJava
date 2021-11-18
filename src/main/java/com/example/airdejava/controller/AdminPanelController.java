package com.example.airdejava.controller;

import com.example.airdejava.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
    @FXML
    private TextField txtNomPersonne, txtPrenomPersonne, txtCivilitePersonne, txtAdressePersonne, txtTelephonePersonne, txtFaxPersonne, txtEmailPersonne, txtNomGroupe,
            txtNomRencontre, txtLieuRencontre, txtPeriodiciteRencontre, txtNbPersonneRencontre, txtTitreOeuvre, txtNomAuteurOeuvre, txtPrenomAuteurOeuvre,
            txtDureeOeuvre, txtTypeOeuvre, txtAddProgrammeHHDebut, txtAddProgrammeHHFin, txtAddProgrammeMMDebut, txtAddProgrammeMMFin, txtEstMembreInstrument,
            txtEstMembreResponsabilite, txtAddPays, txtAddRegion;

    @FXML
    private DatePicker dbDateRencontre, dbApparitionOeuvre, dbAddProgrammeDate;

    @FXML
    private ComboBox<String> cbbAddEstMembrePersonne, cbbAddEstMembreGroupe, cbbAddEstMembreSpecialite, cbbRepertoireOeuvre, cbbRepertoireGroupe,
            cbbAddProgrammeGroupe, cbbAddProgrammeRencontre, cbbEstOrganisateurPersonne, cbbEstOrganisateurRencontre,
            cbbEstCorrespondantPersonne, cbbEstCorrespondantGroupe, ccbAddRegionPays, cbbAddGroupeRegion;

    // Add new personne in database
    @FXML
    void clickBtnAddPersonne(ActionEvent event) {
        if(!txtNomPersonne.getText().equals("") && !txtTelephonePersonne.getText().equals("") && !txtEmailPersonne.getText().equals("")){
            try{
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertPersonne(?, ?, ?, ?, ?, ?, ?, ?)}");
                statement.setString(1, null);
                statement.setString(2, txtNomPersonne.getText());
                statement.setString(3, txtPrenomPersonne.getText().equals("") ? null : txtPrenomPersonne.getText());
                statement.setString(4, txtCivilitePersonne.getText().equals("") ? null : txtCivilitePersonne.getText());
                statement.setString(5, txtAdressePersonne.getText().equals("") ? null : txtAdressePersonne.getText());
                statement.setString(6, txtTelephonePersonne.getText());
                statement.setString(7, txtFaxPersonne.getText().equals("") ? null : txtFaxPersonne.getText());
                statement.setString(8, txtEmailPersonne.getText());
                statement.execute();
                connection.close();
                txtNomPersonne.clear(); txtPrenomPersonne.clear(); txtCivilitePersonne.clear(); txtAdressePersonne.clear();
                txtTelephonePersonne.clear(); txtFaxPersonne.clear(); txtEmailPersonne.clear();
                comboBoxPersonne();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add groupe in database
    @FXML
    void clickBtnAddGroupe(ActionEvent event) {
        if(!txtNomGroupe.getText().equals("")){
            try {
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertGroupe(?, ?)}");
                statement.setString(1, txtNomGroupe.getText());
                statement.setInt(2, Integer.parseInt(cbbAddGroupeRegion.getValue().substring(cbbAddGroupeRegion.getValue().lastIndexOf(":")+2)));
                statement.execute();
                connection.close();
                txtNomGroupe.clear();
                comboBoxNomGroupe();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add rencontre in database
    @FXML
    void clickBtnAddRencontre(ActionEvent event) {
        if(!txtNomRencontre.getText().equals("") && !txtLieuRencontre.getText().equals("") && dbDateRencontre.getValue() != null &&
                !txtPeriodiciteRencontre.getText().equals("") && !txtNbPersonneRencontre.getText().equals("")){
            try{
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertRencontre(?, ?, ?, ?, ?)}");
                statement.setString(1, txtNomRencontre.getText());
                statement.setString(2, txtLieuRencontre.getText());
                statement.setDate(3, Date.valueOf(dbDateRencontre.getValue()));
                statement.setString(4, txtPeriodiciteRencontre.getText());
                statement.setString(5, txtNbPersonneRencontre.getText());
                statement.execute();
                connection.close();
                txtNomRencontre.clear(); txtLieuRencontre.clear(); dbDateRencontre.setValue(null); txtPeriodiciteRencontre.clear(); txtNbPersonneRencontre.clear();
                comboBoxRencontre();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add oeuvre in database
    @FXML
    void btnClickAddOeuvre(ActionEvent event) {
        if(!txtTitreOeuvre.getText().equals("")  && !txtNomAuteurOeuvre.getText().equals("") && !txtDureeOeuvre.getText().equals("")){
            try{
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertOeuvre(?, ?, ?, ?, ?, ?)}");
                statement.setString(1, txtTitreOeuvre.getText());
                statement.setDate(2, dbApparitionOeuvre.getValue().equals(null) ? null : Date.valueOf(dbApparitionOeuvre.getValue()));
                statement.setString(3, txtNomAuteurOeuvre.getText());
                statement.setString(4, txtPrenomAuteurOeuvre.getText().equals("") ? null : txtPrenomAuteurOeuvre.getText());
                statement.setString(5, txtDureeOeuvre.getText());
                statement.setString(6, txtTypeOeuvre.getText());
                statement.execute();
                connection.close();
                txtTitreOeuvre.clear(); dbApparitionOeuvre.setValue(null); txtNomAuteurOeuvre.clear(); txtPrenomAuteurOeuvre.clear(); txtDureeOeuvre.clear(); txtTypeOeuvre.clear();
                comboBoxOeuvre();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add programme in database
    @FXML
    void btnClickAddProgramme(ActionEvent event) {
        if(cbbAddProgrammeGroupe.getValue() != null && dbAddProgrammeDate.getValue() != null && !txtAddProgrammeHHDebut.getText().equals("") &&
                !txtAddProgrammeMMDebut.getText().equals("") && !txtAddProgrammeHHFin.getText().equals("") && !txtAddProgrammeMMFin.getText().equals("") && cbbAddProgrammeRencontre.getValue() != null){
            try{
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertProgramme(?, ?, ?, ?, ?)}");
                statement.setString(1, cbbAddProgrammeGroupe.getValue().substring(0, cbbAddProgrammeGroupe.getValue().indexOf(":")-1));
                statement.setDate(2, Date.valueOf(dbAddProgrammeDate.getValue()));
                statement.setTime(3, Time.valueOf(txtAddProgrammeHHDebut.getText() + ":" + txtAddProgrammeMMDebut.getText() + ":" + 00));
                statement.setTime(4, Time.valueOf(txtAddProgrammeHHFin.getText() + ":" + txtAddProgrammeMMFin.getText() + ":" + 00));
                statement.setInt(5, Integer.parseInt(cbbAddProgrammeRencontre.getValue().substring(cbbAddProgrammeRencontre.getValue().indexOf(":")+2)));
                statement.execute();
                connection.close();
                cbbAddProgrammeGroupe.getSelectionModel().select(-1); dbAddProgrammeDate.setValue(null); txtAddProgrammeHHDebut.clear(); txtAddProgrammeMMDebut.clear();
                txtAddProgrammeHHFin.clear(); txtAddProgrammeMMFin.clear(); cbbAddProgrammeRencontre.getSelectionModel().select(-1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add membre in groupe in database
    @FXML
    void btnClickAddEstMembre(ActionEvent event) {
        if(cbbAddEstMembrePersonne.getValue() != null && cbbAddEstMembreGroupe.getValue() != null && cbbAddEstMembreSpecialite.getValue() != null){
            try {
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertEstMembre(?, ?, ?, ?, ?)}");
                statement.setInt(1, Integer.parseInt(cbbAddEstMembreGroupe.getValue().substring(cbbAddEstMembreGroupe.getValue().indexOf(":")+2)));
                statement.setInt(2, Integer.parseInt(cbbAddEstMembrePersonne.getValue().substring(cbbAddEstMembrePersonne.getValue().indexOf(":")+2)));
                statement.setString(3, cbbAddEstMembreSpecialite.getValue());
                statement.setString(4, txtEstMembreInstrument.getText().equals("") ? null : txtEstMembreInstrument.getText());
                statement.setString(5, txtEstMembreResponsabilite.getText().equals("") ? null : txtEstMembreResponsabilite.getText());
                statement.execute();
                connection.close();
                cbbAddEstMembreGroupe.getSelectionModel().select(-1); cbbAddEstMembrePersonne.getSelectionModel().select(-1); cbbAddEstMembreSpecialite.getSelectionModel().select(-1);
                txtEstMembreInstrument.clear(); txtEstMembreResponsabilite.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add an organisateur in rencontre in database
    @FXML
    void btnClickEstOrganisateur(ActionEvent event) {
        if(cbbEstOrganisateurPersonne.getValue() != null && cbbEstOrganisateurRencontre.getValue() != null){
            try{
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertEstOrganisateur(?, ?)}");
                statement.setInt(1, Integer.parseInt(cbbEstOrganisateurRencontre.getValue().substring(cbbEstOrganisateurRencontre.getValue().indexOf(":")+2)));
                statement.setInt(2, Integer.parseInt(cbbEstOrganisateurPersonne.getValue().substring(cbbEstOrganisateurPersonne.getValue().indexOf(":")+2)));
                statement.execute();
                connection.close();
                cbbEstOrganisateurRencontre.getSelectionModel().select(-1); cbbEstOrganisateurPersonne.getSelectionModel().select(-1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add a correspondant in groupe in database
    @FXML
    void btnClickEstCorrespondant(ActionEvent event) {
        if(cbbEstCorrespondantPersonne.getValue() != null && cbbEstCorrespondantGroupe.getValue() != null){
            try {
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertEstCorrespondant(?, ?)}");
                statement.setInt(1, Integer.parseInt(cbbEstCorrespondantGroupe.getValue().substring(cbbEstCorrespondantGroupe.getValue().indexOf(":")+2)));
                statement.setInt(2, Integer.parseInt(cbbEstCorrespondantPersonne.getValue().substring(cbbEstCorrespondantPersonne.getValue().indexOf(":")+2)));
                statement.execute();
                connection.close();
                cbbEstCorrespondantGroupe.getSelectionModel().select(-1); cbbEstCorrespondantPersonne.getSelectionModel().select(-1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add Oeuvre in repertoire
    @FXML
    void btnClickOeuvreInRepertoire(ActionEvent event) {
        if(cbbRepertoireOeuvre.getValue() != null && cbbRepertoireGroupe.getValue() != null){
            try {
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertRepertoire(?, ?)}");
                statement.setInt(1, Integer.parseInt(cbbRepertoireOeuvre.getValue().substring(cbbRepertoireOeuvre.getValue().indexOf(":")+2)));
                statement.setInt(2, Integer.parseInt(cbbRepertoireGroupe.getValue().substring(cbbRepertoireGroupe.getValue().indexOf(":")+2)));
                statement.execute();
                connection.close();
                cbbRepertoireOeuvre.getSelectionModel().select(-1); cbbRepertoireGroupe.getSelectionModel().select(-1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add pays in database
    @FXML
    void clickAddPays(ActionEvent event){
        if(!txtAddPays.getText().equals("")){
            try {
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertPays(?)}");
                statement.setString(1, txtAddPays.getText());
                statement.execute();
                connection.close();
                txtAddPays.clear();
                comboBoxPays();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add region in database
    @FXML
    void clickAddRegion(ActionEvent event){
        if(!txtAddRegion.getText().equals("")){
            try {
                Connection connection = Utils.databaseConnection();
                CallableStatement statement = connection.prepareCall("{CALL insertRegion(?, ?)}");
                statement.setString(1, txtAddRegion.getText());
                statement.setInt(2, Integer.parseInt(ccbAddRegionPays.getValue().substring(ccbAddRegionPays.getValue().indexOf(":")+2)));
                statement.execute();
                connection.close();
                txtAddRegion.clear(); ccbAddRegionPays.getSelectionModel().select(-1);
                comboBoxRegion();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Init combo box with groupe name
    private void comboBoxNomGroupe(){
        try{
            // Remove all item in combo box
            cbbAddProgrammeGroupe.getItems().removeAll();
            cbbAddEstMembreGroupe.getItems().removeAll();
            cbbEstCorrespondantGroupe.getItems().removeAll();
            cbbRepertoireGroupe.getItems().removeAll();

            // Select groupe name
            Connection connection = Utils.databaseConnection();
            CallableStatement statement = connection.prepareCall("{CALL selectNomGroupe}");
            ResultSet resultSet = statement.executeQuery();
            ObservableList nomGroupe = FXCollections.observableArrayList();
            while (resultSet.next()){
                nomGroupe.add(resultSet.getObject("DENOMINATION") + " : " + resultSet.getString("ID_GROUPE"));
            }
            cbbAddProgrammeGroupe.setItems(nomGroupe);
            cbbAddEstMembreGroupe.setItems(nomGroupe);
            cbbEstCorrespondantGroupe.setItems(nomGroupe);
            cbbRepertoireGroupe.setItems(nomGroupe);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Init combo box with rencontre
    private void comboBoxRencontre(){
        try{
            // Remove all item in combo box
            cbbAddProgrammeRencontre.getItems().removeAll();
            cbbEstOrganisateurRencontre.getItems().removeAll();

            // Select rencontre
            Connection connection = Utils.databaseConnection();
            CallableStatement statement = connection.prepareCall("{CALL selectRencontre}");
            ResultSet resultSet = statement.executeQuery();
            ObservableList rencontre = FXCollections.observableArrayList();
            while (resultSet.next()){
                rencontre.add(resultSet.getString("NOM_RENCONTRE") + " | " + resultSet.getString("LIEU") + " | " +
                        resultSet.getString("DATE_DU_PROCHAIN_DEROULEMENT") + " : " + resultSet.getString("ID_RENCONTRE"));
            }
            cbbAddProgrammeRencontre.setItems(rencontre);
            cbbEstOrganisateurRencontre.setItems(rencontre);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Init combo box with personne
    private void comboBoxPersonne(){
        try {
            // Remove all item in combo box
            cbbAddEstMembrePersonne.getItems().removeAll();
            cbbEstOrganisateurPersonne.getItems().removeAll();
            cbbEstCorrespondantPersonne.getItems().removeAll();

            // Select Personne
            Connection connection = Utils.databaseConnection();
            CallableStatement statement = connection.prepareCall("{CALL selectPersonne}");
            ResultSet resultSet = statement.executeQuery();
            ObservableList personne = FXCollections.observableArrayList();
            while (resultSet.next()){
                personne.add(resultSet.getString("NOM") + " " + resultSet.getString("PRENOM") + " : " + resultSet.getString("ID_PERSONNE"));
            }
            cbbAddEstMembrePersonne.setItems(personne);
            cbbEstOrganisateurPersonne.setItems(personne);
            cbbEstCorrespondantPersonne.setItems(personne);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Init combo box with oeuvre
    private void comboBoxOeuvre(){
        try {
            // Remove all item in combo box
            cbbRepertoireOeuvre.getItems().removeAll();

            // Select Oeuvre
            Connection connection = Utils.databaseConnection();
            CallableStatement statement = connection.prepareCall("{CALL selectOeuvre}");
            ResultSet resultSet = statement.executeQuery();
            ObservableList oeuvre = FXCollections.observableArrayList();
            while (resultSet.next()){
                oeuvre.add(resultSet.getString("TITRE") + " | " + resultSet.getString("NOM_DE_L_AUTEUR") + " " +
                        resultSet.getString("PRENOM_DE_L_AUTEUR") + " : " + resultSet.getString("ID_OEUVRE"));
            }
            cbbRepertoireOeuvre.setItems(oeuvre);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Init combo box with pays
    private void comboBoxPays(){
        // Remove all item in combo box
        ccbAddRegionPays.getItems().removeAll();

        try {
            // Select Pays
            Connection connection = Utils.databaseConnection();
            CallableStatement statement = connection.prepareCall("{CALL selectPays}");
            ResultSet resultSet = statement.executeQuery();
            ObservableList pays = FXCollections.observableArrayList();
            while (resultSet.next()){
                pays.add(resultSet.getString("PAYS") + " : " + resultSet.getString("ID_PAYS"));
            }
            ccbAddRegionPays.setItems(pays);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Init combo box with region
    private void comboBoxRegion(){
        // Remove all item in combo box
        cbbAddGroupeRegion.getItems().removeAll();

        try {
            // Select Region
            Connection connection = Utils.databaseConnection();
            CallableStatement statement = connection.prepareCall("{CALL selectRegion}");
            ResultSet resultSet = statement.executeQuery();
            ObservableList region = FXCollections.observableArrayList();
            while (resultSet.next()){
                region.add(resultSet.getString("NOM_REGION") + " : " + resultSet.getString("ID_REGION"));
            }
            cbbAddGroupeRegion.setItems(region);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Init
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbbAddEstMembreSpecialite.setItems(FXCollections.observableArrayList("soliste", "choriste", "musicien", "chanteur"));
        comboBoxNomGroupe();
        comboBoxOeuvre();
        comboBoxPays();
        comboBoxPersonne();
        comboBoxRegion();
        comboBoxRencontre();
    }
}
