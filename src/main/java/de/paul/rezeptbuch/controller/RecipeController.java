package de.paul.rezeptbuch.controller;

import de.paul.rezeptbuch.model.Category;
import de.paul.rezeptbuch.model.Recipe;
import de.paul.rezeptbuch.service.CategoryService;
import de.paul.rezeptbuch.service.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller für alle Rezept-Seiten.
 */
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    /**
     * @param recipeService Service für Rezepte
     * @param categoryService Service für Kategorien
     */
    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    /**
     * Zeigt die Rezeptliste.
     *
     * @param model Model für die View
     * @return Template-Name
     */
    @GetMapping
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes/list";
    }

    /**
     * Zeigt die Detailseite eines Rezepts.
     *
     * @param id Rezept-ID
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Template-Name oder Redirect
     */
    @GetMapping("/{id}")
    public String showRecipe(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("recipe", recipeService.getRecipeById(id));
            return "recipes/detail";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Rezept wurde nicht gefunden.");
            return "redirect:/recipes";
        }
    }

    /**
     * Zeigt das Formular für ein neues Rezept.
     *
     * @param model Model für die View
     * @return Template-Name
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Recipe recipe = new Recipe();
        recipe.setCategory(new Category());
        model.addAttribute("recipe", recipe);
        model.addAttribute("isEdit", false);
        addCategoryOptions(model);
        return "recipes/form";
    }

    /**
     * Speichert ein neues Rezept.
     *
     * @param recipe Formulardaten
     * @param bindingResult Ergebnis der Validierung
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Redirect oder Formular bei Fehlern
     */
    @PostMapping("/new")
    public String createRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        ensureCategoryObject(recipe);
        if (!isValidCategorySelection(recipe, bindingResult)) {
            model.addAttribute("isEdit", false);
            addCategoryOptions(model);
            return "recipes/form";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            addCategoryOptions(model);
            return "recipes/form";
        }
        recipeService.createRecipe(recipe);
        redirectAttributes.addFlashAttribute("successMessage", "Rezept wurde erfolgreich angelegt.");
        return "redirect:/recipes";
    }

    /**
     * Zeigt das Bearbeitungsformular.
     *
     * @param id Rezept-ID
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Template-Name oder Redirect
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("recipe", recipeService.getRecipeById(id));
            model.addAttribute("isEdit", true);
            addCategoryOptions(model);
            return "recipes/form";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Rezept wurde nicht gefunden.");
            return "redirect:/recipes";
        }
    }

    /**
     * Aktualisiert ein Rezept.
     *
     * @param id Rezept-ID
     * @param recipe Formulardaten
     * @param bindingResult Ergebnis der Validierung
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Redirect oder Formular bei Fehlern
     */
    @PostMapping("/{id}/edit")
    public String updateRecipe(@PathVariable Long id, @Valid @ModelAttribute("recipe") Recipe recipe,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        ensureCategoryObject(recipe);
        if (!isValidCategorySelection(recipe, bindingResult)) {
            model.addAttribute("isEdit", true);
            addCategoryOptions(model);
            return "recipes/form";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            addCategoryOptions(model);
            return "recipes/form";
        }
        try {
            recipeService.updateRecipe(id, recipe);
            redirectAttributes.addFlashAttribute("successMessage", "Rezept wurde erfolgreich gespeichert.");
            return "redirect:/recipes/" + id;
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Rezept wurde nicht gefunden.");
            return "redirect:/recipes";
        }
    }

    /**
     * Zeigt die Sicherheitsabfrage vor dem Löschen.
     *
     * @param id Rezept-ID
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Template-Name oder Redirect
     */
    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("recipe", recipeService.getRecipeById(id));
            return "recipes/delete";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Rezept wurde nicht gefunden.");
            return "redirect:/recipes";
        }
    }

    /**
     * Löscht ein Rezept nach Bestätigung.
     *
     * @param id Rezept-ID
     * @param redirectAttributes Meldungen für Redirects
     * @return Redirect zur Liste
     */
    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            recipeService.deleteRecipeById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Rezept wurde erfolgreich gelöscht.");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Rezept wurde nicht gefunden.");
        }
        return "redirect:/recipes";
    }

    /**
     * Legt alle Kategorien für das Auswahlfeld ins Model.
     *
     * @param model Model für die View
     */
    private void addCategoryOptions(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
    }

    /**
     * Sichert eine stabile Formularbindung für category.id.
     *
     * @param recipe Rezept aus dem Formular
     */
    private void ensureCategoryObject(Recipe recipe) {
        if (recipe.getCategory() == null) {
            recipe.setCategory(new Category());
        }
    }

    /**
     * Prüft die Kategorieauswahl und lädt die echte Kategorie aus der DB.
     *
     * @param recipe zu validierendes Rezept
     * @param bindingResult Ergebnis der Validierung
     * @return true bei gültiger Auswahl, sonst false
     */
    private boolean isValidCategorySelection(Recipe recipe, BindingResult bindingResult) {
        if (recipe.getCategory() == null || recipe.getCategory().getId() == null) {
            bindingResult.rejectValue("category", "NotNull", "Bitte wählen Sie eine Kategorie aus.");
            return false;
        }
        try {
            Category category = categoryService.getCategoryById(recipe.getCategory().getId());
            recipe.setCategory(category);
            return true;
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("category", "NotFound", "Die ausgewählte Kategorie existiert nicht.");
            return false;
        }
    }
}

