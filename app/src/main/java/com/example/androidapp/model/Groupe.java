package com.example.androidapp.model;

import java.util.LinkedList;

public class Groupe {
    private String titre;
    private String description;
    LinkedList<Student> liste_students;

    public Groupe(String titre, String description, LinkedList<Student> liste_students){
        this.titre= new String(titre);
        this.description= new String(description);
        this.liste_students = liste_students;
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

    public LinkedList<Student> getListe_etudiants() {
        return liste_students;
    }

    public void setListe_etudiants(LinkedList<Student> liste_students) {
        this.liste_students = liste_students;
    }
}
