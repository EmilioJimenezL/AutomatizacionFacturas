package com.example.hellofx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Objects;

public class SQLManager {
    private static final String URL = "jdbc:mysql://localhost:3306/Consultorio";
    private static final String USER = "root";
    private static final String PASSWORD = "33Emi.44";
    private final Connection connection;

    public SQLManager() throws SQLException{
        try {
            // Register a JDBC driver (optional for newer versions of MySQL Connector/J)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the connection
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    public void checkServer() throws SQLException {
        try (Statement stmt = connection.createStatement();
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/hellofx/Setup.sql"))))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                if (line.trim().endsWith(";")) {
                    stmt.execute(sb.toString());
                    sb.setLength(0);
                }
            }
        } catch (SQLException | IOException e) {
            throw new SQLException("Error checking server: " + e.getMessage());
        }
    }

    public ObservableList<MailData> getMailData() throws SQLException {
        String query = "SELECT * FROM SolicitudCorreo";
        ObservableList<MailData> mailDataList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mailDataList.add(new MailData(
                        resultSet.getString("Razon"),
                        resultSet.getString("RFC"),
                        resultSet.getString("CP"),
                        resultSet.getString("CFDI"),
                        resultSet.getString("Regimen"),
                        resultSet.getString("Monto"),
                        resultSet.getString("Concepto")
                ));
            }
            return mailDataList;
        } catch (SQLException e) {
            throw new SQLException("Error getting mail data: " + e.getMessage());
        }
    }

    public ObservableList<ListViewObject> getSolicitudes() throws SQLException {
        String query = "SELECT * FROM VistaGUI";
        ObservableList<ListViewObject> listViewObjects = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listViewObjects.add(new ListViewObject(
                        resultSet.getInt("ID_Solicitud"),
                        resultSet.getString("Nombre").concat(" ").concat(resultSet.getString("Apellido")),
                        resultSet.getString("RazonSocial"),
                        resultSet.getString("RFC"),
                        resultSet.getString("Regimen"),
                        resultSet.getString("CFDI"),
                        resultSet.getString("Correo"),
                        resultSet.getString("Monto"),
                        resultSet.getString("FormaDePago"),
                        resultSet.getString("Concepto"),
                        resultSet.getString("NumContacto"),
                        resultSet.getString("Estado")
                ));
            }
            return listViewObjects;
        } catch (SQLException e) {
            throw new SQLException("Error getting mail data: " + e.getMessage());
        }
    }

    public ObservableList<PerfilFiscal> getProfiles() throws SQLException {
        String query = "SELECT * FROM PerfilesFiscalesView";
        ObservableList<PerfilFiscal> perfilesFiscales = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                perfilesFiscales.add(new PerfilFiscal(
                        resultSet.getInt("ID_PerfilFiscal"),
                        resultSet.getString("Paciente"),
                        resultSet.getString("RazonSocial"),
                        resultSet.getString("RFC"),
                        resultSet.getString("CP"),
                        resultSet.getString("Regimen")
                ));
            }
            return perfilesFiscales;
        } catch (SQLException e) {
            throw new SQLException("Error getting mail data: " + e.getMessage());
        }
    }

    public ObservableList<PerfilFiscal> getProfilesByPatientID(int patientID) throws SQLException {
        String query = "{CALL GetPerfilesFiscalesPorPaciente(?)}";
        ObservableList<PerfilFiscal> perfilesFiscales = FXCollections.observableArrayList();
        try {
            CallableStatement stmt = connection.prepareCall(query);
            stmt.setInt(1, patientID);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                perfilesFiscales.add(new PerfilFiscal(
                        resultSet.getInt("ID_PerfilFiscal"),
                        resultSet.getString("Nombre").concat(" ").concat(resultSet.getString("Apellido")),
                        resultSet.getString("RazonSocial"),
                        resultSet.getString("RFC"),
                        resultSet.getString("CP"),
                        resultSet.getString("Regimen")
                ));
            }
            return perfilesFiscales;
        } catch (SQLException e) {
            throw new SQLException("Error getting profiles for patient: " + e.getMessage());
        }
    }

    public ObservableList<Patient> getPatients() throws SQLException {
        String query = "SELECT * FROM Pacientes";
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = connection.prepareCall(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                patients.add(new Patient(
                        resultSet.getInt("ID_Paciente"),
                        resultSet.getString("Apellido"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("NumContacto1"),
                        resultSet.getString("NumContacto2"),
                        resultSet.getString("Correo")
                ));
            }
            return patients;
        } catch (SQLException e) {
            throw new SQLException("Error getting patients data: " + e.getMessage());
        }
    }

    public ObservableList<String> getCFDI() throws SQLException {
        String query = "SELECT ID_CFDI FROM UsosCFDI";
        ObservableList<String> cfdiList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cfdiList.add(resultSet.getString("ID_CFDI"));
            }
            return cfdiList;
        } catch (SQLException e) {
            throw new SQLException("Error accessing the CFDI catalog database: " + e.getMessage());
        }
    }

    public ObservableList<String> getRegimenes() throws SQLException {
        String query = "SELECT ID_Regimen FROM RegimenesFiscales";
        ObservableList<String> RegimenesList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RegimenesList.add(resultSet.getString("ID_Regimen"));
            }
            return RegimenesList;
        } catch (SQLException e) {
            throw new SQLException("Error accessing the CFDI catalog database: " + e.getMessage());
        }
    }

    public void insertPatient(String apellido, String nombre, String telefono1, String telefono2, String correo) throws SQLException {
        String query = "INSERT INTO Consultorio.Pacientes (Apellido, Nombre, NumContacto1, NumContacto2, Correo) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, apellido);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, telefono1);
            preparedStatement.setString(4, telefono2);
            preparedStatement.setString(5, correo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error inserting patient data: " + e.getMessage());
        }
    }

    public void insertSolicitud(Integer ID_Paciente, Integer ID_PerfilFiscal, String CFDI, String Monto, String Concepto, String FormaDePago) throws SQLException {
        String query = "INSERT INTO Consultorio.SolicitudesFacturas (Perfil_Fiscal, UsoCFDI, Monto, Concepto, Estado, FormaDePago) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
            preparedStatement.setInt(1, ID_PerfilFiscal);
            preparedStatement.setString(2, CFDI);
            preparedStatement.setString(3, Monto);
            preparedStatement.setString(4, Concepto);
            preparedStatement.setString(5, "Pendiente");
            preparedStatement.setString(6, FormaDePago);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("Error inserting patient data: " + e.getMessage());
        }
    }

    public void deleteSolicitud(Integer ID_Solicitud) throws SQLException {
        String query = "DELETE FROM SolicitudesFacturas WHERE ID_Solicitud = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
            preparedStatement.setInt(1, ID_Solicitud);
            preparedStatement.executeUpdate();
        }
    }

    public void insertProfile(Integer Paciente, String RazonSocial, String RFC, String CP, String Regimen) throws SQLException{
        String query = "INSERT INTO Consultorio.PerfilesFiscales (Paciente, RazonSocial, RFC, CP, Regimen) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)){
            preparedStatement.setInt(1, Paciente);
            preparedStatement.setString(2, RazonSocial);
            preparedStatement.setString(3, RFC);
            preparedStatement.setString(4, CP);
            preparedStatement.setString(5, Regimen);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error inserting profile data: " + e.getMessage());
        }
    }

    public ObservableList<Patient> searchPatient(Integer SearchType, String SearchValue) throws SQLException {
        String query;
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        ResultSet resultSet = null;

        if (SearchType > 4 || SearchType < 1) {
            throw new SQLException("Invalid search type");
        }

        switch (SearchType) {
            case 1:
                query = "{CALL BuscarPacienteApellido(?)}";
                try (CallableStatement stmt = connection.prepareCall(query)) {
                    stmt.setString(1, SearchValue);
                    resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        patients.add(new Patient(
                                resultSet.getInt("ID_Paciente"),
                                resultSet.getString("Apellido"),
                                resultSet.getString("Nombre"),
                                resultSet.getString("NumContacto1"),
                                resultSet.getString("NumContacto2"),
                                resultSet.getString("Correo")
                        ));
                    }
                } catch (SQLException e) {
                    throw new SQLException("Error searching patient by last name: " + e.getMessage());
                }
                break;

            case 2:
                query = "{CALL BuscarPacienteNombre(?)}";
                try (CallableStatement stmt = connection.prepareCall(query)) {
                    stmt.setString(1, SearchValue);
                    resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        patients.add(new Patient(
                                resultSet.getInt("ID_Paciente"),
                                resultSet.getString("Apellido"),
                                resultSet.getString("Nombre"),
                                resultSet.getString("NumContacto1"),
                                resultSet.getString("NumContacto2"),
                                resultSet.getString("Correo")
                        ));
                    }
                } catch (SQLException e) {
                    throw new SQLException("Error searching patient by name: " + e.getMessage());
                }
                break;

            case 3:
                query = "{CALL BuscarPacienteNumero(?)}";
                try (CallableStatement stmt = connection.prepareCall(query)) {
                    stmt.setString(1, SearchValue);
                    resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        patients.add(new Patient(
                                resultSet.getInt("ID_Paciente"),
                                resultSet.getString("Apellido"),
                                resultSet.getString("Nombre"),
                                resultSet.getString("NumContacto1"),
                                resultSet.getString("NumContacto2"),
                                resultSet.getString("Correo")
                        ));
                    }
                } catch (SQLException e) {
                    throw new SQLException("Error searching patient by phone: " + e.getMessage());
                }
                break;

            case 4:
                query = "{CALL BuscarPacienteCorreo(?)}";
                try (CallableStatement stmt = connection.prepareCall(query)) {
                    stmt.setString(1, SearchValue);
                    resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        patients.add(new Patient(
                                resultSet.getInt("ID_Paciente"),
                                resultSet.getString("Apellido"),
                                resultSet.getString("Nombre"),
                                resultSet.getString("NumContacto1"),
                                resultSet.getString("NumContacto2"),
                            resultSet.getString("Correo")
                        ));
                    }
                } catch (SQLException e) {
                    throw new SQLException("Error searching patient by email: " + e.getMessage());
                }
                break;
        }
        return patients;
    }
}