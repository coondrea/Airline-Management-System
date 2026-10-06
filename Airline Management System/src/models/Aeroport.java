package models;

public class Aeroport {
    private String codIATA;
    private String nume;
    private String oras;

    public Aeroport(String codIATA, String nume, String oras) {
        this.codIATA = codIATA;
        this.nume = nume;
        this.oras = oras;
    }

    public String getCodIATA() { return codIATA; }
    public void setCodIATA(String codIATA) { this.codIATA = codIATA; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getOras() { return oras; }
    public void setOras(String oras) { this.oras = oras; }
}