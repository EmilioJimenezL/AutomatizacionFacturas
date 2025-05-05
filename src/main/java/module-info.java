module com.example.hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.hellofx to javafx.fxml;
    exports com.example.hellofx;
}