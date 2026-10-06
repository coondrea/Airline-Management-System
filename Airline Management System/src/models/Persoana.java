package models;

public abstract class Persoana {
    // folosim protected pentru ca Pasager si Angajat sa le poata accesa la nevoie
    protected String cnp;
    protected String nume;
    protected String prenume;

    public Persoana(String cnp, String nume, String prenume) {
        this.cnp = cnp;
        this.nume = nume;
        this.prenume = prenume;
    }

    public String getCnp() { return cnp; }
    public void setCnp(String cnp) { this.cnp = cnp; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }
}