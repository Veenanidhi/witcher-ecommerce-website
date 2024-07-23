package com.witcher.e_commerce.application.witcher.controller;


import com.witcher.e_commerce.application.witcher.entity.Category;
import com.witcher.e_commerce.application.witcher.entity.Product;
import com.witcher.e_commerce.application.witcher.service.category.CategoryService;
import com.witcher.e_commerce.application.witcher.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;

    private final ProductService productService;

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImage";
    @Autowired
    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String adminHome(){
        return "admin-page";
    }

    @GetMapping("/categories")
    public String showCategory(Model model){
        model.addAttribute("categories", categoryService.findAll());
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
        return "redirect:/admin-page";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return "redirect:/categories";
    }

    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable Long id,Model model){
        Optional<Category> category=categoryService.getCategoryById(id);

        if (category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }else
            return "404";

    }

    //Product Section
    @GetMapping("/products")
    public String adminProductPage(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    @GetMapping("/products/add")
    public String adminProductAddGet(Model model){

        model.addAttribute("products", new Product());
        model.addAttribute("category", categoryService.findAll());
        return "productsAdd";
    }


@PostMapping("/products/add")
    public String productAddPost(@ModelAttribute("products")Product product,
                                @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException{

        product.setId(product.getId());
        product.setName(product.getName());
        product.setCategory(product.getCategory());
        product.setPrice(product.getPrice());
        product.setStock(product.getStock());
        product.setDescription(product.getDescription());
        product.setStatus(product.getStatus());

        String imageUUID;
        if (!file.isEmpty()){
            imageUUID= file.getOriginalFilename();
           Path fileNameAndPath= Paths.get(uploadDir, imageUUID);
           //uploaddir- holds the file path
            Files.write(fileNameAndPath, file.getBytes());
            //means file path save kardo- files.write
            //file.getButes- actual image file

        } else {
            imageUUID= imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/products";
}
    //requestparam- means when a request is snd athile parameters oke attached ayit snd akn


































}































