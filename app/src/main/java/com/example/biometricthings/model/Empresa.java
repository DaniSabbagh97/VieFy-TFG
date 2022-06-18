package com.example.biometricthings.model;

public class Empresa {
    private int id_empresa;
    private String nombre;
    private int dueño;
    private int SaldoInicial;
    private int SaldoActual;
    private double PagoLocal;
    private int id_propiedad;
    private String slogan;
    private String anuncio;
    private String cuerpoAnuncio;

    public Empresa() {
    }

    public Empresa(String nombre, int dueño, int saldoInicial, int saldoActual, double pagoLocal, int id_propiedad, String slogan, String anuncio, String cuerpoAnuncio) {
        this.nombre = nombre;
        this.dueño = dueño;
        SaldoInicial = saldoInicial;
        SaldoActual = saldoActual;
        PagoLocal = pagoLocal;
        this.id_propiedad = id_propiedad;
        this.slogan = slogan;
        this.anuncio = anuncio;
        this.cuerpoAnuncio = cuerpoAnuncio;
    }

    public Empresa(int id_empresa, String nombre, int dueño, int saldoInicial, int saldoActual, double pagoLocal, int id_propiedad, String slogan, String anuncio, String cuerpoAnuncio) {
        this.id_empresa = id_empresa;
        this.nombre = nombre;
        this.dueño = dueño;
        SaldoInicial = saldoInicial;
        SaldoActual = saldoActual;
        PagoLocal = pagoLocal;
        this.id_propiedad = id_propiedad;
        this.slogan = slogan;
        this.anuncio = anuncio;
        this.cuerpoAnuncio = cuerpoAnuncio;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDueño() {
        return dueño;
    }

    public void setDueño(int dueño) {
        this.dueño = dueño;
    }

    public int getSaldoInicial() {
        return SaldoInicial;
    }

    public void setSaldoInicial(int saldoInicial) {
        SaldoInicial = saldoInicial;
    }

    public int getSaldoActual() {
        return SaldoActual;
    }

    public void setSaldoActual(int saldoActual) {
        SaldoActual = saldoActual;
    }

    public double getPagoLocal() {
        return PagoLocal;
    }

    public void setPagoLocal(double pagoLocal) {
        PagoLocal = pagoLocal;
    }

    public int getId_propiedad() {
        return id_propiedad;
    }

    public void setId_propiedad(int id_propiedad) {
        this.id_propiedad = id_propiedad;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(String anuncio) {
        this.anuncio = anuncio;
    }

    public String getCuerpoAnuncio() {
        return cuerpoAnuncio;
    }

    public void setCuerpoAnuncio(String cuerpoAnuncio) {
        this.cuerpoAnuncio = cuerpoAnuncio;
    }
}
