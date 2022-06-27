package com.example.biometricthings.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PasarBytes implements Parcelable {

    private byte[] pdf;


    public PasarBytes() {
    }

    public PasarBytes(byte[] pdf) {
        this.pdf = pdf;
    }

    protected PasarBytes(Parcel in) {
        pdf = in.createByteArray();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(pdf);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PasarBytes> CREATOR = new Creator<PasarBytes>() {
        @Override
        public PasarBytes createFromParcel(Parcel in) {
            return new PasarBytes(in);
        }

        @Override
        public PasarBytes[] newArray(int size) {
            return new PasarBytes[size];
        }
    };

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

}
