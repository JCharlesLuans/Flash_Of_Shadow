package org.thunderbot.FOS.database.beans;

public class Effet {
    private int id;
    private String nom;
    private int statAgilite;
    private int statArmure;
    private int statDexterite;
    private int statEndurance;
    private int statForce;
    private int statIntelligence;
    private int statSagesse;
    private int dps;
    private int duree;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getStatAgilite() {
        return statAgilite;
    }

    public void setStatAgilite(int statAgilite) {
        this.statAgilite = statAgilite;
    }

    public int getStatArmure() {
        return statArmure;
    }

    public void setStatArmure(int statArmure) {
        this.statArmure = statArmure;
    }

    public int getStatDexterite() {
        return statDexterite;
    }

    public void setStatDexterite(int statDexterite) {
        this.statDexterite = statDexterite;
    }

    public int getStatEndurance() {
        return statEndurance;
    }

    public void setStatEndurance(int statEndurance) {
        this.statEndurance = statEndurance;
    }

    public int getStatForce() {
        return statForce;
    }

    public void setStatForce(int statForce) {
        this.statForce = statForce;
    }

    public int getStatIntelligence() {
        return statIntelligence;
    }

    public void setStatIntelligence(int statIntelligence) {
        this.statIntelligence = statIntelligence;
    }

    public int getStatSagesse() {
        return statSagesse;
    }

    public void setStatSagesse(int statSagesse) {
        this.statSagesse = statSagesse;
    }

    public int getDps() {
        return dps;
    }

    public void setDps(int dps) {
        this.dps = dps;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Effet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", statAgilite=" + statAgilite +
                ", statArmure=" + statArmure +
                ", statDexterite=" + statDexterite +
                ", statEndurance=" + statEndurance +
                ", statForce=" + statForce +
                ", statIntelligence=" + statIntelligence +
                ", statSagesse=" + statSagesse +
                ", dps=" + dps +
                ", duree=" + duree +
                ", image='" + image + '\'' +
                '}';
    }
}
