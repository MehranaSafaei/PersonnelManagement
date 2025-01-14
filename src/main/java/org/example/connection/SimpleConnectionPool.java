package org.example.connection;

import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleConnectionPool {

    private final String URL = "jdbc:postgresql://localhost:5432/postgres"; // URL postgres DB
//            private final String URL = "jdbc:mysql://localhost:3306/mydb"; //URL MySQL DB
    private final String USER = "postgres"; // user postgres DB
//            private final String USER = "root"; // user MySQL DB
    private final String PASSWORD = "1234"; // password postgres DB
//    private final String PASSWORD = "Aa@123456"; // password MySQL DB

    //    private static final String PROPERTIES_FILE = "database.properties";
    private static final List<Connection> availableConnections = new ArrayList<>();
    private static final List<Connection> usedConnections = new ArrayList<>();

    public SimpleConnectionPool() throws SQLException {
        int INITIAL_POOL_SIZE = 20;
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            availableConnections.add(createConnection());
        }
    }

    // create a new Connection
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // get Connection from pool
    public static Connection getConnection() {
        if (availableConnections.isEmpty()) {
            System.out.println("No available connections! Returning null.");
            return null;
        }

        Connection connection = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    // release Connection to pool
    public void releaseConnection(Connection connection) {
        if (connection != null && usedConnections.remove(connection)) {
            availableConnections.add(connection);
        }
    }

    // Close all Connections
    public void closeAllConnections(Connection disposedConnection) {
        // Close connections in availableConnections
        for (Connection conn : availableConnections) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Close connections in usedConnections
        for (Connection conn : usedConnections) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Clear the lists
        availableConnections.clear();
        usedConnections.clear();

        // Close the specific disposed connection
        try {
            if (disposedConnection != null && !disposedConnection.isClosed()) {
                disposedConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
