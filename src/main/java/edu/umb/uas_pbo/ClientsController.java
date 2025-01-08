package edu.umb.uas_pbo;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientsController {

    @FXML
    private TableView<client> clientsTableView; // Pastikan ini sesuai dengan fx:id di FXML
    @FXML
    private TableColumn<client, Integer> clientsID; // Ganti tipe sesuai dengan ID
    @FXML
    private TableColumn<client, String> clientsName; // Ganti tipe sesuai dengan Name
    @FXML
    private TableColumn<client, String> clientsContact; // Ganti tipe sesuai dengan Contact
    @FXML
    private TableColumn<client, String> clientsAddress; // Ganti tipe sesuai dengan Address
    @FXML
    private TextField textfieldID; // ID field untuk update
    @FXML
    private TextField textfieldName;
    @FXML
    private TextField textfieldContact;
    @FXML
    private TextField textfieldAddress;

    private ObservableList<client> clientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Mengatur kolom untuk TableView
        clientsID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        clientsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        clientsContact.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
        clientsAddress.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

        // Memuat data awal dari database
        loadData();
    }

    private void loadData() {
        clientList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM clients"; // Ganti dengan tabel Anda
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                client client = new client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact"),
                        resultSet.getString("address")
                );
                clientList.add(client);
            }
            clientsTableView.setItems(clientList);
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat memuat data: " + e.getMessage());
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        String name = textfieldName.getText();
        String contact = textfieldContact.getText();
        String address = textfieldAddress.getText();

        if (name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
            showAlert("Input Error", "Semua field harus diisi!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO clients (name, contact, address) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, contact);
            statement.setString(3, address);
            statement.executeUpdate();
            showAlert("Success", "Client berhasil ditambahkan!");
            loadData(); // Refresh data
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat menambahkan client: " + e.getMessage());
        }
    }

    @FXML
    private void Update(ActionEvent event) {
        int id = Integer.parseInt(textfieldID.getText());
        String name = textfieldName.getText();
        String contact = textfieldContact.getText();
        String address = textfieldAddress.getText();

        if (name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
            showAlert("Input Error", "Semua field harus diisi!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE clients SET name = ?, contact = ?, address = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, contact);
            statement.setString(3, address);
            statement.setInt(4, id);
            statement.executeUpdate();
            showAlert("Success", "Client berhasil diperbarui!");
            loadData(); // Refresh data
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat memperbarui client: " + e.getMessage());
        }
    }

    @FXML
    private void Delete(ActionEvent event) {
        int id = Integer.parseInt(textfieldID.getText());

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM clients WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            showAlert("Success", "Client berhasil dihapus!");
            loadData(); // Refresh data
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat menghapus client: " + e.getMessage());
        }
    }

    @FXML
    private void Back(ActionEvent event) {
        try {
            // Muat file FXML untuk halaman dashboard
            Parent dashboardParent = FXMLLoader.load(getClass().getResource("Dashboard.fxml")); // Ganti dengan nama file FXML dashboard Anda
            Scene dashboardScene = new Scene(dashboardParent);

            // Mendapatkan Stage saat ini dan mengubahnya menjadi scene dashboard
            Stage window = (Stage) clientsTableView.getScene().getWindow(); // Ambil stage saat ini
            window.setScene(dashboardScene);
            window.show();
        } catch (Exception e) {
            showAlert("Error", "Terjadi kesalahan saat kembali ke dashboard: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
