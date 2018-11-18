package com.base.connection;

/**
 * Created by RISITH-PC on 10/21/2018.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseConnection {
    private static BaseConnection baseConnection;
    private final Connection conn;

    private BaseConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/pharmacy","root","apple");
    }

    public static BaseConnection createConnection() throws ClassNotFoundException, SQLException{
        if(baseConnection == null){
            baseConnection = new BaseConnection();
        }
        return baseConnection;
    }

    public Connection getConnection(){
        return conn;
    }
}
