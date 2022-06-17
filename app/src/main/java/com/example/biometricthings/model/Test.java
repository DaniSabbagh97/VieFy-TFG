package com.example.biometricthings.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Test implements Parcelable {

    private int id;
    private int id_user;
    private int ImpB2;
    private int ImpB3;
    private int ImpB4;
    private int ImpB5;
    private int FutC2;
    private int FutC4;
    private int FutC3;
    private int FutC5;
    private int ImpB8;
    private int ImpB9;
    private int ImpB10;
    private int ImpB11;
    private double FelicidadInicial;
    private double FelicidadFinalEstimada;
    private String Rol;
    private String Pareja;
    private int NHijos;

    public Test(int id_user, int impB2, int impB3, int impB4, int impB5, int futC2, int futC4, int futC3, int futC5, int impB8, int impB9, int impB10, int impB11, double felicidadInicial, double felicidadFinalEstimada, String rol, String pareja, int NHijos) {
        this.id_user = id_user;
        ImpB2 = impB2;
        ImpB3 = impB3;
        ImpB4 = impB4;
        ImpB5 = impB5;
        FutC2 = futC2;
        FutC4 = futC4;
        FutC3 = futC3;
        FutC5 = futC5;
        ImpB8 = impB8;
        ImpB9 = impB9;
        ImpB10 = impB10;
        ImpB11 = impB11;
        FelicidadInicial = felicidadInicial;
        FelicidadFinalEstimada = felicidadFinalEstimada;
        Rol = rol;
        Pareja = pareja;
        this.NHijos = NHijos;
    }

    public Test(int id_user, int impB2, int impB3, int impB4, int impB5, int futC2, int futC4, int futC3, int futC5, int impB8, int impB9, int impB10, int impB11, String rol, String pareja, int NHijos) {
        this.id_user = id_user;
        ImpB2 = impB2;
        ImpB3 = impB3;
        ImpB4 = impB4;
        ImpB5 = impB5;
        FutC2 = futC2;
        FutC4 = futC4;
        FutC3 = futC3;
        FutC5 = futC5;
        ImpB8 = impB8;
        ImpB9 = impB9;
        ImpB10 = impB10;
        ImpB11 = impB11;
        Rol = rol;
        Pareja = pareja;
        this.NHijos = NHijos;
    }

    protected Test(Parcel in) {
        id = in.readInt();
        id_user = in.readInt();
        ImpB2 = in.readInt();
        ImpB3 = in.readInt();
        ImpB4 = in.readInt();
        ImpB5 = in.readInt();
        FutC2 = in.readInt();
        FutC4 = in.readInt();
        FutC3 = in.readInt();
        FutC5 = in.readInt();
        ImpB8 = in.readInt();
        ImpB9 = in.readInt();
        ImpB10 = in.readInt();
        ImpB11 = in.readInt();
        FelicidadInicial = in.readDouble();
        FelicidadFinalEstimada = in.readDouble();
        Rol = in.readString();
        Pareja = in.readString();
        NHijos = in.readInt();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(id_user);
        parcel.writeInt(ImpB2);
        parcel.writeInt(ImpB3);
        parcel.writeInt(ImpB4);
        parcel.writeInt(ImpB5);
        parcel.writeInt(FutC2);
        parcel.writeInt(FutC4);
        parcel.writeInt(FutC3);
        parcel.writeInt(FutC5);
        parcel.writeInt(ImpB8);
        parcel.writeInt(ImpB9);
        parcel.writeInt(ImpB10);
        parcel.writeInt(ImpB11);
        parcel.writeDouble(FelicidadInicial);
        parcel.writeDouble(FelicidadFinalEstimada);
        parcel.writeString(Rol);
        parcel.writeString(Pareja);
        parcel.writeInt(NHijos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getImpB2() {
        return ImpB2;
    }

    public void setImpB2(int impB2) {
        ImpB2 = impB2;
    }

    public int getImpB3() {
        return ImpB3;
    }

    public void setImpB3(int impB3) {
        ImpB3 = impB3;
    }

    public int getImpB4() {
        return ImpB4;
    }

    public void setImpB4(int impB4) {
        ImpB4 = impB4;
    }

    public int getImpB5() {
        return ImpB5;
    }

    public void setImpB5(int impB5) {
        ImpB5 = impB5;
    }

    public int getFutC2() {
        return FutC2;
    }

    public void setFutC2(int futC2) {
        FutC2 = futC2;
    }

    public int getFutC4() {
        return FutC4;
    }

    public void setFutC4(int futC4) {
        FutC4 = futC4;
    }

    public int getFutC3() {
        return FutC3;
    }

    public void setFutC3(int futC3) {
        FutC3 = futC3;
    }

    public int getFutC5() {
        return FutC5;
    }

    public void setFutC5(int futC5) {
        FutC5 = futC5;
    }

    public int getImpB8() {
        return ImpB8;
    }

    public void setImpB8(int impB8) {
        ImpB8 = impB8;
    }

    public int getImpB9() {
        return ImpB9;
    }

    public void setImpB9(int impB9) {
        ImpB9 = impB9;
    }

    public int getImpB10() {
        return ImpB10;
    }

    public void setImpB10(int impB10) {
        ImpB10 = impB10;
    }

    public int getImpB11() {
        return ImpB11;
    }

    public void setImpB11(int impB11) {
        ImpB11 = impB11;
    }

    public double getFelicidadInicial() {
        return FelicidadInicial;
    }

    public void setFelicidadInicial(double felicidadInicial) {
        FelicidadInicial = felicidadInicial;
    }

    public double getFelicidadFinalEstimada() {
        return FelicidadFinalEstimada;
    }

    public void setFelicidadFinalEstimada(double felicidadFinalEstimada) {
        FelicidadFinalEstimada = felicidadFinalEstimada;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getPareja() {
        return Pareja;
    }

    public void setPareja(String pareja) {
        Pareja = pareja;
    }

    public int getNHijos() {
        return NHijos;
    }

    public void setNHijos(int NHijos) {
        this.NHijos = NHijos;
    }

    public static Creator<Test> getCREATOR() {
        return CREATOR;
    }


}
