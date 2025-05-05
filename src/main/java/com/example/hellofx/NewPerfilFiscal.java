package com.example.hellofx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class NewPerfilFiscal {
    public TextField razonSocialField;
    public TextField rfcField;
    public TextField cpField;
    private Integer ID_Paciente;
    public ChoiceBox<String> choiceBoxRegimen;

    public void initialize() {
        try{
            SQLManager connection = new SQLManager();
            ObservableList<String> RegimenesList = connection.getRegimenes();
            choiceBoxRegimen.setItems(RegimenesList);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("SQL Connection Error: \n" + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void handleAcceptButton(){
        try{
            SQLManager connection = new SQLManager();
            connection.insertProfile(ID_Paciente, razonSocialField.getText(), rfcField.getText(), cpField.getText(), choiceBoxRegimen.getValue());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("SQL Connection Error: \n" + e.getMessage());
            alert.showAndWait();
        }
        closeWindow();
    }

    @FXML
    public void closeWindow() {
        razonSocialField.getScene().getWindow().hide();
    }

    public void setPatientId(Integer ID){
        this.ID_Paciente = ID;
        initialize();
    }
}
