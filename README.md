# Rezeptbuch (Spring Boot + Thymeleaf + H2)

Webanwendung zum Verwalten eines persönlichen Rezeptbuchs. Rezepte können inklusive Titel, Beschreibung, Notiz, Kochzeit, Bewertung und Quell-URL gespeichert und Kategorien zugeordnet werden.

## Features 
- **Recipes**: anlegen, anzeigen, bearbeiten, löschen (CRUD)
- **Categories**: anlegen, anzeigen, bearbeiten, löschen (CRUD)
- Zuordnung: jedes Recipe gehört zu einer Category

## Tech-Stack
- Java + Maven (Start über **Maven Wrapper**)
- Spring Boot
- Thymeleaf (Server-Side Rendering)
- Spring Data JPA (Persistenz)
- H2 Database (**Persistenz**, nicht In-Memory)

## Datenmodell
Es gibt mindestens zwei Tabellen (normalisiert):

1. `Recipe`
   - Felder (fachlich): `title`, `description`, `sourceUrl`, `notes`, `cookTimeMinutes`, `rating`, `category_id`
2. `Category`
   - Felder (fachlich): `name`, `description`, `imageUrl`

## Assignment-Kontext 
- Start via `mvnw spring-boot:run`
- App auf `http://localhost:8080`
- H2 Console auf `http://localhost:8080/h2-console/`
- Persistenz auf Festplatte
- 2 Entities mit vollständigem CRUD



.\mvnw.cmd spring-boot:run
