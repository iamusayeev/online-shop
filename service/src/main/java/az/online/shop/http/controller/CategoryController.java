package az.online.shop.http.controller;

import az.online.shop.dto.CategoryDTO;
import az.online.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "category/category";
    }

    @PostMapping
    public String create(Model model, CategoryDTO categoryDto) {
        model.addAttribute("category", categoryDto);
        categoryService.create(categoryDto);
        return "redirect:/categories";
    }
}