package com.example.ndourbaila.ufrset;

/**
 * Created by ndourbaila on 15/11/2017.
 */
public class Etudiant {
    private String nom;
    private String prenom;
    private String ine;
    private String filiere;
    private String niveau;
    private String adresse;

    public Etudiant(){}

    public Etudiant(String nom ,String prenom,String ine,String filiere,String niveau){
        this.nom = nom;
        this.prenom = prenom;
        this.ine = ine;
        this.filiere = filiere;
        this.niveau = niveau;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getFiliere() {
        return filiere;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
