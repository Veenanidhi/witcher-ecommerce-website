package com.witcher.e_commerce.application.witcher.controller;

import com.witcher.e_commerce.application.witcher.entity.User;
import com.witcher.e_commerce.application.witcher.service.EmailService;
import com.witcher.e_commerce.application.witcher.service.UserService;
import com.witcher.e_commerce.application.witcher.service.category.CategoryService;
import com.witcher.e_commerce.application.witcher.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {


    private final UserService userService;

    private final EmailService emailService;

    private final CategoryService categoryService;

    private final ProductService productService;

    @Autowired
    public AuthController(UserService userService, EmailService emailService, CategoryService categoryService, ProductService productService){
        this.userService=userService;
        this.emailService = emailService;
        this.categoryService = categoryService;
        this.productService = productService;
    }




    @GetMapping("/login")
    public String showLogin(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }

        return "redirect:/product-listing";

    }



    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/home")
    public String showHome(Model model){
        return "landing";
    }

    @GetMapping("/signup")
    public String signUpController(Model model){
        model.addAttribute("webUser", new User());
        return "signup";

    }

    @PostMapping("/processSignUpPage")
    public String processSignUpPage(
            @ModelAttribute("webUser") User theWebUser,
            BindingResult theBindingResult, RedirectAttributes ra,
            Model theModel
    ){


        //to check the username is already taken
        boolean existingUser=userService.existsByUsername(theWebUser.getUsername());
        if (existingUser){
            theModel.addAttribute("webUser",new User());
            theModel.addAttribute("signupError","Username already exists!!!");
            return "redirect:/signup";
        }

        boolean existingEmail= userService.existsByEmail(theWebUser.getEmail());
        if (existingEmail){
            theModel.addAttribute("message","Email already exists");
            return "signup";
        }

        //save the new user
        userService.registerUser(theWebUser);
        ra.addFlashAttribute("message","an activation mail is send to ur mail");
        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";
    }

    @GetMapping("/productPage")
    public String productPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.getAllProduct());
        return "product-listing";
    }

    @GetMapping("/productPage/category/{id}")
    public String productByCategory(Model model, @PathVariable Long id){
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));

        return "product-listing";
    }

    @GetMapping("/productList/viewProduct/{id}")
    public String viewProduct(Model model, @PathVariable Long id){
        model.addAttribute("product", productService.getProductById(id).get());
        return "view-product";
    }

    

}





























/* @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Principal principal, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object authenticatedAttribute = session.getAttribute("authenticated");
            if (authenticatedAttribute != null && (Boolean) authenticatedAttribute) {
                return "redirect:/"; // Redirect authenticated users away from the login page
            }
            session.removeAttribute("registration");
        }
        return "login";
    }*/