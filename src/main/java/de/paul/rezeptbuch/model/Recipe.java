package de.paul.rezeptbuch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Rezept mit allen relevanten Angaben und einer Kategorie.
 */
@Entity
@Table(name = "recipes")
public class Recipe {

    /**
     * Primärschlüssel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rezepttitel.
     */
    @NotBlank(message = "Bitte geben Sie einen Rezepttitel ein.")
    private String title;

    /**
     * Beschreibung, z. B. Zutaten und Ablauf.
     */
    private String description;

    /**
     * Link zur Quelle.
     */
    private String sourceUrl;

    /**
     * Eigene Notizen zum Rezept.
     */
    private String notes;

    /**
     * Kochzeit in Minuten.
     */
    private int cookTimeMinutes;

    /**
     * Bewertung von 1 bis 5.
     */
    @NotNull(message = "Bitte geben Sie eine Bewertung an.")
    @Min(value = 1, message = "Die Bewertung muss zwischen 1 und 5 liegen.")
    @Max(value = 5, message = "Die Bewertung muss zwischen 1 und 5 liegen.")
    private Integer rating;

    /**
     * Zugehörige Kategorie.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Bitte wählen Sie eine Kategorie aus.")
    private Category category;

    /**
     * Standardkonstruktor für JPA.
     */
    public Recipe() {
    }

    /**
     * Erstellt ein Rezept ohne ID.
     *
     * @param title Titel
     * @param description Beschreibung
     * @param sourceUrl Quell-URL
     * @param notes Notizen
     * @param cookTimeMinutes Kochzeit in Minuten
     * @param rating Bewertung (1-5)
     * @param category Kategorie
     */
    public Recipe(String title, String description, String sourceUrl, String notes,
                  int cookTimeMinutes, Integer rating, Category category) {
        this.title = title;
        this.description = description;
        this.sourceUrl = sourceUrl;
        this.notes = notes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.rating = rating;
        this.category = category;
    }

    /**
     * Gibt die ID des Rezepts zurück.
     *
     * @return die eindeutige ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Setzt die ID des Rezepts.
     *
     * @param id die neue ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gibt den Titel des Rezepts zurück.
     *
     * @return der Titel
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setzt den Titel des Rezepts.
     *
     * @param title der neue Titel
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gibt die Beschreibung des Rezepts zurück.
     *
     * @return die Beschreibung
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung des Rezepts.
     *
     * @param description die neue Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gibt die Source-URL des Rezepts zurück.
     *
     * @return die URL der Quelle
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * Setzt die Source-URL des Rezepts.
     *
     * @param sourceUrl die neue URL
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    /**
     * Gibt die zusätzlichen Notizen zum Rezept zurück.
     *
     * @return die Notizen
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Setzt die zusätzlichen Notizen zum Rezept.
     *
     * @param notes die neuen Notizen
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gibt die Zubereitungszeit des Rezepts zurück.
     *
     * @return die Zeit in Minuten
     */
    public int getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    /**
     * Setzt die Zubereitungszeit des Rezepts.
     *
     * @param cookTimeMinutes die neue Zeit in Minuten
     */
    public void setCookTimeMinutes(int cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    /**
     * Gibt die Bewertung des Rezepts zurück.
     *
     * @return die Bewertung (1-5)
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Setzt die Bewertung des Rezepts.
     *
     * @param rating die neue Bewertung (1-5)
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Gibt die Kategorie des Rezepts zurück.
     *
     * @return die zugehörige Kategorie
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Setzt die Kategorie des Rezepts.
     *
     * @param category die neue Kategorie
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}