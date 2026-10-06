package services;

import config.DatabaseConnection;
import models.Pasager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasagerRepository implements CrudRepository<Pasager, String> {

    private static PasagerRepository instance;
    private final Connection connection;

    private PasagerRepository() {
        this.connection = DatabaseConnection.getInstance();
    }

    public static PasagerRepository getInstance() {
        if (instance == null) {
            instance = new PasagerRepository();
        }
        return instance;
    }

    @Override
    public void create(Pasager pasager) {
        String sql = "INSERT INTO PASAGER (cnp, nume, prenume, numar_pasaport) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pasager.getCnp());
            statement.setString(2, pasager.getNume());
            statement.setString(3, pasager.getPrenume());
            statement.setString(4, pasager.getNumarPasaport());
            statement.executeUpdate();
            System.out.println("pasager inserat in db: " + pasager.getNume());
        } catch (SQLException e) {
            System.out.println("eroare creare pasager: " + e.getMessage());
        }
    }

    @Override
    public Pasager read(String id) {
        String sql = "SELECT * FROM PASAGER WHERE cnp = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Pasager(rs.getString("cnp"), rs.getString("nume"), rs.getString("prenume"), rs.getString("numar_pasaport"));
            }
        } catch (SQLException e) {
            System.out.println("eroare citire pasager: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Pasager pasager) {
        String sql = "UPDATE PASAGER SET nume = ?, prenume = ?, numar_pasaport = ? WHERE cnp = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pasager.getNume());
            statement.setString(2, pasager.getPrenume());
            statement.setString(3, pasager.getNumarPasaport());
            statement.setString(4, pasager.getCnp());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("eroare la update pasager: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM PASAGER WHERE cnp = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("eroare la stergere pasager: " + e.getMessage());
        }
    }

    @Override
    public List<Pasager> readAll() {
        List<Pasager> pasageri = new ArrayList<>();
        String sql = "SELECT * FROM PASAGER";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                pasageri.add(new Pasager(rs.getString("cnp"), rs.getString("nume"), rs.getString("prenume"), rs.getString("numar_pasaport")));
            }
        } catch (SQLException e) {
            System.out.println("eroare la citire toti pasagerii: " + e.getMessage());
        }
        return pasageri;
    }
}