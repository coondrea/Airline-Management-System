package services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {

    // 1. instanta unica singleton
    private static AuditService instance;

    // 2. calea catre fisierul in care vom salva actiunile
    private final String filepath = "audit.csv";

    // 3. formatul pentru data si ora (ex: 2026-06-08 22:45:30)
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 4. constructor privat pentru a bloca instantierea multipla
    private AuditService() {
    }

    // 5. metoda de acces la instanta
    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    // 6. metoda principala care scrie in fisier
    public void logAction(String actionName) {
        // folosim filewriter cu parametrul 'true' pentru a adauga text la finalul fisierului (append)
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.println(actionName + "," + timestamp);
        } catch (IOException e) {
            System.out.println("eroare la scrierea in fisierul de audit: " + e.getMessage());
        }
    }
}