package com.example.hellofx;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewPacienteWindow {
    public TextField apellidoField;
    public TextField nombreField;
    public TextField telefono1Field;
    public TextField telefono2Field;
    public TextField correoField;

    public void initialize() {}

    public void handleAcceptButton(){
        String apellido = apellidoField.getText();
        String nombre = nombreField.getText();
        String telefono1 = telefono1Field.getText();
        String telefono2 = telefono2Field.getText();
        String correo = correoField.getText();
        try{
            SQLManager connection = new SQLManager();
            connection.insertPatient(apellido, nombre, telefono1, telefono2, correo);
            closeWindow();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("SQL Connection Error: \n" + e.getMessage());
            alert.showAndWait();
        }
        closeWindow();
    }

    public void closeWindow() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
        stage.close();
    }
    

}