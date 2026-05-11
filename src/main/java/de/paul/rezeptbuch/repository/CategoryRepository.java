package de.paul.rezeptbuch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.paul.rezeptbuch.model.Category;

/**
 * Spring Data JPA Repository für die Entity-Klasse Category.
 * Bietet Standard-CRUD-Operationen (Create, Read, Update, Delete) und Datenbankoperationen
 * für Rezeptkategorien.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}