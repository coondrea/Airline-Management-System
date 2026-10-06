package services;

import config.DatabaseConnection;
import models.Aeroport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AeroportRepository implements CrudRepository<Aeroport, String> {

    // 1. instanta unica (singleton)
    private static AeroportRepository instance;
    private final Connection connection;

    // 2. constructor privat
    private AeroportRepository() {
        this.connection = DatabaseConnection.getInstance();
    }

    // 3. metoda de acces la instanta
    public static AeroportRepository getInstance() {
        if (instance == null) {
            instance = new AeroportRepository();
        }
        return instance;
    }

    @Override
    public void create(Aeroport aeroport) {
        // semnele de intrebare sunt 'placeholdere' pe care le completam din codul java
        String sql = "INSERT INTO AEROPORT (cod_iata, nume, oras) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, aeroport.getCodIATA());
            statement.setString(2, aeroport.getNume());
            statement.setString(3, aeroport.getOras());
            statement.executeUpdate(); // executa operatia in postgresql
            System.out.println("aeroport inserat in db: " + aeroport.getCodIATA());
        } catch (SQLException e) {
            System.out.println("eroare la creare aeroport: " + e.getMessage());
        }
    }

    @Override
    public Aeroport read(String id) {
        String sql = "SELECT * FROM AEROPORT WHERE cod_iata = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery(); // citeste din postgresql

            // daca gaseste un rand, extragem informatiile si cream obiectul
            if (resultSet.next()) {
                return new Aeroport(
                        resultSet.getString("cod_iata"),
                        resultSet.getString("nume"),
                        resultSet.getString("oras")
                );
            }
        } catch (SQLException e) {
            System.out.println("eroare la citire aeroport: " + e.getMessage());
        }
        return null; // returnam null daca nu s-a gasit aeroportul
    }

    @Override
    public void update(Aeroport aeroport) {
        String sql = "UPDATE AEROPORT SET nume = ?, oras = ? WHERE cod_iata = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, aeroport.getNume());
            statement.setString(2, aeroport.getOras());
            statement.setString(3, aeroport.getCodIATA());
            statement.executeUpdate();
            System.out.println("aeroport actualizat in db: " + aeroport.getCodIATA());
        } catch (SQLException e) {
            System.out.println("eroare la update aeroport: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM AEROPORT WHERE cod_iata = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
            System.out.println("aeroport sters din db: " + id);
        } catch (SQLException e) {
            System.out.println("eroare la stergere aeroport: " + e.getMessage());
        }
    }

    @Override
    public List<Aeroport> readAll() {
        List<Aeroport> aeroporturi = new ArrayList<>();
        String sql = "SELECT * FROM AEROPORT";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // parcurgem toate randurile primite de la baza de date
            while (resultSet.next()) {
                aeroporturi.add(new Aeroport(
                        resultSet.getString("cod_iata"),
                        resultSet.getString("nume"),
                        resultSet.getString("oras")
                ));
            }
        } catch (SQLException e) {
            System.out.println("eroare la citire toate aeroporturile: " + e.getMessage());
        }
        return aeroporturi;
    }
}