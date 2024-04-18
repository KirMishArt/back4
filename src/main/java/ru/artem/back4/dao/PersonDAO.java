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
        jdbcTemplate.update("INSERT INTO persons (name, surname, second_name, tel, email, birth, gender, fovolang, bio) VALUES (?,?,?,?,?,?,?,?,?)",person.getName(),person.getSurname(),person.getSecond_name(),
                person.getTel(),person.getEmail(),person.getBirth(),
                person.getGender(),person.getFovoLang(),person.getBio());
    }
}
