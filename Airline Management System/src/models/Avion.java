package models;

public class Avion {
    private String idAvion;
    private String model;
    private int capacitateMaxima;

    public Avion(String idAvion, String model, int capacitateMaxima) {
        this.idAvion = idAvion;
        this.model = model;
        this.capacitateMaxima = capacitateMaxima;
    }

    public String getIdAvion() { return idAvion; }
    public void setIdAvion(String idAvion) { this.idAvion = idAvion; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getCapacitateMaxima() { return capacitateMaxima; }
    public void setCapacitateMaxima(int capacitateMaxima) { this.capacitateMaxima = capacitateMaxima; }
}