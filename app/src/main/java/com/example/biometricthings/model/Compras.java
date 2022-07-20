package com.example.biometricthings.model;

public class Compras {

    private int id_compra;
    private Empresa empresa;
    private Practicas practica;
    private byte[] entrega;
    private int id_clase;
    private float nota;

    public Compras(int id_compras) {
        this.id_compra = id_compra;
    }

    public Compras() {
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Practicas getPractica() {
        return practica;
    }

    public void setPractica(Practicas practica) {
        this.practica = practica;
    }

    public byte[] getEntrega() {
        return entrega;
    }

    public void setEntrega(byte[] entrega) {
        this.entrega = entrega;
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
