package com.example.airdejava.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
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
    public static void callProcedure(AnchorPane ap, Connection connection, int index, ObservableList data,
                                     TextField txtTitre, TextField txtGroupe, TextField txtRencontre, String spec, int indexSigne,
                                     TextField txtDuree, TextField txtRegionPays, TextField txtNbGroupe, TextField txtInstrument, TextField txtLieuRencontre, TableView tvResult){
        try{
            // Clear observable list
            data.clear();
            // Delete all column in tableview
            tvResult.getColumns().clear();
            // Init resultSet
            ResultSet result = null;

            // Call stored procedure relative to interrogation index
            if(index == 0){
                CallableStatement statement = connection.prepareCall("{CALL fromTitle(?)}");
                statement.setString(1, txtTitre.getText());
                result = statement.executeQuery();
            }else if(index == 1){
                CallableStatement statement = connection.prepareCall("{CALL rencontreFromTitleAndGroup(?, ?)}");
                statement.setString(1, txtTitre.getText());
                statement.setString(2, txtGroupe.getText());
                result = statement.executeQuery();
            }else if(index == 2){
                CallableStatement statement = connection.prepareCall("{CALL membreFromSpecAndRencontre(?, ?)}");
                statement.setString(1, spec);
                statement.setString(2, txtRencontre.getText());
                result = statement.executeQuery();
            }else if(index == 3){
                CallableStatement statement = connection.prepareCall("{CALL titreFromDureeAndRegion(?, ?, ?)}");
                statement.setInt(1, indexSigne);
                statement.setInt(2, Integer.parseInt(txtDuree.getText()));
                statement.setString(3, txtRegionPays.getText());
                result = statement.executeQuery();
            }else if(index == 4){
                CallableStatement statement = connection.prepareCall("{CALL rencontreFromNbGroupe(?)}");
                statement.setInt(1, Integer.parseInt(txtNbGroupe.getText()));
                result = statement.executeQuery();
            }else if(index == 5){
                CallableStatement statement = connection.prepareCall("{CALL rencontreFromInstrument(?)}");
                statement.setString(1, txtInstrument.getText());
                result = statement.executeQuery();
            }else if(index == 6){
                //data.add("Planning complet de la rencontre par lieu et groupe : ");
                CallableStatement statement = connection.prepareCall("{CALL planningRencontreFromGroupeAndLieu(?, ?)}");
                statement.setString(1, txtGroupe.getText());
                statement.setString(2, txtLieuRencontre.getText());
                result = statement.executeQuery();
            }

            while (result.next()){
                // Add value in observableList
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=result.getMetaData().getColumnCount(); i++){
                    row.add(result.getString(i));
                }
                data.add(row);
            }

            // If querry get result : init tableview with column
            if (!data.isEmpty()){
                int width = 0;
                // Add column in tableview
                for(int i = 1; i <= result.getMetaData().getColumnCount(); i++){
                    final int j = i;
                    TableColumn col = new TableColumn(result.getMetaData().getColumnLabel(i));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j-1).toString());
                        }
                    });
                    col.setStyle("-fx-alignment: CENTER;");
                    tvResult.getColumns().addAll(col);
                    // get each column width
                    width += col.getPrefWidth() + (col.getText().length()*6);
                }
                // Increase tableview and scrollpane width
                tvResult.setPrefWidth(width);
                ap.setPrefWidth(width);
            }

            // Add observable list in tableview
            tvResult.setItems(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
