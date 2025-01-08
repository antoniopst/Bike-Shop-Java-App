module edu.umb.uas_pbo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens edu.umb.uas_pbo to javafx.fxml;
    exports edu.umb.uas_pbo;
}