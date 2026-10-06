<div align="center">
  <h1>✈️ Airline Management System</h1>
  <p>A Java console application for managing airports, aircraft, flights, passengers and ticket sales, with PostgreSQL persistence via JDBC.</p>
</div>

---

## 📌 Short Description
Built for the **Advanced Object-Oriented Programming (PAO)** course, this project models the core of an airline's operations: flights between airports, aircraft capacity, ticket sales with different fare classes, and an audit trail of every action performed in the system.

## 💻 Tech Stack
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-42.7-blue?style=for-the-badge)

## ✨ Features & OOP Concepts

* **Inheritance & abstraction:** abstract `Persoana` → `Pasager` / `Angajat`; abstract `Bilet` → `BiletEconomy` / `BiletBusiness`, each with its own `calculeazaPretFinal()` (polymorphism).
* **Collections:** `HashMap` for fast flight lookup, `ArrayList` for sold tickets, and a `TreeSet` with a custom `Comparator` that keeps passengers sorted by name.
* **Generic CRUD interface:** `CrudRepository<T, ID>` implemented by `AeroportRepository`, `AvionRepository`, `PasagerRepository` and `ZborRepository`.
* **Singleton pattern:** database connection, repositories and the audit service.
* **JDBC with `PreparedStatement`:** parameterized queries (no SQL injection), foreign-key aware reads of `Zbor` (re-assembles airports and aircraft).
* **Audit service:** every business action is timestamped and appended to `audit.csv`.
* **Random data generator:** `GeneratorDate` produces passengers and tickets to simulate sales; flights stop selling once the aircraft is full.

## 🗂️ Project Structure
```
Airline Management System/src/
├── Main.java                 # demo scenario
├── config/                   # DatabaseConnection (singleton, env-based config)
├── models/                   # Persoana, Pasager, Angajat, Bilet*, Aeroport, Avion, Zbor
├── services/                 # AirlineService, AuditService, *Repository (JDBC)
└── utils/                    # GeneratorDate
schema.sql                    # PostgreSQL tables
```

## 🚀 Setup & Execution

**Requirements:** JDK 17+, PostgreSQL, and the [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download/) (`postgresql-42.x.jar`).

**1. Create the tables**
```bash
psql -U postgres -d postgres -f schema.sql
```

**2. Configure the connection** via environment variables (nothing is hardcoded):
```bash
export DB_URL="jdbc:postgresql://localhost:5432/postgres"   # optional, this is the default
export DB_USER="postgres"                                   # optional, this is the default
export DB_PASSWORD="your_password"
```

**3. Compile and run** (use `;` instead of `:` as classpath separator on Windows)
```bash
cd "Airline Management System"
javac -cp postgresql-42.x.jar -d out $(find src -name "*.java")
java -cp out:postgresql-42.x.jar Main
```

Or open the folder in IntelliJ IDEA and add the JDBC jar as a library.

---
<div align="center">Author: <b>Condrea Ion-Luca</b></div>
