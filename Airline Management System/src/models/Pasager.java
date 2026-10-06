package models;

public class Pasager extends Persoana {
    private String numarPasaport;

    public Pasager(String cnp, String nume, String prenume, String numarPasaport) {
        super(cnp, nume, prenume);
        this.numarPasaport = numarPasaport;
    }

    public String getNumarPasaport() { return numarPasaport; }
    public void setNumarPasaport(String numarPasaport) { this.numarPasaport = numarPasaport; }
}
