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

public class SuppliersController {

    @FXML
    private TableView<Supplier> supplierTableView;
    @FXML
    private TableColumn<Supplier, Integer> suppliersID;
    @FXML
    private TableColumn<Supplier, String> suppliersName;
    @FXML
    private TableColumn<Supplier, String> suppliersContact;
    @FXML
    private TableColumn<Supplier, String> suppliersAddress;
    @FXML
    private TextField textfieldID;
    @FXML
    private TextField textfieldName;
    @FXML
    private TextField textfieldContact;
    @FXML
    private TextField textfieldAddress;

    private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        suppliersID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        suppliersName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        suppliersContact.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
        suppliersAddress.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

        loadData();
    }

    private void loadData() {
        supplierList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM suppliers";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact"),
                        resultSet.getString("address")
                );
                supplierList.add(supplier);
            }
            supplierTableView.setItems(supplierList);
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
            String sql = "INSERT INTO suppliers (name, contact, address) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, contact);
            statement.setString(3, address);
            statement.executeUpdate();
            showAlert("Success", "Supplier berhasil ditambahkan!");
            loadData();
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat menambahkan supplier: " + e.getMessage());
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
            String sql = "UPDATE suppliers SET name = ?, contact = ?, address = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, contact);
            statement.setString(3, address);
            statement.setInt(4, id);
            statement.executeUpdate();
            showAlert("Success", "Supplier berhasil diperbarui!");
            loadData();
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat memperbarui supplier: " + e.getMessage());
        }
    }

    @FXML
    private void Delete(ActionEvent event) {
        int id = Integer.parseInt(textfieldID.getText());

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM suppliers WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            showAlert("Success", "Supplier berhasil dihapus!");
            loadData();
        } catch (SQLException e) {
            showAlert("Error", "Terjadi kesalahan saat menghapus supplier: " + e.getMessage());
        }
    }

    @FXML
    private void Back(ActionEvent event) {
        try {
            Parent dashboardParent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene dashboardScene = new Scene(dashboardParent);

            Stage window = (Stage) supplierTableView.getScene().getWindow();
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