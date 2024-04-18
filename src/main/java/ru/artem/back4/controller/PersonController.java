package ru.artem.back4.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.artem.back4.dao.PersonDAO;
import ru.artem.back4.model.Person;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    @Autowired
    public PersonController(PersonDAO personDAO){this.personDAO=personDAO;}
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){return "form";}
    @PostMapping()
    public String create(@ModelAttribute("person")  Person person) {


        personDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",personDAO.index());
        return "people";
    }
}
