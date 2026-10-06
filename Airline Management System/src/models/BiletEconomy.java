package models;

public class BiletEconomy extends Bilet {
    private double taxaBagajCala;

    public BiletEconomy(String idBilet, double pretDeBaza, double taxaBagajCala) {
        super(idBilet, pretDeBaza);
        this.taxaBagajCala = taxaBagajCala;
    }

    public double getTaxaBagajCala() { return taxaBagajCala; }
    public void setTaxaBagajCala(double taxaBagajCala) { this.taxaBagajCala = taxaBagajCala; }

    @Override
    public double calculeazaPretFinal() {
        return pretDeBaza + taxaBagajCala;
    }
}