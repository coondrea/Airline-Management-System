package models;

public class Angajat extends Persoana {
    private String functie;
    private double salariu;

    public Angajat(String cnp, String nume, String prenume, String functie, double salariu) {
        super(cnp, nume, prenume);
        this.functie = functie;
        this.salariu = salariu;
    }

    public String getFunctie() { return functie; }
    public void setFunctie(String functie) { this.functie = functie; }

    public double getSalariu() { return salariu; }
    public void setSalariu(double salariu) { this.salariu = salariu; }
}