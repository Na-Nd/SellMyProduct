package ru.nand.SellMyProduct.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nand.SellMyProduct.details.PersonDetails;
import ru.nand.SellMyProduct.models.Person;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal PersonDetails personDetails, Model model){
        if(personDetails != null){
            Person currentPerson = personDetails.getPerson();
            System.out.println("GET HOME Передан пользователь " + currentPerson.getName());
            model.addAttribute("person", currentPerson);
        }
        else {
            System.out.println("GET HOME Пользователь null");
        }
        return "home";
    }
}
