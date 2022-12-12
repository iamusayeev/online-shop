package az.online.shop.http.controller;

import az.online.shop.dto.ProductCreateDTO;
import az.online.shop.service.ProductService;
import java.security.Principal;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String getAll(Model model) {
        var products = productService.getAll();
        model.addAttribute("products", products);
        return "product/products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Integer id, Principal principal) {
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addToUserBucket(Collections.singletonList(id), principal.getName());
        return "redirect:/products";
    }

    @PostMapping
    public String add(Model model, ProductCreateDTO productDTO) {
        model.addAttribute("product", productDTO);
        productService.addProduct(productDTO);
        return "redirect:/products";
    }
}