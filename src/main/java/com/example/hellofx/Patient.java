package com.example.hellofx;

public class Patient {
    private int id_Paciente;
    private String apellido;
    private String nombre;
    private String numContacto1;
    private String numContacto2;
    private String correo;

    public Patient(int id_Paciente, String apellido, String nombre, String numContacto1, String numContacto2, String correo) {
        this.id_Paciente = id_Paciente;
        this.apellido = apellido;
        this.nombre = nombre;
        this.numContacto1 = numContacto1;
        this.numContacto2 = numContacto2;
        this.correo = correo;
    }

    public int getId_Paciente() {
        return id_Paciente;
    }

    public void setId_Paciente(int id_Paciente) {
        this.id_Paciente = id_Paciente;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumContacto1() {
        return numContacto1;
    }

    public void setNumContacto1(String numContacto1) {
        this.numContacto1 = numContacto1;
    }

    public String getNumContacto2() {
        return numContacto2;
    }

    public void setNumContacto2(String numContacto2) {
        this.numContacto2 = numContacto2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
