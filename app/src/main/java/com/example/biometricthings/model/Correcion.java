package com.example.biometricthings.model;

public class Correcion {
    private int id_compra;
    private float nota;

    public Correcion() {
    }

    public Correcion(int id_compra) {
        this.id_compra = id_compra;
    }

    public Correcion(int id_compra, float nota) {
        this.id_compra = id_compra;
        this.nota = nota;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
