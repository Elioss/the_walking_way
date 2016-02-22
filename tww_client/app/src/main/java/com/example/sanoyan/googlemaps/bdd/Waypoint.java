package com.example.sanoyan.googlemaps.bdd;

/**
 * Created by Lionel on 15/02/2016.
 */
public class Waypoint {

    private int id;
    private String utilisateur;
    private String nom;
    private String type;
    private String commentaire;
    private int longueur;
    private int latitude;

    //setters

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }


    //getters


    public String getUtilisateur() {
        return utilisateur;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return ("Utilisateur: " + utilisateur + "nom du waypoint: " + nom + ", type: " + type +  ", commentaire: " + commentaire
                + ", longeur: " + longueur +  ", latitude: " + latitude );
    }

    public Waypoint(int id, String utilisateur, String nom, String type, String commentaire, int longueur, int latitude) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.nom = nom;
        this.type = type;
        this.commentaire = commentaire;
        this.longueur = longueur;
        this.latitude = latitude;
    }

    public Waypoint(String nom, String type, String commentaire, int longueur, int latitude) {
        this.nom = nom;
        this.type = type;
        this.commentaire = commentaire;
        this.longueur = longueur;
        this.latitude = latitude;
    }
}
