package com.example.airdejava.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Utils {
    // Create database connection
    public static Connection databaseConnection(){
        ConnectionBDD databaseConnection = new ConnectionBDD();
        return databaseConnection.getConnection();
    }

    // call stored procedure
    public static void callProcedure(int index, ObservableList data, Connection connection, ListView lvResult,
                                     TextField txtTitre, TextField txtGroupe, TextField txtRencontre, String spec, int indexSigne,
                                     TextField txtDuree, TextField txtRegionPays, TextField txtNbGroupe, TextField txtInstrument){
        try{
            // Clear observable list
            data.clear();
            // Call stored procedure relative to interrogation index
            if(index == 0){
                CallableStatement statement = connection.prepareCall("{CALL fromTitle(?)}");
                statement.setString(1, txtTitre.getText());
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    // Add value in obersableList
                    data.add(result.getString("DENOMINATION"));
                }
            }else if(index == 1){
                CallableStatement statement = connection.prepareCall("{CALL rencontreFromTitleAndGroup(?, ?)}");
                statement.setString(1, txtTitre.getText());
                statement.setString(2, txtGroupe.getText());
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    // Add value in obersableList
                    data.add(result.getString("NOM_RENCONTRE"));
                }
            }else if(index == 2){
                CallableStatement statement = connection.prepareCall("{CALL membreFromSpecAndRencontre(?, ?)}");
                statement.setString(1, spec);
                statement.setString(2, txtRencontre.getText());
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    // Add value in obersableList
                    data.add(result.getString("NOM") + " " + result.getString("PRENOM"));
                }
            }else if(index == 3){
                CallableStatement statement = connection.prepareCall("{CALL titreFromDureeAndRegion(?, ?, ?)}");
                statement.setInt(1, indexSigne);
                statement.setInt(2, Integer.parseInt(txtDuree.getText()));
                statement.setString(3, txtRegionPays.getText());
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    // Add value in obersableList
                    data.add(result.getString("TITRE"));
                }
            }else if(index == 4){
                CallableStatement statement = connection.prepareCall("{CALL rencontreFromNbGroupe(?)}");
                statement.setInt(1, Integer.parseInt(txtNbGroupe.getText()));
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    // Add value in obersableList
                    data.add(result.getString("LIEU_DE_LA_PRESENTATION"));
                }
            }else if(index == 5){
                CallableStatement statement = connection.prepareCall("{CALL rencontreFromInstrument(?)}");
                statement.setString(1, txtInstrument.getText());
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    // Add value in obersableList
                    data.add(result.getString("NOM_RENCONTRE"));
                }
            }

            // Add observable list in listView
            lvResult.setItems(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}