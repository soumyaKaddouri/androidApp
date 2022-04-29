package com.example.androidapp.model;

public class Etudiant {
    private String nom;
    private String prenom;
    private String tel;

    public Etudiant(String nom, String prenom, String tel){
        nom= new String(nom);
        prenom= new String(prenom);
        tel= new String(tel);
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
}
