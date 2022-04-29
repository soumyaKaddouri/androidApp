package com.example.androidapp.model;

import java.util.LinkedList;

public class Groupe {
    private String titre;
    private String description;
    LinkedList<Etudiant> liste_etudiants;

    public Groupe(String titre, String description, LinkedList<Etudiant> liste_etudiants ){
        this.titre= new String(titre);
        this.description= new String(description);
        this.liste_etudiants=liste_etudiants;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LinkedList<Etudiant> getListe_etudiants() {
        return liste_etudiants;
    }

    public void setListe_etudiants(LinkedList<Etudiant> liste_etudiants) {
        this.liste_etudiants = liste_etudiants;
    }
}
