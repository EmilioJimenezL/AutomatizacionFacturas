package com.example.hellofx;

public class PerfilFiscal {
    private int IDPerfilFiscal;
    private String Paciente;
    private String RazonSocial;
    private String RFC;
    private String CP;
    private String Regimen;

    public PerfilFiscal(int IDPerfilFiscal, String paciente, String razonSocial, String RFC, String CP, String regimen) {
        this.IDPerfilFiscal = IDPerfilFiscal;
        this.Paciente = paciente;
        this.RazonSocial = razonSocial;
        this.RFC = RFC;
        this.CP = CP;
        this.Regimen = regimen;
    }

    public int getIDPerfilFiscal() {
        return IDPerfilFiscal;
    }

    public void setIDPerfilFiscal(int IDPerfilFiscal) {
        this.IDPerfilFiscal = IDPerfilFiscal;
    }

    public String getPaciente() {
        return Paciente;
    }

    public void setPaciente(String paciente) {
        this.Paciente = paciente;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.RazonSocial = razonSocial;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getRegimen() {
        return Regimen;
    }

    public void setRegimen(String regimen) {
        this.Regimen = regimen;
    }
}
