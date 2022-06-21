package com.example.biometricthings.model;

public class Solicitud {

    private int id_solicitud;
    private int id_empresa;
    private int id_user;
    private String mensaje;
    private byte[] pdf;

    public Solicitud() {
    }

    public Solicitud(int id_empresa, int id_user, String mensaje, byte[] pdf) {
        this.id_empresa = id_empresa;
        this.id_user = id_user;
        this.mensaje = mensaje;
        this.pdf = pdf;
    }

    public Solicitud(int id_solicitud, int id_empresa, int id_user, String mensaje, byte[] pdf) {
        this.id_solicitud = id_solicitud;
        this.id_empresa = id_empresa;
        this.id_user = id_user;
        this.mensaje = mensaje;
        this.pdf = pdf;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
}
