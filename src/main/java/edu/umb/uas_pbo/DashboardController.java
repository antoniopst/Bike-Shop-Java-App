package edu.umb.uas_pbo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button suppliersButton;

    @FXML
    private Button mechanicsButton;

    @FXML
    private Button printreportsButton;

    @FXML
    private Button servicesButton;

    @FXML
    private Button ordersButton;

    /**
     * Navigate to the login page.
     */
    @FXML
    private void loginPage() {
        showAlert(Alert.AlertType.INFORMATION, "Log Out", "Anda berhasil logout.");
        // TODO: Tambahkan logika untuk navigasi ke halaman login
        // App.setRoot("login");
    }

    /**
     * Navigate to the clients page.
     */
    @FXML
    private void clientsPage() {
        loadPage("clients.fxml");
    }

    /**
     * Navigate to the suppliers page.
     */
    @FXML
    private void suppliersPage() {
        loadPage("suppliers.fxml");
    }

    /**
     * Navigate to the mechanics page.
     */
    @FXML
    private void mechanicsPage() {
        loadPage("mechanics.fxml");
    }

    /**
     * Navigate to the reports page.
     */
    @FXML
    private void reportsPage() {
        loadPage("reports.fxml");
    }

    /**
     * Navigate to the services page.
     */
    @FXML
    private void servicesPage() {
        loadPage("services.fxml");
    }

    /**
     * Navigate to the orders page.
     */
    @FXML
    private void ordersPage() {
        loadPage("orders.fxml");
    }

    /**
     * Utility method to load a new page.
     *
     * @param fxmlFile the name of the FXML file to load
     */
    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) clientsButton.getScene().getWindow(); // Mengambil stage dari Button
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Terjadi kesalahan saat memuat halaman: " + e.getMessage());
        }
    }

    /**
     * Utility method to show alerts.
     *
     * @param alertType the type of alert
     * @param title     the title of the alert
     * @param content   the content of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
