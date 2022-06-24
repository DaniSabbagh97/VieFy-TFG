package com.example.biometricthings.model;



public class Clase {

    private int id_clase;
    private String clave;
    private String nombre;
    private int numero_de_usos;
    private String fecha_inicio;
    private String fecha_fin;
    private int id_user;

    public Clase(int id_clase, String clave, String nombre, int numero_de_usos, String fecha_inicio, String fecha_fin, int id_user) {
        this.id_clase = id_clase;
        this.clave = clave;
        this.nombre = nombre;
        this.numero_de_usos = numero_de_usos;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.id_user = id_user;
    }

    public Clase(String clave, String nombre, int numero_de_usos, String fecha_inicio, String fecha_fin, int id_user) {
        this.clave = clave;
        this.nombre = nombre;
        this.numero_de_usos = numero_de_usos;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.id_user = id_user;
    }

    public Clase(String clave, String nombre, int numero_de_usos, String fecha_inicio, String fecha_fin) {
        this.clave = clave;
        this.nombre = nombre;
        this.numero_de_usos = numero_de_usos;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public Clase(String clave) {
        this.clave = clave;
    }

    public Clase() {
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero_de_usos() {
        return numero_de_usos;
    }

    public void setNumero_de_usos(int numero_de_usos) {
        this.numero_de_usos = numero_de_usos;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
