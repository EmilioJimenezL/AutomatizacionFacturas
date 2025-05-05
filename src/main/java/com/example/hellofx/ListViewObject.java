package com.example.hellofx;

public class ListViewObject {
    private Integer ID;
    private String Paciente;
    private String RazonSocial;
    private String RFC;
    private String Regimen;
    private String CFDI;
    private String Correo;
    private String Monto;
    private String FormaPago;
    private String Concepto;
    private String Telefono;
    private String Estado;

    public ListViewObject(Integer id, String paciente, String razonSocial, String RFC, String regimen, String CFDI, String correo, String monto, String formaPago, String concepto, String telefono, String estado) {
        this.ID = id;
        this.Paciente = paciente;
        this.RazonSocial = razonSocial;
        this.RFC = RFC;
        this.Regimen = regimen;
        this.CFDI = CFDI;
        this.Correo = correo;
        this.Monto = monto;
        this.FormaPago = formaPago;
        this.Concepto = concepto;
        this.Telefono = telefono;
        this.Estado = estado;
    }
    public Integer getID() {
        return ID;
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

    public String getRegimen() {
        return Regimen;
    }

    public void setRegimen(String regimen) {
        this.Regimen = regimen;
    }

    public String getCFDI() {
        return CFDI;
    }

    public void setCFDI(String CFDI) {
        this.CFDI = CFDI;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        this.Correo = correo;
    }

    public String getMonto() {
        return Monto;
    }

    public void setMonto(String monto) {
        this.Monto = monto;
    }

    public String getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(String formaPago) {
        this.FormaPago = formaPago;
    }

    public String getConcepto() {
        return Concepto;
    }

    public void setConcepto(String concepto) {
        this.Concepto = concepto;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        this.Telefono = telefono;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        this.Estado = estado;
    }
}
