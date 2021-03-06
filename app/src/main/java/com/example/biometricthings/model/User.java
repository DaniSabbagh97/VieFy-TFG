package com.example.biometricthings.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id_user;
    private int expediente;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String rol;
    private String rol_juego;
    private int id_clase;
    private int id_empresa;
    private int salario;
    private int salarioEmpresario;
    private int confirmado;
    private String contrasenia;
    private int isActive;
    private int id_propiedades;
    private int isProfe;
    //private byte[] imagen;
    private String imagen;
    private float saldoActual;

    public User(String email, String contrasenia) {
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public User(int id_user, int salario) {
        this.id_user = id_user;
        this.salario = salario;
    }

    public User(int id_user) {
        this.id_user = id_user;
    }





    public User(int id_user, String nombre, String apellidos, String email) {
        this.id_user = id_user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }

    public User(int id_user, int salario, int salarioEmpresario) {
        this.id_user = id_user;
        this.salario = salario;
        this.salarioEmpresario = salarioEmpresario;
    }

    public User(int expediente,
                String nombre,
                String apellidos,
                String email,
                String telefono,
                String rol,
                String rol_juego,
                int id_clase,
                int id_empresa,
                int salario,
                int salarioEmpresario,
                int confirmado,
                String contrasenia,
                int isActive) {
        this.expediente = expediente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.rol_juego = rol_juego;
        this.id_clase = id_clase;
        this.id_empresa = id_empresa;
        this.salario = salario;
        this.salarioEmpresario = salarioEmpresario;
        this.confirmado = confirmado;
        this.contrasenia = contrasenia;
        this.isActive = isActive;
    }



    public User() {
    }

    public User(int expediente,
                String nombre,
                String apellidos,
                String email,
                String telefono,
                String contrasenia,
                int isProfe,
                String rol) {
        this.expediente = expediente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.isProfe = isProfe;
        this.rol= rol;
    }
    public User(int expediente,
                String nombre,
                String apellidos,
                String email,
                String telefono,
                String contrasenia,
                int isProfe,
                String rol,
                String imagen) {
        this.expediente = expediente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.isProfe = isProfe;
        this.rol= rol;
        this.imagen= imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getExpediente() {
        return expediente;
    }

    public void setExpediente(int expediente) {
        this.expediente = expediente;
    }

    public String getapellidos() {
        return apellidos;
    }

    public void setapellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol_juego() {
        return rol_juego;
    }

    public void setRol_juego(String rol_juego) {
        this.rol_juego = rol_juego;
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getSalarioEmpresario() {
        return salarioEmpresario;
    }

    public void setSalarioEmpresario(int salarioEmpresario) {
        this.salarioEmpresario = salarioEmpresario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(int confirmado) {
        this.confirmado = confirmado;
    }

    public String getcontrasenia() {
        return contrasenia;
    }

    public void setcontrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getId_propiedades() {
        return id_propiedades;
    }

    public void setId_propiedades(int id_propiedades) {
        this.id_propiedades = id_propiedades;
    }

    public int getIsProfe() {
        return isProfe;
    }

    public void setIsProfe(int isProfe) {
        this.isProfe = isProfe;
    }

    /*public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }*/

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(float saldoActual) {
        this.saldoActual = saldoActual;
    }
}
