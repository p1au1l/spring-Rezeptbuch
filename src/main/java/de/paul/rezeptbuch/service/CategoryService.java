package de.paul.rezeptbuch.service;

import de.paul.rezeptbuch.model.Category;
import de.paul.rezeptbuch.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service für Kategorien.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * @param categoryRepository Repository für Kategorien
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Speichert eine neue Kategorie.
     *
     * @param category Kategorie aus dem Formular
     * @return gespeicherte Kategorie
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Lädt eine Kategorie über ihre ID.
     *
     * @param id Kategorie-ID
     * @return gefundene Kategorie
     * @throws EntityNotFoundException wenn keine Kategorie zur ID existiert
     */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    /**
     * @return alle vorhandenen Kategorien
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Aktualisiert eine bestehende Kategorie.
     *
     * @param id Kategorie-ID
     * @param categoryDetails neue Werte
     * @return aktualisierte Kategorie
     * @throws EntityNotFoundException wenn keine Kategorie zur ID existiert
     */
    public Category updateCategory(Long id, Category categoryDetails) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        existingCategory.setName(categoryDetails.getName());
        existingCategory.setDescription(categoryDetails.getDescription());

        return categoryRepository.save(existingCategory);
    }

    /**
     * Löscht eine Kategorie.
     *
     * @param id Kategorie-ID
     * @throws EntityNotFoundException wenn keine Kategorie zur ID existiert
     */
    public void deleteCategoryById(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(existingCategory);
    }
}

