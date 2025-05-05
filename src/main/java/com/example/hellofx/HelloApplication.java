package com.example.hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Main application class that starts the JavaFX invoice automation application
 */
public class HelloApplication extends Application {

    /**
     * Initializes and starts the main application window
     *
     * @param stage The primary stage for the application
     * @throws IOException if the FXML or CSS files cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the main FXML layout
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);

        // Load and apply CSS styles
        String css = this.getClass().getResource("HelloApplication.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Configure and display the main window
        stage.setTitle("Automatizacion de facturas");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * Main entry point for the application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}