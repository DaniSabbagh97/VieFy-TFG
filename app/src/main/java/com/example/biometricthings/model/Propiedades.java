package com.example.biometricthings.model;

import java.util.ArrayList;

public class Propiedades {

    private int id_propiedades;
    private String Nombre;
    private String Descripcion;
    private double precio;
    private int Habitaciones;
    private int Baños;
    private int metros_2;
    private String Planta;
    private String zona;
    private String municipio;
    private String distrito;
    private int Terraza;
    private int Piscina;
    private int aireAcondicionado;
    private int Parking;
    private String Direccion;
    private String img1;
    private String img2;
    private String img3;
    private String tipo;

    public Propiedades() {
    }

    public Propiedades(int id_propiedades) {
        this.id_propiedades = id_propiedades;
    }

    public Propiedades(int id_propiedades, String nombre, String descripcion, double precio, int habitaciones, int baños, int metros_2, String planta, String zona, String municipio, String distrito, int terraza, int piscina, int aireAcondicionado, int parking, String direccion, String img1, String img2, String img3, String tipo) {
        this.id_propiedades = id_propiedades;
        Nombre = nombre;
        Descripcion = descripcion;
        this.precio = precio;
        Habitaciones = habitaciones;
        Baños = baños;
        this.metros_2 = metros_2;
        Planta = planta;
        this.zona = zona;
        this.municipio = municipio;
        this.distrito = distrito;
        Terraza = terraza;
        Piscina = piscina;
        this.aireAcondicionado = aireAcondicionado;
        Parking = parking;
        Direccion = direccion;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.tipo = tipo;
    }

    public Propiedades(int id_propiedades, String nombre, String descripcion, double precio, int habitaciones, int baños, int metros_2, String planta, String zona, String municipio, String distrito, int terraza, int piscina, int aireAcondicionado, int parking, String img1, String img2, String img3, String tipo) {
        this.id_propiedades = id_propiedades;
        Nombre = nombre;
        Descripcion = descripcion;
        this.precio = precio;
        Habitaciones = habitaciones;
        Baños = baños;
        this.metros_2 = metros_2;
        Planta = planta;
        this.zona = zona;
        this.municipio = municipio;
        this.distrito = distrito;
        Terraza = terraza;
        Piscina = piscina;
        this.aireAcondicionado = aireAcondicionado;
        Parking = parking;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.tipo = tipo;
    }

    public int getId_propiedades() {
        return id_propiedades;
    }

    public void setId_propiedades(int id_propiedades) {
        this.id_propiedades = id_propiedades;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getHabitaciones() {
        return Habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        Habitaciones = habitaciones;
    }

    public int getBaños() {
        return Baños;
    }

    public void setBaños(int baños) {
        Baños = baños;
    }

    public int getMetros_2() {
        return metros_2;
    }

    public void setMetros_2(int metros_2) {
        this.metros_2 = metros_2;
    }

    public String getPlanta() {
        return Planta;
    }

    public void setPlanta(String planta) {
        Planta = planta;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public int getTerraza() {
        return Terraza;
    }

    public void setTerraza(int terraza) {
        Terraza = terraza;
    }

    public int getPiscina() {
        return Piscina;
    }

    public void setPiscina(int piscina) {
        Piscina = piscina;
    }

    public int getAireAcondicionado() {
        return aireAcondicionado;
    }

    public void setAireAcondicionado(int aireAcondicionado) {
        this.aireAcondicionado = aireAcondicionado;
    }

    public int getParking() {
        return Parking;
    }

    public void setParking(int parking) {
        Parking = parking;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

