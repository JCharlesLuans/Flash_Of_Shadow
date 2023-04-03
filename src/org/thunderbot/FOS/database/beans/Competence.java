package org.thunderbot.FOS.database.beans;

import java.io.Serializable;

public class Competence implements Serializable {
    private int _id;
    private int idEffet;
    private int degaBase;
    private int portee;
    private int cout;
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

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPortee() {
        return portee;
    }

    public void setPortee(int portee) {
        this.portee = portee;
    }

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    @Override
    public String toString() {
        return "Competence{" +
                "_id=" + _id +
                ", idEffet=" + idEffet +
                ", degaBase=" + degaBase +
                ", portee=" + portee +
                ", cout=" + cout +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
