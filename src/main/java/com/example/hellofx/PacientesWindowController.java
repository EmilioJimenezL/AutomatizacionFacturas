package com.example.hellofx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PacientesWindowController {
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, Integer> idColumn;
    @FXML
    private TableColumn<Patient, String> apellidoColumn;
    @FXML
    private TableColumn<Patient, String> nombreColumn;
    @FXML
    private TableColumn<Patient, String> telefono1Column;
    @FXML
    private TableColumn<Patient, String> telefono2Column;
    @FXML
    private TableColumn<Patient, String> correoColumn;
    @FXML
    private Button aceptarButton;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_Paciente"));
        apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        telefono1Column.setCellValueFactory(new PropertyValueFactory<>("numContacto1"));
        telefono2Column.setCellValueFactory(new PropertyValueFactory<>("numContacto2"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("correo"));
        try {
            SQLManager connection = new SQLManager();
            ObservableList<Patient> list = connection.getPatients();
            patientsTable.setItems(list);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("SQL Connection Error: \n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void closeWindow(){
        Stage stage = (Stage) patientsTable.getScene().getWindow();
        stage.close();
    }

    public void handleNewButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newPacienteWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        NewPacienteWindow controller = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        closeWindow();
    }

    @FXML
    private void handleAcceptButton() throws IOException {
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("elegirPerfilFiscal.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            seleccionarPerfilFiscalController controller = fxmlLoader.getController();
            controller.setPatientId(selectedPatient.getId_Paciente());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            closeWindow();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a patient from the table.");
            alert.showAndWait();
        }
    }
}
