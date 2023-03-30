package org.thunderbot.FOS.database.beans;

public class Competence {
    private int _id;
    private int idEffet;
    private int degaBase;
    private String nom;
    private String image;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getDegaBase() {
        return degaBase;
    }

    public void setDegaBase(int degaBase) {
        this.degaBase = degaBase;
    }

    public int getIdEffet() {
        return idEffet;
    }

    public void setIdEffet(int idEffet) {
        this.idEffet = idEffet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
