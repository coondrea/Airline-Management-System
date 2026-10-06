package services;
import models.*;
import java.util.*;

public class AirlineService {
    // 1. map pentru stocarea si cautarea rapida a zborurilor
    private Map<String, Zbor> zboruri = new HashMap<>();

    // 2. lista pentru bilete (foloseste mostenirea, va contine clase derivate)
    private List<Bilet> bileteVandute = new ArrayList<>();

    // 3. colectie sortata: treeset care sorteaza pasagerii alfabetic dupa nume si prenume
    private Set<Pasager> pasageri = new TreeSet<>(Comparator.comparing(Pasager::getNume).thenComparing(Pasager::getPrenume));

    // --- operatiile sistemului ---

    public void adaugaZbor(Zbor zbor) {
        zboruri.put(zbor.getNumarZbor(), zbor);

        // inregistram actiunea in audit
        AuditService.getInstance().logAction("adauga_zbor");
    }

    public void cumparaBilet(Bilet bilet, Pasager pasager, String numarZbor) {
        Zbor zbor = zboruri.get(numarZbor);

        if (zbor != null && zbor.getLocuriOcupate() < zbor.getAvion().getCapacitateMaxima()) {
            bileteVandute.add(bilet);
            pasageri.add(pasager);
            zbor.setLocuriOcupate(zbor.getLocuriOcupate() + 1);
            System.out.println("Bilet achizitionat cu succes pentru: " + pasager.getNume());

            // inregistram actiunea in audit doar daca s-a cumparat cu succes
            AuditService.getInstance().logAction("cumpara_bilet");
        } else {
            System.out.println("Eroare: Zborul nu exista sau este plin.");
        }
    }

    public void afiseazaPasageriSortati() {
        System.out.println("--- Lista Pasageri (Sortata Alfabetic) ---");
        for (Pasager p : pasageri) {
            System.out.println(p.getNume() + " " + p.getPrenume() + " - Pasaport: " + p.getNumarPasaport());
        }

        // inregistram actiunea
        AuditService.getInstance().logAction("afiseaza_pasageri");
    }

    public double calculeazaIncasariTotale() {
        double total = 0;
        for (Bilet bilet : bileteVandute) {
            total += bilet.calculeazaPretFinal();
        }

        // inregistram actiunea
        AuditService.getInstance().logAction("calculeaza_incasari");

        return total;
    }

    public void afiseazaZboruri() {
        System.out.println("--- Zboruri in sistem ---");
        for (Zbor zbor : zboruri.values()) {
            System.out.println("Zbor " + zbor.getNumarZbor() + ": " +
                    zbor.getPlecare().getOras() + " -> " +
                    zbor.getDestinatie().getOras() +
                    " | Locuri ocupate: " + zbor.getLocuriOcupate() + "/" + zbor.getAvion().getCapacitateMaxima());
        }

        // inregistram actiunea
        AuditService.getInstance().logAction("afiseaza_zboruri");
    }
}