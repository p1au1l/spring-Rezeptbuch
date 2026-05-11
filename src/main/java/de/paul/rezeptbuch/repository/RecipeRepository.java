package de.paul.rezeptbuch.repository;

import de.paul.rezeptbuch.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository für die Entity-Klasse Recipe.
 * Bietet Standard-CRUD-Operationen (Create, Read, Update, Delete) und Datenbankoperationen
 * für Rezepte.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}