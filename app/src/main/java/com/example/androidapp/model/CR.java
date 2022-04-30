package com.example.androidapp.model;

import java.util.LinkedList;

public class CR {
    String heure_debut;
    String description;
    LinkedList<Student> liste_absents;

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LinkedList<Student> getListe_absents() {
        return liste_absents;
    }

    public void setListe_absents(LinkedList<Student> liste_absents) {
        this.liste_absents = liste_absents;
    }
}
