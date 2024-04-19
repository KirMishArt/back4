package ru.artem.back4.controller;

//import jakarta.validation.Valid;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    @Autowired
    public PersonController(PersonDAO personDAO){this.personDAO=personDAO;}
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){return "form";}
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            addCookies(person, response);
            return "form";
        }


        deleteCookies(response);
        personDAO.save(person);
        return "redirect:/people";
    }
    private void addCookies(Person person, HttpServletResponse response){
        Cookie nameCookie=new Cookie("name",person.getName());
        nameCookie.setMaxAge(60*60*24*365);
        response.addCookie(nameCookie);
        Cookie surnameCookie = new Cookie("surname", person.getSurname());
        surnameCookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(surnameCookie);

        Cookie secondNameCookie = new Cookie("second_name", person.getSecond_name());
        secondNameCookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(secondNameCookie);

        Cookie telCookie = new Cookie("tel", person.getTel());
        telCookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(telCookie);

        Cookie emailCookie = new Cookie("email", person.getEmail());
        emailCookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(emailCookie);

    }
    private void deleteCookies(HttpServletResponse response) {
        Cookie nameCookie = new Cookie("name", "");
        nameCookie.setMaxAge(0);
        response.addCookie(nameCookie);

        Cookie surnameCookie = new Cookie("surname", "");
        surnameCookie.setMaxAge(0);
        response.addCookie(surnameCookie);

        Cookie secondNameCookie = new Cookie("second_name", "");
        secondNameCookie.setMaxAge(0);
        response.addCookie(secondNameCookie);

        Cookie telCookie = new Cookie("tel", "");
        telCookie.setMaxAge(0);
        response.addCookie(telCookie);

        Cookie emailCookie = new Cookie("email", "");
        emailCookie.setMaxAge(0);
        response.addCookie(emailCookie);
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",personDAO.index());
        return "people";
    }
}
