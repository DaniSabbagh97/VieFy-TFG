package com.example.biometricthings.model;

public class Anual {

    private String mensaje;
    private int cuenta;
    private int beneficio;
    private String tipo;


    public Anual(String mensaje, int cuenta, int beneficio, String tipo) {
        this.mensaje = mensaje;
        this.cuenta = cuenta;
        this.beneficio = beneficio;
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public int getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(int beneficio) {
        this.beneficio = beneficio;
    }
}
