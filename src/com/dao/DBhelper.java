package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBhelper {
    public static Connection GetConnection(){
       String path = "jdbc:mysql://localhost:3306/library?user=root&password=1234";
       Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            c = DriverManager.getConnection(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
}
