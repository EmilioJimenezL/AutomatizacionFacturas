package com.example.hellofx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class InsertDataSolicitud {
    @FXML
    public TextField montoTextField;
    @FXML
    public TextField conceptoTextField;
    @FXML
    private ChoiceBox<String> choiceBoxCFDI;
    @FXML
    private ChoiceBox<String> choiceBoxForma;
    private Integer ID_Paciente;
    private Integer ID_Profile;

    public void initialize() {
        ObservableList<String> itemsForma = FXCollections.observableArrayList("Debito", "Credito", "Transferencia", "Efectivo");
        choiceBoxForma.setItems(itemsForma);

        try {
            SQLManager connection = new SQLManager();
            ObservableList<String> CFDIlist = connection.getCFDI();
            choiceBoxCFDI.setItems(CFDIlist);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Connection Error");
            alert.setHeaderText(null);
            alert.setContentText("SQL Connection Error: \n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public void setIds(Integer ID_Paciente, Integer ID_Profile) {
        this.ID_Paciente = ID_Paciente;
        this.ID_Profile = ID_Profile;
        initialize();
    }

    @FXML
    public void handleAcceptButton() {
        try{
            SQLManager connection = new SQLManager();
            connection.insertSolicitud(ID_Paciente,ID_Profile,choiceBoxCFDI.getValue(), montoTextField.getText(), conceptoTextField.getText(), choiceBoxForma.getValue());
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
        Stage stage = (Stage) choiceBoxCFDI.getScene().getWindow();
        stage.close();
    }

}
