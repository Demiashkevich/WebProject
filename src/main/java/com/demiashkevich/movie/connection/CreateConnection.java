package com.demiashkevich.movie.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {

    private static final Logger LOGGER = Logger.getLogger(CreateConnection.class);

    private static final String URL = "jdbc:mysql://localhost:3305/cinema_rating";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private ProxyConnection proxyConnection;

    public CreateConnection(){
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            proxyConnection = new ProxyConnection(connection);
        } catch (SQLException exception) {
            LOGGER.error(exception);
        }
    }

    public ProxyConnection getConnection() {
        return proxyConnection;
    }
}
