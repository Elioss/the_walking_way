package com.example.sanoyan.googlemaps.bdd;

/**
 * Created by Lionel on 15/02/2016.
 */
public class Position {
    private int id;
    private String utilisateur;
    private double latitude;
    private double longitude;


    //setters

    public void setId(int id) {
        this.id = id;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //getters

    public int getId() {
        return id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return ("Utilisateur: " + utilisateur + ", latitude: " + latitude
                +  ", longitude: " + longitude);
    }

    public Position(int id, String utilisateur, double latitude, double longitude) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Position(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Position(String utilisateur, double latitude, double longitude) {
        this.utilisateur = utilisateur;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Position() {

    }
}
