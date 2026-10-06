package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    // configuratia se citeste din variabilele de mediu, ca sa nu avem parole in cod
    private static final String URL = getEnv("DB_URL", "jdbc:postgresql://localhost:5432/postgres");
    private static final String USER = getEnv("DB_USER", "postgres");
    private static final String PASSWORD = getEnv("DB_PASSWORD", "");

    private DatabaseConnection() {
    }

    private static String getEnv(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("conexiunea la postgresql a fost realizata cu succes!");
            }
        } catch (SQLException e) {
            System.out.println("eroare la conectarea jdbc: " + e.getMessage());
        }
        return connection;
    }
}