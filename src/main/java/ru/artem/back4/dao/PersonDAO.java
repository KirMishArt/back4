package ru.artem.back4.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.artem.back4.model.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){this.jdbcTemplate=jdbcTemplate;}

    public List<Person>index(){

        return jdbcTemplate.query("SELECT * FROM persons",new BeanPropertyRowMapper<Person>(Person.class));
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO persons (name, surname, second_name, tel, email, birth, gender, bio,login,password) VALUES (?,?,?,?,?,?,?,?,?,?)",
                person.getName(), person.getSurname(), person.getSecond_name(),
                person.getTel(), person.getEmail(), person.getBirth(),
                person.getGender(), person.getBio(),person.getLogin(),person.getPassword());

        int personId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM persons", Integer.class);


// сохраняем избранные языки в таблицу language_person
        for (String language : person.getFovoLang()) {
            jdbcTemplate.update("INSERT INTO language_person (person_id, language_id) VALUES (?, ?)", personId, getLanguageId(language));
        }
    }

    private int getLanguageId(String languageName) {
        String lowerCaseLanguageName = languageName.toLowerCase();
        return jdbcTemplate.queryForObject("SELECT id FROM languages WHERE LOWER(name) = ?", Integer.class, lowerCaseLanguageName);
    }
    public Person findByLogin(String login) {
        return jdbcTemplate.queryForObject("SELECT * FROM persons WHERE login = ?",
                new BeanPropertyRowMapper<>(Person.class), login);
    }
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM persons WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE persons SET name=?,surname=?,second_name=?,email=? WHERE id=?", updatedPerson.getName(),updatedPerson.getSurname(),updatedPerson.getSecond_name(),updatedPerson.getEmail(),id);
    }

}
