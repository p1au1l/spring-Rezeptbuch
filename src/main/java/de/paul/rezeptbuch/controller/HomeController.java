package de.paul.rezeptbuch.controller;

import de.paul.rezeptbuch.service.CategoryService;
import de.paul.rezeptbuch.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller für die Startseite.
 */
@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final RecipeService recipeService;

    /**
     * @param categoryService Service für Kategorien
     * @param recipeService Service für Rezepte
     */
    public HomeController(CategoryService categoryService, RecipeService recipeService) {
        this.categoryService = categoryService;
        this.recipeService = recipeService;
    }

    /**
     * Zeigt die Startseite mit den wichtigsten Zahlen.
     *
     * @param model Model für die View
     * @return Template-Name
     */
    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("categoryCount", categoryService.getAllCategories().size());
        model.addAttribute("recipeCount", recipeService.getAllRecipes().size());
        return "index";
    }
}

