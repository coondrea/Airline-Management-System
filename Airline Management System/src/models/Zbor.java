package models;

import java.time.LocalDateTime;

public class Zbor {
    private String numarZbor;
    private Aeroport plecare;
    private Aeroport destinatie;
    private Avion avion;
    private LocalDateTime oraPlecare;
    private int locuriOcupate;

    public Zbor(String numarZbor, Aeroport plecare, Aeroport destinatie, Avion avion, LocalDateTime oraPlecare) {
        this.numarZbor = numarZbor;
        this.plecare = plecare;
        this.destinatie = destinatie;
        this.avion = avion;
        this.oraPlecare = oraPlecare;
        this.locuriOcupate = 0; // un zbor nou are 0 locuri ocupate
    }

    public String getNumarZbor() { return numarZbor; }
    public void setNumarZbor(String numarZbor) { this.numarZbor = numarZbor; }

    public Aeroport getPlecare() { return plecare; }
    public void setPlecare(Aeroport plecare) { this.plecare = plecare; }

    public Aeroport getDestinatie() { return destinatie; }
    public void setDestinatie(Aeroport destinatie) { this.destinatie = destinatie; }

    public Avion getAvion() { return avion; }
    public void setAvion(Avion avion) { this.avion = avion; }

    public LocalDateTime getOraPlecare() { return oraPlecare; }
    public void setOraPlecare(LocalDateTime oraPlecare) { this.oraPlecare = oraPlecare; }

    public int getLocuriOcupate() { return locuriOcupate; }
    public void setLocuriOcupate(int locuriOcupate) { this.locuriOcupate = locuriOcupate; }
}