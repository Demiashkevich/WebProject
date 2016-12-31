package com.demiashkevich.movie.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {

    private static final String URL = "jdbc:mysql://localhost:3305/cinema_rating";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private ProxyConnection proxyConnection;

    public CreateConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            proxyConnection = new ProxyConnection(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ProxyConnection getConnection() {
        return proxyConnection;
    }
}
