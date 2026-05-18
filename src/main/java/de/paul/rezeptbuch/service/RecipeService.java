package de.paul.rezeptbuch.service;

import de.paul.rezeptbuch.model.Recipe;
import de.paul.rezeptbuch.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service für Rezepte.
 */
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    /**
     * @param recipeRepository Repository für Rezepte
     */
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Speichert ein neues Rezept.
     *
     * @param recipe Rezept aus dem Formular
     * @return gespeichertes Rezept
     */
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    /**
     * Lädt ein Rezept über seine ID.
     *
     * @param id Rezept-ID
     * @return gefundenes Rezept
     * @throws EntityNotFoundException wenn kein Rezept zur ID existiert
     */
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));
    }

    /**
     * @return alle vorhandenen Rezepte
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Aktualisiert ein bestehendes Rezept.
     *
     * @param id Rezept-ID
     * @param recipeDetails neue Werte
     * @return aktualisiertes Rezept
     * @throws EntityNotFoundException wenn kein Rezept zur ID existiert
     */
    public Recipe updateRecipe(Long id, Recipe recipeDetails) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));

        existingRecipe.setTitle(recipeDetails.getTitle());
        existingRecipe.setDescription(recipeDetails.getDescription());
        existingRecipe.setSourceUrl(recipeDetails.getSourceUrl());
        existingRecipe.setNotes(recipeDetails.getNotes());
        existingRecipe.setCookTimeMinutes(recipeDetails.getCookTimeMinutes());
        existingRecipe.setRating(recipeDetails.getRating());
        existingRecipe.setCategory(recipeDetails.getCategory());

        return recipeRepository.save(existingRecipe);
    }

    /**
     * Löscht ein Rezept.
     *
     * @param id Rezept-ID
     * @throws EntityNotFoundException wenn kein Rezept zur ID existiert
     */
    public void deleteRecipeById(Long id) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));
        recipeRepository.delete(existingRecipe);
    }
}

