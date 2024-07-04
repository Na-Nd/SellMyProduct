package ru.nand.SellMyProduct.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nand.SellMyProduct.services.PersonService;

@Controller
public class AuthCodeController {

    private final PersonService personService;

    @Autowired
    public AuthCodeController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/codeAuth")
    public String showCodeAuthForm(){
        return "codeAuth";
    }

    @PostMapping("/codeAuth")
    public String verifyCode(@RequestParam("email") String email, @RequestParam("authCode") String authCode, Model model){
        if(personService.authenticateUser(email, authCode)){
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid auth code");
            return "codeAuth";
        }
    }

}
