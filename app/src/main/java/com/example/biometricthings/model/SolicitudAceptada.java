package com.example.biometricthings.model;

public class SolicitudAceptada {
    private int id_solicitud;
    private int salario;

    public SolicitudAceptada(int id_solicitud, int salario) {
        this.id_solicitud = id_solicitud;
        this.salario = salario;
    }

    public SolicitudAceptada() {
    }

    public SolicitudAceptada(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }
}
