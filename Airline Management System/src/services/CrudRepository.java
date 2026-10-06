package services;

import java.util.List;

// t reprezinta tipul de obiect (ex: aeroport, avion)
// id reprezinta tipul cheii primare din baza de date (ex: string pt cod_iata)
public interface CrudRepository<T, ID> {
    void create(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
    List<T> readAll();
}