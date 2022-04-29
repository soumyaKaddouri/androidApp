package com.example.androidapp.model;

import java.util.LinkedList;

public class Professeur {
    private String nom;
    private String prenom;
    private String tel;
    private LinkedList<Groupe> liste_groupe;
    //...

    public Professeur(String nom, String prenom, String tel,
                      LinkedList<Groupe> liste_groupe){
        nom= new String(nom);
        prenom= new String(prenom);
        tel= new String(tel);
        this.liste_groupe=liste_groupe;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LinkedList<Groupe> getListe_groupe() {
        return liste_groupe;
    }

    public void setListe_groupe(LinkedList<Groupe> liste_groupe) {
        this.liste_groupe = liste_groupe;
    }
}
