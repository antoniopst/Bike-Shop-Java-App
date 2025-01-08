package edu.umb.uas_pbo;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MechanicsController {

    @FXML
    private TableView<Mechanic> mechanicsTableView;

    @FXML
    private TableColumn<Mechanic, Integer> mechanicsID;

    @FXML
    private TableColumn<Mechanic, String> mechanicsName;

    @FXML
    private TableColumn<Mechanic, String> mechanicsSpecialization;

    @FXML
    private TableColumn<Mechanic, String> mechanicsContact;

    @FXML
    private TextField textfieldID;

    @FXML
    private TextField textfieldName;

    @FXML
    private TextField textfieldSpecialization;

    @FXML
    private TextField textfieldContact;

    @FXML
    private Button AddButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button BackButton;

    private ObservableList<Mechanic> mechanicList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        mechanicsID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        mechanicsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mechanicsSpecialization.setCellValueFactory(cellData -> cellData.getValue().specializationProperty());
        mechanicsContact.setCellValueFactory(cellData -> cellData.getValue().contactProperty());

        // Load initial data
        loadData();
    }

    private void loadData() {
        mechanicList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM mechanics";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Mechanic mechanic = new Mechanic(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("specialization"),
                        resultSet.getString("contact")
                );
                mechanicList.add(mechanic);
            }
            mechanicsTableView.setItems(mechanicList);
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat memuat data: " + e.getMessage());
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        String name = textfieldName.getText();
        String specialization = textfieldSpecialization.getText();
        String contact = textfieldContact.getText();

        if (name.isEmpty() || specialization.isEmpty() || contact.isEmpty()) {
            showAlert("Input Error", "Semua field harus diisi!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO mechanics (name, specialization, contact) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, specialization);
            statement.setString(3, contact);
            statement.executeUpdate();
            showAlert("Success", "Mechanic berhasil ditambahkan!");
            loadData();
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat menambahkan mechanic: " + e.getMessage());
        }
    }

    @FXML
    private void Update(ActionEvent event) {
        String idText = textfieldID.getText();
        if (idText.isEmpty()) {
            showAlert("Input Error", "ID harus diisi untuk memperbarui data!");
            return;
        }

        int id = Integer.parseInt(idText);
        String name = textfieldName.getText();
        String specialization = textfieldSpecialization.getText();
        String contact = textfieldContact.getText();

        if (name.isEmpty() || specialization.isEmpty() || contact.isEmpty()) {
            showAlert("Input Error", "Semua field harus diisi!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE mechanics SET name = ?, specialization = ?, contact = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, specialization);
            statement.setString(3, contact);
            statement.setInt(4, id);
            statement.executeUpdate();
            showAlert("Success", "Mechanic berhasil diperbarui!");
            loadData();
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat memperbarui mechanic: " + e.getMessage());
        }
    }

    @FXML
    private void Delete(ActionEvent event) {
        String idText = textfieldID.getText();
        if (idText.isEmpty()) {
            showAlert("Input Error", "ID harus diisi untuk menghapus data!");
            return;
        }

        int id = Integer.parseInt(idText);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM mechanics WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            showAlert("Success", "Mechanic berhasil dihapus!");
            loadData();
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat menghapus mechanic: " + e.getMessage());
        }
    }

    @FXML
    private void Back(ActionEvent event) {
        try {
            Parent dashboardParent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene dashboardScene = new Scene(dashboardParent);

            Stage window = (Stage) mechanicsTableView.getScene().getWindow();
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
