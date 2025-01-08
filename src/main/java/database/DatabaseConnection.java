package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/bike_shop"; // Ganti dengan nama database Anda
    public static final String USER = "root"; // Ganti dengan username MySQL Anda
    public static final String PASSWORD = ""; // Ganti dengan password MySQL Anda

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Koneksi ke database berhasil!");
        } catch (SQLException e) {
            System.out.println("Gagal terhubung ke database.");
            e.printStackTrace();
        }
    }
}

