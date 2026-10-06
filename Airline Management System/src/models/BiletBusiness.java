package models;

public class BiletBusiness extends Bilet {
    private double taxaLounge;

    public BiletBusiness(String idBilet, double pretDeBaza, double taxaLounge) {
        super(idBilet, pretDeBaza);
        this.taxaLounge = taxaLounge;
    }

    public double getTaxaLounge() { return taxaLounge; }
    public void setTaxaLounge(double taxaLounge) { this.taxaLounge = taxaLounge; }

    @Override
    public double calculeazaPretFinal() {
        return pretDeBaza + taxaLounge + 150.0; // adaugam o taxa fixa de lux
    }
}