package com.example.biometricthings.model;

public class Practicas {

    private int id_practica;
    private String nombrePractica;
    private int numEjercicios;
    private int valorTotal;
    private String fechaEntrega;
    private int id_clase;
    private int id_profesor;
    private byte[] pdf;
    private byte[] entrega;
    private int nota;
    private int beneficio;

    public Practicas(String nombrePractica, int numEjercicios, int valorTotal, String fechaEntrega, int id_clase, int id_profesor, byte[] pdf, int nota, int beneficio) {
        this.nombrePractica = nombrePractica;
        this.numEjercicios = numEjercicios;
        this.valorTotal = valorTotal;
        this.fechaEntrega = fechaEntrega;
        this.id_clase = id_clase;
        this.id_profesor = id_profesor;
        this.pdf = pdf;
        this.nota = nota;
        this.beneficio = beneficio;
    }

    public Practicas(int id_practica) {
        this.id_practica = id_practica;
    }

    public Practicas(String nombrePractica, int numEjercicios, int valorTotal, String fechaEntrega, int id_clase, byte[] pdf, int beneficio) {
        this.nombrePractica = nombrePractica;
        this.numEjercicios = numEjercicios;
        this.valorTotal = valorTotal;
        this.fechaEntrega = fechaEntrega;
        this.id_clase = id_clase;
        this.pdf = pdf;
        this.beneficio = beneficio;
    }

    public Practicas() {
    }

    public Practicas(int id_practica, String nombrePractica, byte[] entrega) {
        this.id_practica = id_practica;
        this.nombrePractica = nombrePractica;
        this.entrega = entrega;
    }

    public int getId_practica() {
        return id_practica;
    }

    public void setId_practica(int id_practica) {
        this.id_practica = id_practica;
    }

    public String getNombrePractica() {
        return nombrePractica;
    }

    public void setNombrePractica(String nombrePractica) {
        this.nombrePractica = nombrePractica;
    }

    public int getNumEjercicios() {
        return numEjercicios;
    }

    public void setNumEjercicios(int numEjercicios) {
        this.numEjercicios = numEjercicios;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(int beneficio) {
        this.beneficio = beneficio;
    }

    public byte[] getEntrega() {
        return entrega;
    }

    public void setEntrega(byte[] entrega) {
        this.entrega = entrega;
    }
}
