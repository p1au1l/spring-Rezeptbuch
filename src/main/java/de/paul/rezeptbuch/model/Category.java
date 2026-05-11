package de.paul.rezeptbuch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Kategorie für Rezepte.
 */
@Entity
@Table(name = "categories")
public class Category {

    /**
     * Primärschlüssel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Anzeigename der Kategorie.
     */
    @NotBlank(message = "Bitte geben Sie einen Kategorienamen ein.")
    private String name;

    /**
     * Freitext-Beschreibung.
     */
    private String description;


    /**
     * Standardkonstruktor für JPA.
     */
    public Category() {
    }

    /**
     * Erstellt eine Kategorie ohne ID.
     *
     * @param name Name der Kategorie
     * @param description Beschreibung
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gibt die ID der Kategorie zurück.
     *
     * @return die eindeutige ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Setzt die ID der Kategorie.
     *
     * @param id die neue ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gibt den Namen der Kategorie zurück.
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Kategorie.
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Beschreibung der Kategorie zurück.
     *
     * @return die Beschreibung
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung der Kategorie.
     *
     * @param description die neue Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
    }

}