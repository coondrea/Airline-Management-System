-- PostgreSQL schema for the Airline Management System
-- Column names match the queries in src/services/*Repository.java

CREATE TABLE IF NOT EXISTS aeroport (
    cod_iata VARCHAR(3)   PRIMARY KEY,
    nume     VARCHAR(100) NOT NULL,
    oras     VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS avion (
    id_avion          VARCHAR(20)  PRIMARY KEY,
    model             VARCHAR(100) NOT NULL,
    capacitate_maxima INT          NOT NULL CHECK (capacitate_maxima > 0)
);

CREATE TABLE IF NOT EXISTS pasager (
    cnp            VARCHAR(13) PRIMARY KEY,
    nume           VARCHAR(50) NOT NULL,
    prenume        VARCHAR(50) NOT NULL,
    numar_pasaport VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS zbor (
    numar_zbor     VARCHAR(10) PRIMARY KEY,
    cod_plecare    VARCHAR(3)  NOT NULL REFERENCES aeroport(cod_iata),
    cod_destinatie VARCHAR(3)  NOT NULL REFERENCES aeroport(cod_iata),
    id_avion       VARCHAR(20) NOT NULL REFERENCES avion(id_avion),
    ora_plecare    TIMESTAMP   NOT NULL,
    locuri_ocupate INT         NOT NULL DEFAULT 0 CHECK (locuri_ocupate >= 0)
);
