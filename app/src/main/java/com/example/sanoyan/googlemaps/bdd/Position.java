package com.example.sanoyan.googlemaps.bdd;

/**
 * Created by Lionel on 15/02/2016.
 */
public class Position {
    private int id;
    private String utilisateur;
    private int longitude;
    private int latitude;

    //setters

    public void setId(int id) {
        this.id = id;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    //getters

    public int getId() {
        return id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return ("Utilisateur: " + utilisateur + ", longitude: " + longitude
                +  ", latitude: " + latitude);
    }

    public Position(int id, String utilisateur, int longitude, int latitude) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Position(String utilisateur, int longitude, int latitude) {
        this.utilisateur = utilisateur;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
