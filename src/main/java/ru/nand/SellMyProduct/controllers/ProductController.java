package ru.nand.SellMyProduct.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nand.SellMyProduct.details.PersonDetails;
import ru.nand.SellMyProduct.models.Person;
import ru.nand.SellMyProduct.models.Product;
import ru.nand.SellMyProduct.services.ProductService;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/currentProduct/{id}") // Отобразить конкретный продукт
    public String showProduct(@PathVariable("id") int id, Model model){
        Optional<Product> currentProduct = productService.findById(id);
        if(currentProduct.isPresent()){
            Product product = currentProduct.get();
            model.addAttribute("product", product);
            return "productView";
        } else {
            return "productNotFound";
        }
    }

    @GetMapping()
    public String showAllProducts(Model model){ // Все продукты
        model.addAttribute("products", productService.findAll());
        return "allProducts";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model, @AuthenticationPrincipal PersonDetails personDetails) { // Страница добавления продукта
        model.addAttribute("product", new Product());
        model.addAttribute("currentPerson", personDetails.getPerson());
        if(personDetails.getPerson() != null){
            System.out.println("GET Передан пользователь " + personDetails.getPerson().getName());
        }
        else {
            System.out.println("Пользователь null");
        }
        return "addProduct";
    }

    @PostMapping("/add")
    public String addProduct(
                    @ModelAttribute("product") Product product,
                    @RequestParam("image")MultipartFile imageFile,
                    @AuthenticationPrincipal PersonDetails personDetails)
    {
        if(personDetails != null){

            Person person = personDetails.getPerson();
            System.out.println("POST Передан пользователь " + person.getName());

            if(!imageFile.isEmpty()){
                String imagePath = productService.saveImage(imageFile);
                product.setImagePath(imagePath);
            }

            product.setPerson(person);
            productService.save(product);
            return "redirect:/products";
        }
        else {
            System.out.println("Пользователь null");
        }


        return "redirect:/home";
    }

}
