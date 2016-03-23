package com.example.sanoyan.googlemaps.bdd;

/**
 * Created by Lionel on 15/02/2016.
 */
public class Waypoint extends Position{

    private String nom;
    private String type;
    private String commentaire;

    //setters

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }


    //getters

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getCommentaire() {
        return commentaire;
    }


    @Override
    public String toString() {
        return ("Utilisateur: " + super.getUtilisateur() + "nom du waypoint: " + nom + ", type: " + type +  ", commentaire: " + commentaire
                + ", longeur: " + super.getLongitude() +  ", latitude: " + super.getLatitude() );
    }

    public Waypoint() {

    }

    public Waypoint(String utilisateur, String nom, String type, String commentaire, double longitude, double latitude) {
        super(utilisateur, longitude, latitude);
        this.nom = nom;
        this.type = type;
        this.commentaire = commentaire;

    }

    public Waypoint(String nom, String type, String commentaire, double longitude, double latitude) {
        super(longitude, latitude);
        this.nom = nom;
        this.type = type;
        this.commentaire = commentaire;
    }



}
