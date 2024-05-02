package ru.artem.back4.controller;

//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.artem.back4.dao.LanguageDAO;
import ru.artem.back4.dao.PersonDAO;
import ru.artem.back4.model.Person;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final LanguageDAO languageDAO;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public PersonController(PersonDAO personDAO, LanguageDAO languageDAO, PasswordEncoder passwordEncoder) {
        this.personDAO = personDAO;
        this.languageDAO = languageDAO;
        this.passwordEncoder = passwordEncoder;
    }





    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){return "form";}
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, HttpServletResponse response,Model model) {
        if(bindingResult.hasErrors()) {
            addCookies(person, response);
            return "form";
        }
        String login=generateLogin();
        String password=generatePassword();
        person.setLogin(login);
        person.setPassword(passwordEncoder.encode(password));

        deleteCookies(response);
        personDAO.save(person);
        model.addAttribute("login", login);
        model.addAttribute("password", password);

        return "data";
    }
    private String generateLogin(){
        return UUID.randomUUID().toString().substring(0,5);
    }
    private String generatePassword() {
        // генерация пароля с использованием случайных символов
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            password.append(c);
        }

        return password.toString();
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

        String lang=String.join(",",person.getFovoLang());
        lang=lang.replaceAll("[^a-zA-Z0-9-]", "_");
        Cookie fovoLang = new Cookie("fovoLang", lang);
        fovoLang.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(fovoLang);

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

        Cookie fovoLang = new Cookie("fovoLang", "");
        fovoLang.setMaxAge(0);
        response.addCookie(fovoLang);

    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",personDAO.index());
        model.addAttribute("languages", languageDAO.index());
        return "people";
    }

}
