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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
    @FXML
    private TextField txtNomPersonne, txtPrenomPersonne, txtCivilitePersonne, txtAdressePersonne, txtTelephonePersonne, txtFaxPersonne, txtEmailPersonne, txtNomGroupe,
            txtNomRencontre, txtLieuRencontre, txtPeriodiciteRencontre, txtNbPersonneRencontre, txtTitreOeuvre, txtNomAuteurOeuvre, txtPrenomAuteurOeuvre,
            txtDureeOeuvre, txtTypeOeuvre, dbAddProgrammeHHDebut, dbAddProgrammeHHFin, dbAddProgrammeMMDebut, dbAddProgrammeMMFin;

    @FXML
    private DatePicker dbDateRencontre, dbApparitionOeuvre, dbAddProgrammeDate;

    @FXML
    private ComboBox<String> cbbAddEstMembrePersonne, cbbAddEstMembreGroupe, cbbAddEstMembreSpecialite, cbbRepertoireOeuvre, cbbRepertoireGroupe, cbbParticipeGroupe,
            cbbParticipeRencontre, cbbAddProgrammeGroupe, cbbAddProgrammeRencontre, cbbEstOrganisateurPersonne, cbbEstOrganisateurRencontre,
            cbbEstCorrespondantPersonne, cbbEstCorrespondantGroupe, ccbAddRegionPays, cbbAddGroupeRegion;

    // Add new personne in database
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

    // Add groupe in database
    @FXML
    void clickBtnAddGroupe(ActionEvent event) {
        Connection connection = Utils.databaseConnection();
        if(!txtNomGroupe.getText().equals("")){
            try {
                CallableStatement statement = connection.prepareCall("{CALL insertGroupe(?, ?)}");
                statement.setString(1, txtNomGroupe.getText());
                statement.setInt(2, Integer.parseInt(cbbAddGroupeRegion.getValue().substring(cbbAddGroupeRegion.getValue().lastIndexOf(" : ")+2)));
                statement.execute();
                connection.close();
                txtNomGroupe.clear();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add rencontre in database
    @FXML
    void clickBtnAddRencontre(ActionEvent event) {
        Connection connection = Utils.databaseConnection();
        if(!txtNomRencontre.getText().equals("") && !txtLieuRencontre.getText().equals("") && dbDateRencontre.getValue() == null &&
                !txtPeriodiciteRencontre.getText().equals("") && !txtNbPersonneRencontre.getText().equals("")){
            try{
                CallableStatement statement = connection.prepareCall("{CALL insertRencontre(?, ?, ?, ?, ?)}");
                statement.setString(1, txtNomRencontre.getText());
                statement.setString(2, txtLieuRencontre.getText());
                statement.setDate(3, Date.valueOf(dbDateRencontre.getValue()));
                statement.setString(4, txtPeriodiciteRencontre.getText());
                statement.setString(5, txtNbPersonneRencontre.getText());
                statement.execute();
                connection.close();
                txtNomRencontre.clear(); txtLieuRencontre.clear(); dbDateRencontre.setValue(null); txtPeriodiciteRencontre.clear(); txtNbPersonneRencontre.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add oeuvre in database
    @FXML
    void btnClickAddOeuvre(ActionEvent event) {
        Connection connection = Utils.databaseConnection();
        if(!txtTitreOeuvre.getText().equals("")  && !txtNomAuteurOeuvre.getText().equals("") && !txtDureeOeuvre.getText().equals("")){
            try{
                CallableStatement statement = connection.prepareCall("{CALL insertOeuvre(?, ?, ?, ?, ?, ?)}");
                statement.setString(1, txtTitreOeuvre.getText());
                statement.setDate(2, Date.valueOf(dbApparitionOeuvre.getValue()));
                statement.setString(3, txtNomAuteurOeuvre.getText());
                statement.setString(4, txtPrenomAuteurOeuvre.getText());
                statement.setString(5, txtDureeOeuvre.getText());
                statement.setString(6, txtTypeOeuvre.getText());
                statement.execute();
                connection.close();
                txtTitreOeuvre.clear(); dbApparitionOeuvre.setValue(null); txtNomAuteurOeuvre.clear(); txtPrenomAuteurOeuvre.clear(); txtDureeOeuvre.clear(); txtTypeOeuvre.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Add programme in database
    @FXML
    void btnClickAddProgramme(ActionEvent event) {

    }

    // Init comboBox
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = Utils.databaseConnection();
        cbbAddEstMembreSpecialite.setItems(FXCollections.observableArrayList("soliste", "choriste", "musicien", "chanteur"));
        try{
            // Select groupe name
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
            cbbParticipeGroupe.setItems(nomGroupe);

            // Select rencontre
            statement = connection.prepareCall("{CALL selectRencontre}");
            resultSet = statement.executeQuery();
            ObservableList rencontre = FXCollections.observableArrayList();
            while (resultSet.next()){
                rencontre.add(resultSet.getString("NOM_RENCONTRE") + " | " + resultSet.getString("LIEU") + " | " +
                        resultSet.getString("DATE_DU_PROCHAIN_DEROULEMENT") + " | " + resultSet.getString("PERIODICITE_DE_LA_RENCONTRE") + " | " +
                        resultSet.getString("NOMBRE_DE_PERSONNES_ATTENDUES") + " : " + resultSet.getString("ID_RENCONTRE"));
            }
            cbbAddProgrammeRencontre.setItems(rencontre);
            cbbEstOrganisateurRencontre.setItems(rencontre);
            cbbParticipeRencontre.setItems(rencontre);

            // Select Personne
            statement = connection.prepareCall("{CALL selectPersonne}");
            resultSet = statement.executeQuery();
            ObservableList personne = FXCollections.observableArrayList();
            while (resultSet.next()){
                personne.add(resultSet.getString("NOM") + " " + resultSet.getString("PRENOM") + " : " + resultSet.getString("ID_PERSONNE"));
            }
            cbbAddEstMembrePersonne.setItems(personne);
            cbbEstOrganisateurPersonne.setItems(personne);
            cbbEstCorrespondantPersonne.setItems(personne);

            // Select Oeuvre
            statement = connection.prepareCall("{CALL selectOeuvre}");
            resultSet = statement.executeQuery();
            ObservableList oeuvre = FXCollections.observableArrayList();
            while (resultSet.next()){
                oeuvre.add(resultSet.getString("TITRE") + " " + resultSet.getString("NOM_DE_L_AUTEUR") + " " +
                        resultSet.getString("PRENOM_DE_L_AUTEUR") + " : " + resultSet.getString("ID_OEUVRE"));
            }
            cbbRepertoireOeuvre.setItems(oeuvre);

            // Select Pays
            statement = connection.prepareCall("{CALL selectPays}");
            resultSet = statement.executeQuery();
            ObservableList pays = FXCollections.observableArrayList();
            while (resultSet.next()){
                pays.add(resultSet.getString("PAYS") + " : " + resultSet.getString("ID_PAYS"));
            }
            ccbAddRegionPays.setItems(pays);

            // Select Region
            statement = connection.prepareCall("{CALL selectRegion}");
            resultSet = statement.executeQuery();
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
}
