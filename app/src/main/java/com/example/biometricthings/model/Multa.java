package com.example.biometricthings.model;

public class Multa {

    private int id_user;
    private String motivo;
    private int cantidad;

    public Multa(int id_user, String motivo, int cantidad) {
        this.id_user = id_user;
        this.motivo = motivo;
        this.cantidad = cantidad;
    }

    public int getid_user() {
        return id_user;
    }

    public void setid_user(int id_user) {
        this.id_user = id_user;
    }

    public String getmotivo() {
        return motivo;
    }

    public void setmotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
