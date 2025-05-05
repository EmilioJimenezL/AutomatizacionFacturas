package com.example.hellofx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class seleccionarPerfilFiscalController {
    private int ID_Paciente;
    @FXML
    private Button cancelButton;
    @FXML
    private Button acceptButton;
    @FXML
    private TableView<PerfilFiscal> tableView;
    @FXML
    private TableColumn<PerfilFiscal, Integer> idColumn;
    @FXML
    private TableColumn<PerfilFiscal, String> pacienteColumn;
    @FXML
    private TableColumn<PerfilFiscal, String> razonSocialColumn;
    @FXML
    private TableColumn<PerfilFiscal, String> rfcColumn;
    @FXML
    private TableColumn<PerfilFiscal, String> cpColumn;
    @FXML
    private TableColumn<PerfilFiscal, String> regimenColumn;
    
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("IDPerfilFiscal"));
        pacienteColumn.setCellValueFactory(new PropertyValueFactory<>("Paciente"));
        razonSocialColumn.setCellValueFactory(new PropertyValueFactory<>("RazonSocial"));
        rfcColumn.setCellValueFactory(new PropertyValueFactory<>("RFC"));
        cpColumn.setCellValueFactory(new PropertyValueFactory<>("CP"));
        regimenColumn.setCellValueFactory(new PropertyValueFactory<>("Regimen"));

        try {
            SQLManager connection = new SQLManager();
            ObservableList<PerfilFiscal> list = connection.getProfilesByPatientID (ID_Paciente);
            tableView.setItems(list);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("SQL Connection Error: \n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void setPatientId(Integer ID){
        this.ID_Paciente = ID;
        initialize();
    }

    public void handleNewButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newPerfilFiscal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        NewPerfilFiscal controller = fxmlLoader.getController();
        controller.setPatientId(ID_Paciente);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        closeWindow();
    }

    public void handleAcceptButton() throws IOException {
        PerfilFiscal selectedProfile = tableView.getSelectionModel().getSelectedItem();
        if (selectedProfile != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("insertDataSolicitud.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            InsertDataSolicitud controller = fxmlLoader.getController();
            controller.setIds(ID_Paciente, selectedProfile.getIDPerfilFiscal());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            closeWindow();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a profile first");
            alert.showAndWait();
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
