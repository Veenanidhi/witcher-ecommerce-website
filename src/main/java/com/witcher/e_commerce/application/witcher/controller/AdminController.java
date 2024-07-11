package com.witcher.e_commerce.application.witcher.controller;

/*
import com.witcher.e_commerce.application.witcher.entity.Category;
import com.witcher.e_commerce.application.witcher.entity.Product;
import com.witcher.e_commerce.application.witcher.service.CategoryService;
import com.witcher.e_commerce.application.witcher.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @Autowired
    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String adminHome(){
        return "admin";
    }

    @GetMapping("/categories")
    public String categoriesPage(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/categories/add")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String postCategory(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        categoryService.removeCategoryById(id);

        return "redirect:admin/categories";

    }

    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model){
        Optional<Category> category= categoryService.getCategoryById(id);
        if (category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }
        else {
            return "not found";
        }

    }

    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("products",productService.getAllProduct());
        return "products";
    }

    @GetMapping("/products/add")
    public String addProducts(Model model){
        model.addAttribute("products", new Product());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/products/add")
     public String productAdd(@ModelAttribute("product")Product product,
                              @RequestParam("productImage")MultipartFile file,
                              @RequestParam("imgName")String imgName)throws IOException {


        Product product1= new Product();

        product1.setId(product.getId());
        product1.setCategory(String.valueOf(categoryService.getCategoryById(Integer.parseInt(product.getCategory_id())).get()));



        return "redirect:/products";

   }



}

*/

























