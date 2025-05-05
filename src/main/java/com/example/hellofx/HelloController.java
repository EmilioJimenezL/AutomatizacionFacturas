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

public class HelloController {

    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private TableView<ListViewObject> tableView;
    @FXML private TableColumn<ListViewObject, Integer> idColumn;
    @FXML private TableColumn<ListViewObject, String> pacienteColumn;
    @FXML private TableColumn<ListViewObject, String> razonSocialColumn;
    @FXML private TableColumn<ListViewObject, String> rfcColumn;
    @FXML private TableColumn<ListViewObject, String> regimenColumn;
    @FXML private TableColumn<ListViewObject, String> cfdiColumn;
    @FXML private TableColumn<ListViewObject, String> correoColumn;
    @FXML private TableColumn<ListViewObject, String> montoColumn;
    @FXML private TableColumn<ListViewObject, String> formaPagoColumn;
    @FXML private TableColumn<ListViewObject, String> conceptoColumn;
    @FXML private TableColumn<ListViewObject, String> telefonoColumn;
    @FXML private TableColumn<ListViewObject, String> estadoColumn;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        pacienteColumn.setCellValueFactory(new PropertyValueFactory<>("Paciente"));
        razonSocialColumn.setCellValueFactory(new PropertyValueFactory<>("RazonSocial"));
        rfcColumn.setCellValueFactory(new PropertyValueFactory<>("RFC"));
        regimenColumn.setCellValueFactory(new PropertyValueFactory<>("Regimen"));
        cfdiColumn.setCellValueFactory(new PropertyValueFactory<>("CFDI"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("Correo"));
        montoColumn.setCellValueFactory(new PropertyValueFactory<>("Monto"));
        formaPagoColumn.setCellValueFactory(new PropertyValueFactory<>("FormaPago"));
        conceptoColumn.setCellValueFactory(new PropertyValueFactory<>("Concepto"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("Estado"));

        try {
            SQLManager connection = new SQLManager();
            ObservableList<ListViewObject> list = connection.getSolicitudes();
            tableView.setItems(list);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("SQL Connection Error: \n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void reloadTable() {
        try {
            SQLManager connection = new SQLManager();
            ObservableList<ListViewObject> list = connection.getSolicitudes();
            tableView.setItems(list);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("SQL Connection Error: \n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void handleDeleteButton(){
        ListViewObject selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                SQLManager connection = new SQLManager();
                connection.deleteSolicitud(selectedItem.getID());
                reloadTable();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Connection Error");
                alert.setHeaderText(null);
                alert.setContentText("SQL Connection Error: \n" + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row to delete.");
            alert.showAndWait();
        }
    }

    public void openNuevaSolicitudWindow () throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pacientesWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void closeApplication() {}
}