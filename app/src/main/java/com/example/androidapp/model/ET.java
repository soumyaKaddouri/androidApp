package com.example.androidapp.model;

import java.util.ArrayList;
import java.util.Date;

public class ET {
    ArrayList<CR> liste_cr;
    Date date_debut;
    //...

    public ET(Date date_debut, ArrayList<CR> liste_cr){
        this.date_debut=date_debut;
        this.liste_cr=liste_cr;
    }

    public ArrayList<CR> getListe_cr() {
        return liste_cr;
    }

    public void setListe_cr(ArrayList<CR> liste_cr) {
        this.liste_cr = liste_cr;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }
}
