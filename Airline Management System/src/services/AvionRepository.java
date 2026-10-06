package services;

import config.DatabaseConnection;
import models.Avion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvionRepository implements CrudRepository<Avion, String> {

    private static AvionRepository instance;
    private final Connection connection;

    private AvionRepository() {
        this.connection = DatabaseConnection.getInstance();
    }

    public static AvionRepository getInstance() {
        if (instance == null) {
            instance = new AvionRepository();
        }
        return instance;
    }

    @Override
    public void create(Avion avion) {
        String sql = "INSERT INTO AVION (id_avion, model, capacitate_maxima) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, avion.getIdAvion());
            statement.setString(2, avion.getModel());
            statement.setInt(3, avion.getCapacitateMaxima());
            statement.executeUpdate();
            System.out.println("avion inserat in db: " + avion.getIdAvion());
        } catch (SQLException e) {
            System.out.println("eroare la creare avion: " + e.getMessage());
        }
    }

    @Override
    public Avion read(String id) {
        String sql = "SELECT * FROM AVION WHERE id_avion = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Avion(rs.getString("id_avion"), rs.getString("model"), rs.getInt("capacitate_maxima"));
            }
        } catch (SQLException e) {
            System.out.println("eroare citire avion: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Avion avion) {
        String sql = "UPDATE AVION SET model = ?, capacitate_maxima = ? WHERE id_avion = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, avion.getModel());
            statement.setInt(2, avion.getCapacitateMaxima());
            statement.setString(3, avion.getIdAvion());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("eroare la update avion: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM AVION WHERE id_avion = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("eroare la stergere avion: " + e.getMessage());
        }
    }

    @Override
    public List<Avion> readAll() {
        List<Avion> avioane = new ArrayList<>();
        String sql = "SELECT * FROM AVION";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                avioane.add(new Avion(rs.getString("id_avion"), rs.getString("model"), rs.getInt("capacitate_maxima")));
            }
        } catch (SQLException e) {
            System.out.println("eroare la citire toate avioanele: " + e.getMessage());
        }
        return avioane;
    }
}