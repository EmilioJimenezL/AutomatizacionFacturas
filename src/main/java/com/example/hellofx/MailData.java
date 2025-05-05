package com.example.hellofx;

public class MailData {
    private String Razon;
    private String RFC;
    private String CP;
    private String CFDI;
    private String Regimen;
    private String Monto;
    private String Concepto;

    public MailData(String Razon, String RFC, String CP, String CFDI, String Regimen, String Monto, String Concepto) {
        this.Razon = Razon;
        this.RFC = RFC;
        this.CP = CP;
        this.CFDI = CFDI;
        this.Regimen = Regimen;
        this.Monto = Monto;
        this.Concepto = Concepto;
    }

    public String getRazon() {
        return Razon;
    }

    public String getRFC() {
        return RFC;
    }

    public String getCP() {
        return CP;
    }

    public String getCFDI() {
        return CFDI;
    }

    public String getRegimen() {
        return Regimen;
    }

    public String getMonto() {
        return Monto;
    }

    public String getConcepto() {
        return Concepto;
    }

}
