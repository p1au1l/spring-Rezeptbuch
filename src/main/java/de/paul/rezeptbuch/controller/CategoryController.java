package de.paul.rezeptbuch.controller;

import de.paul.rezeptbuch.model.Category;
import de.paul.rezeptbuch.service.CategoryService;
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

/**
 * Controller für alle Kategorie-Seiten.
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * @param categoryService Service für Kategorien
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Zeigt die Kategorienliste.
     *
     * @param model Model für die View
     * @return Template-Name
     */
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories/list";
    }

    /**
     * Zeigt die Detailseite einer Kategorie.
     *
     * @param id Kategorie-ID
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Template-Name oder Redirect
     */
    @GetMapping("/{id}")
    public String showCategory(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("category", categoryService.getCategoryById(id));
            return "categories/detail";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kategorie wurde nicht gefunden.");
            return "redirect:/categories";
        }
    }

    /**
     * Zeigt das Formular für eine neue Kategorie.
     *
     * @param model Model für die View
     * @return Template-Name
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("isEdit", false);
        return "categories/form";
    }

    /**
     * Speichert eine neue Kategorie.
     *
     * @param category Formulardaten
     * @param bindingResult Ergebnis der Validierung
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Redirect oder Formular bei Fehlern
     */
    @PostMapping("/new")
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult,
                                 Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "categories/form";
        }
        categoryService.createCategory(category);
        redirectAttributes.addFlashAttribute("successMessage", "Kategorie wurde erfolgreich angelegt.");
        return "redirect:/categories";
    }

    /**
     * Zeigt das Bearbeitungsformular.
     *
     * @param id Kategorie-ID
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Template-Name oder Redirect
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("category", categoryService.getCategoryById(id));
            model.addAttribute("isEdit", true);
            return "categories/form";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kategorie wurde nicht gefunden.");
            return "redirect:/categories";
        }
    }

    /**
     * Aktualisiert eine Kategorie.
     *
     * @param id Kategorie-ID
     * @param category Formulardaten
     * @param bindingResult Ergebnis der Validierung
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Redirect oder Formular bei Fehlern
     */
    @PostMapping("/{id}/edit")
    public String updateCategory(@PathVariable Long id, @Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "categories/form";
        }
        try {
            categoryService.updateCategory(id, category);
            redirectAttributes.addFlashAttribute("successMessage", "Kategorie wurde erfolgreich gespeichert.");
            return "redirect:/categories/" + id;
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kategorie wurde nicht gefunden.");
            return "redirect:/categories";
        }
    }

    /**
     * Zeigt die Sicherheitsabfrage vor dem Löschen.
     *
     * @param id Kategorie-ID
     * @param model Model für die View
     * @param redirectAttributes Meldungen für Redirects
     * @return Template-Name oder Redirect
     */
    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("category", categoryService.getCategoryById(id));
            return "categories/delete";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kategorie wurde nicht gefunden.");
            return "redirect:/categories";
        }
    }

    /**
     * Löscht eine Kategorie nach Bestätigung.
     *
     * @param id Kategorie-ID
     * @param redirectAttributes Meldungen für Redirects
     * @return Redirect zur Liste
     */
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Kategorie wurde erfolgreich gelöscht.");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kategorie wurde nicht gefunden.");
        }
        return "redirect:/categories";
    }
}

