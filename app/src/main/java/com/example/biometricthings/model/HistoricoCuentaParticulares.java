package com.example.biometricthings.model;

public class HistoricoCuentaParticulares {
    private int id_historicoParticular;
    private int id_user;
    private int Saldo;
    private int Importe;
    private String Comentario;
    private String tipo_gasto;
    private String Hora;


    public HistoricoCuentaParticulares() {
    }


    public HistoricoCuentaParticulares(int id_historicoParticular, int id_user, int saldo, int importe, String comentario, String tipo_gasto, String hora) {
        this.id_historicoParticular = id_historicoParticular;
        this.id_user = id_user;
        Saldo = saldo;
        Importe = importe;
        Comentario = comentario;
        this.tipo_gasto = tipo_gasto;
        Hora = hora;
    }

    public HistoricoCuentaParticulares(int id_user, int saldo, int importe, String comentario, String tipo_gasto, String hora) {
        this.id_user = id_user;
        Saldo = saldo;
        Importe = importe;
        Comentario = comentario;
        this.tipo_gasto = tipo_gasto;
        Hora = hora;
    }

    public int getId_historicoParticular() {
        return id_historicoParticular;
    }

    public void setId_historicoParticular(int id_historicoParticular) {
        this.id_historicoParticular = id_historicoParticular;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getSaldo() {
        return Saldo;
    }

    public void setSaldo(int saldo) {
        Saldo = saldo;
    }

    public int getImporte() {
        return Importe;
    }

    public void setImporte(int importe) {
        Importe = importe;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public String getTipo_gasto() {
        return tipo_gasto;
    }

    public void setTipo_gasto(String tipo_gasto) {
        this.tipo_gasto = tipo_gasto;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }
}
