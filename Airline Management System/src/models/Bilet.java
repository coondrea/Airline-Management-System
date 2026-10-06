package models;

public abstract class Bilet {
    protected String idBilet;
    protected double pretDeBaza;

    public Bilet(String idBilet, double pretDeBaza) {
        this.idBilet = idBilet;
        this.pretDeBaza = pretDeBaza;
    }

    public String getIdBilet() { return idBilet; }
    public void setIdBilet(String idBilet) { this.idBilet = idBilet; }

    public double getPretDeBaza() { return pretDeBaza; }
    public void setPretDeBaza(double pretDeBaza) { this.pretDeBaza = pretDeBaza; }

    // metoda abstracta: obliga clasele copil sa implementeze propria logica de calcul
    public abstract double calculeazaPretFinal();
}