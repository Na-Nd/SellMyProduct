package ru.nand.SellMyProduct.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nand.SellMyProduct.models.Person;
import ru.nand.SellMyProduct.models.Product;
import ru.nand.SellMyProduct.services.PersonService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        System.out.println("Person Controller GET register");
        model.addAttribute("person", new Person());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, Model model){
        System.out.println("Person Controller POST register");
        if (bindingResult.hasErrors()){
            System.out.println("Ошибки");
            return "register";
        }
        System.out.println("Нет ошибок");
        personService.register(person);
        return "redirect:/codeAuth";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        System.out.println("Person Controller GET login");
        model.addAttribute("person", new Person());
        return "login";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model){
        Optional<Person> currentPerson = personService.findById(id);
        if(currentPerson.isPresent()){
            Person person = currentPerson.get();
            model.addAttribute("person", person);

            List<Product> products = personService.findProductsByPerson(person);
            model.addAttribute("products", products);

            return "personView";
        } else {
            return "personNotFound";
        }
    }
}
