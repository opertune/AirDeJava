package com.example.airdejava.utils;

import com.example.airdejava.entities.User;

public interface Constant {
    String DBNAME = "airdejava";
    String DBUSER = "root";
    String DBPW = "";
    String DBURL = "jdbc:mysql://localhost:3306/" + DBNAME;

    // User with the lowest privileges
    User USER = new User("guest", 3);
}
