package utils;

import models.Bilet;
import models.BiletBusiness;
import models.BiletEconomy;
import models.Pasager;

import java.util.Random;

public class GeneratorDate {

    // 1. liste predefinite de nume si prenume
    private static final String[] NUME = {"Popescu", "Ionescu", "Stan", "Radu", "Dumitrescu", "Gheorghe", "Marin", "Ilie", "Vasilescu", "Dinu"};
    private static final String[] PRENUME = {"Ion", "Andrei", "Maria", "Elena", "Mihai", "Alexandru", "Ioana", "Cristina", "Gabriel", "Ana"};

    // 2. clasa random ne ajuta sa generam numere aleatorii
    private static final Random random = new Random();

    public static Pasager genereazaPasagerRandom() {
        // extragem un index aleatoriu intre 0 si lungimea vectorului
        String nume = NUME[random.nextInt(NUME.length)];
        String prenume = PRENUME[random.nextInt(PRENUME.length)];

        // generam un cnp fictiv (7 cifre random dupa 1 sau 2)
        String cnp = (random.nextBoolean() ? "1" : "2") + (random.nextInt(9000000) + 1000000);

        // generam un pasaport fictiv (P + 5 cifre)
        String pasaport = "P" + (random.nextInt(90000) + 10000);

        return new Pasager(cnp, nume, prenume, pasaport);
    }

    public static Bilet genereazaBiletRandom() {
        // 30% sansa sa fie business, 70% sansa sa fie economy
        boolean isBusiness = random.nextInt(100) < 30;

        // generam un id de bilet random
        String idBilet = "TKT" + random.nextInt(1000);

        if (isBusiness) {
            return new BiletBusiness(idBilet, 300.0, 100.0);
        } else {
            // taxa de bagaj aleatorie intre 0 si 50 euro
            double taxaBagaj = random.nextInt(51);
            return new BiletEconomy(idBilet, 300.0, taxaBagaj);
        }
    }
}