package com.example.airdejava.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionBDD implements Constant {
    private Connection databaseLink;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(DBURL, DBUSER, DBPW);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion");
        }

        return databaseLink;
    }
}