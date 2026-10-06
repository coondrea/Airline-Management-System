package services;

import config.DatabaseConnection;
import models.Aeroport;
import models.Avion;
import models.Zbor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ZborRepository implements CrudRepository<Zbor, String> {

    // 1. instanta unica singleton
    private static ZborRepository instance;
    private final Connection connection;

    // 2. constructor privat
    private ZborRepository() {
        this.connection = DatabaseConnection.getInstance();
    }

    // 3. metoda de acces la instanta
    public static ZborRepository getInstance() {
        if (instance == null) {
            instance = new ZborRepository();
        }
        return instance;
    }

    @Override
    public void create(Zbor zbor) {
        String sql = "INSERT INTO ZBOR (numar_zbor, cod_plecare, cod_destinatie, id_avion, ora_plecare, locuri_ocupate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, zbor.getNumarZbor());
            // extragem doar id-urile pentru cheile straine
            statement.setString(2, zbor.getPlecare().getCodIATA());
            statement.setString(3, zbor.getDestinatie().getCodIATA());
            statement.setString(4, zbor.getAvion().getIdAvion());
            // transformam localdatetime din java in timestamp pentru postgresql
            statement.setTimestamp(5, Timestamp.valueOf(zbor.getOraPlecare()));
            statement.setInt(6, zbor.getLocuriOcupate());

            statement.executeUpdate();
            System.out.println("zbor inserat in db: " + zbor.getNumarZbor());
        } catch (SQLException e) {
            System.out.println("eroare la creare zbor: " + e.getMessage());
        }
    }

    @Override
    public Zbor read(String id) {
        String sql = "SELECT * FROM ZBOR WHERE numar_zbor = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                // citim cheile straine din tabel
                String codPlecare = rs.getString("cod_plecare");
                String codDestinatie = rs.getString("cod_destinatie");
                String idAvion = rs.getString("id_avion");

                // apelam celelalte singleton-uri pentru a aduce obiectele intregi
                Aeroport plecare = AeroportRepository.getInstance().read(codPlecare);
                Aeroport destinatie = AeroportRepository.getInstance().read(codDestinatie);
                Avion avion = AvionRepository.getInstance().read(idAvion);

                // transformam timestamp inapoi in localdatetime
                LocalDateTime oraPlecare = rs.getTimestamp("ora_plecare").toLocalDateTime();

                // construim si returnam obiectul final
                Zbor zbor = new Zbor(rs.getString("numar_zbor"), plecare, destinatie, avion, oraPlecare);
                zbor.setLocuriOcupate(rs.getInt("locuri_ocupate"));
                return zbor;
            }
        } catch (SQLException e) {
            System.out.println("eroare citire zbor: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Zbor zbor) {
        String sql = "UPDATE ZBOR SET cod_plecare = ?, cod_destinatie = ?, id_avion = ?, ora_plecare = ?, locuri_ocupate = ? WHERE numar_zbor = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, zbor.getPlecare().getCodIATA());
            statement.setString(2, zbor.getDestinatie().getCodIATA());
            statement.setString(3, zbor.getAvion().getIdAvion());
            statement.setTimestamp(4, Timestamp.valueOf(zbor.getOraPlecare()));
            statement.setInt(5, zbor.getLocuriOcupate());
            statement.setString(6, zbor.getNumarZbor());

            statement.executeUpdate();
            System.out.println("zbor actualizat in db: " + zbor.getNumarZbor());
        } catch (SQLException e) {
            System.out.println("eroare update zbor: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM ZBOR WHERE numar_zbor = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
            System.out.println("zbor sters din db: " + id);
        } catch (SQLException e) {
            System.out.println("eroare stergere zbor: " + e.getMessage());
        }
    }

    @Override
    public List<Zbor> readAll() {
        List<Zbor> zboruri = new ArrayList<>();
        String sql = "SELECT * FROM ZBOR";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Aeroport plecare = AeroportRepository.getInstance().read(rs.getString("cod_plecare"));
                Aeroport destinatie = AeroportRepository.getInstance().read(rs.getString("cod_destinatie"));
                Avion avion = AvionRepository.getInstance().read(rs.getString("id_avion"));
                LocalDateTime oraPlecare = rs.getTimestamp("ora_plecare").toLocalDateTime();

                Zbor zbor = new Zbor(rs.getString("numar_zbor"), plecare, destinatie, avion, oraPlecare);
                zbor.setLocuriOcupate(rs.getInt("locuri_ocupate"));
                zboruri.add(zbor);
            }
        } catch (SQLException e) {
            System.out.println("eroare citire toate zborurile: " + e.getMessage());
        }
        return zboruri;
    }
}