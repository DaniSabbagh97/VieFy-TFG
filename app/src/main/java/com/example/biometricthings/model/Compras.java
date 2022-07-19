package com.example.biometricthings.model;

public class Compras {

    private int id_compras;
    private Empresa empresa;
    private Practicas practicas;

    public Compras(int id_compras, Empresa empresa, Practicas practicas) {
        this.id_compras = id_compras;
        this.empresa = empresa;
        this.practicas = practicas;
    }

    public Compras(Empresa empresa, Practicas practicas) {
        this.empresa = empresa;
        this.practicas = practicas;
    }

    public Compras(int id_compras) {
        this.id_compras = id_compras;
    }

    public Compras() {
    }

    public int getId_compras() {
        return id_compras;
    }

    public void setId_compras(int id_compras) {
        this.id_compras = id_compras;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Practicas getPracticas() {
        return practicas;
    }

    public void setPracticas(Practicas practicas) {
        this.practicas = practicas;
    }
}
