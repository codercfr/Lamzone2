package com.example.lamzone2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reunion implements Parcelable {

    private final String heure ;

    private final String lieu;

    private final String sujet;

    private final String email;

    public Reunion(String heure, String lieu, String sujet, String email) {
        this.heure = heure;
        this.lieu = lieu;
        this.sujet = sujet;
        this.email = email;
    }

    protected Reunion(Parcel in) {
        heure = in.readString();
        lieu = in.readString();
        sujet = in.readString();
        email = in.readString();
    }

    public static final Creator<Reunion> CREATOR = new Creator<Reunion>() {
        @Override
        public Reunion createFromParcel(Parcel in) {
            return new Reunion(in);
        }

        @Override
        public Reunion[] newArray(int size) {
            return new Reunion[size];
        }
    };

    public String getHeure() {
        return heure;
    }

    public String getLieu() {
        return lieu;
    }

    public String getSujet() {
        return sujet;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "Reunion{" +
                "heure='" + heure + '\'' +
                ", lieu='" + lieu + '\'' +
                ", sujet='" + sujet + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(heure);
        dest.writeString(lieu);
        dest.writeString(sujet);
        dest.writeString(email);
    }
}
