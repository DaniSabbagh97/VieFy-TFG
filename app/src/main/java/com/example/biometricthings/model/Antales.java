package com.example.biometricthings.model;

public class Antales {
    private int id_antales;
    private String tipo;
    private String mensaje;
    private int cuenta;
    private int beneficio;
    private int id_user;
    private int id_empresa;
    private int id_clase;
    private Empresa empresa;
    private User user;

    public Antales() {
    }

    public Antales(String tipo, String mensaje, int cuenta, int beneficio, int id_user, int id_empresa, int id_clase) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.cuenta = cuenta;
        this.beneficio = beneficio;
        this.id_user = id_user;
        this.id_empresa = id_empresa;
        this.id_clase = id_clase;
    }

    public int getId_antales() {
        return id_antales;
    }

    public void setId_antales(int id_antales) {
        this.id_antales = id_antales;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
