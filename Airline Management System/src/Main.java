import models.*;
import services.*;
import utils.GeneratorDate;

import java.time.LocalDateTime;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // 1. initializam serviciul
        AirlineService serviciu = new AirlineService();

        // 2. cream datele de baza (aeroporturi si avioane)
        Aeroport otopeni = new Aeroport("OTP", "Henri Coanda", "Bucuresti");
        Aeroport jfk = new Aeroport("JFK", "John F. Kennedy", "New York");
        Aeroport cdg = new Aeroport("CDG", "Charles de Gaulle", "Paris");
        Aeroport lhr = new Aeroport("LHR", "Heathrow", "Londra");

        Avion boeing = new Avion("B737", "Boeing 737", 150);
        Avion airbus = new Avion("A320", "Airbus A320", 180);
        Avion embraer = new Avion("E190", "Embraer 190", 100);

        // 3. cream mai multe zboruri
        Zbor zborNewYork = new Zbor("RO101", otopeni, jfk, boeing, LocalDateTime.of(2026, 6, 15, 14, 30));
        Zbor zborParis = new Zbor("RO202", otopeni, cdg, airbus, LocalDateTime.of(2026, 6, 16, 8, 45));
        Zbor zborLondra = new Zbor("RO303", otopeni, lhr, embraer, LocalDateTime.of(2026, 6, 17, 19, 0));

        // 4. adaugam zborurile in serviciu
        serviciu.adaugaZbor(zborNewYork);
        serviciu.adaugaZbor(zborParis);
        serviciu.adaugaZbor(zborLondra);

        serviciu.afiseazaZboruri();

        // --- Generare dinamica de date ---
        System.out.println("\n--- Procesare Achizitii Dinamice ---");

        Random random = new Random();
        String[] zboruriDisponibile = {"RO101", "RO202", "RO303"};

        // marim numarul la 30 de persoane ca sa avem suficienti pasageri pe toate zborurile
        for (int i = 0; i < 30; i++) {
            Pasager pasagerRandom = GeneratorDate.genereazaPasagerRandom();
            Bilet biletRandom = GeneratorDate.genereazaBiletRandom();

            // pasagerul alege un zbor random din cele disponibile
            String zborAles = zboruriDisponibile[random.nextInt(zboruriDisponibile.length)];

            serviciu.cumparaBilet(biletRandom, pasagerRandom, zborAles);
            PasagerRepository.getInstance().create(pasagerRandom);
        }

        // 5. testam rezultatele finale
        System.out.println("\n");
        serviciu.afiseazaPasageriSortati();

        System.out.println("\n--- Raport Financiar ---");
        System.out.println("Incasari totale din toate zborurile: " + serviciu.calculeazaIncasariTotale() + " EUR");

        // --- testare jdbc (baza de date) ---
        System.out.println("\n--- Salvare Sistem in Baza de Date ---");

        AeroportRepository aeroportRepo = AeroportRepository.getInstance();
        AvionRepository avionRepo = AvionRepository.getInstance();
        ZborRepository zborRepo = ZborRepository.getInstance();

        // salvam intai toate dependintele (foreign keys)
        // nota: daca primesti erori rosii de "duplicate key" in consola, e in regula!
        // inseamna ca postgresql isi face treaba si protejeaza datele deja salvate anterior.
        aeroportRepo.create(otopeni);
        aeroportRepo.create(jfk);
        aeroportRepo.create(cdg);
        aeroportRepo.create(lhr);

        avionRepo.create(boeing);
        avionRepo.create(airbus);
        avionRepo.create(embraer);

        // acum putem salva zborurile
        zborRepo.create(zborNewYork);
        zborRepo.create(zborParis);
        zborRepo.create(zborLondra);

        // verificam un zbor oarecare (ex: cel de Paris)
        Zbor zborDinDb = zborRepo.read("RO202");
        if (zborDinDb != null) {
            System.out.println("Succes! Am citit zborul din db: " + zborDinDb.getNumarZbor() +
                    " care zboara catre " + zborDinDb.getDestinatie().getOras() +
                    " cu " + zborDinDb.getLocuriOcupate() + " pasageri la bord.");
        }
    }
}