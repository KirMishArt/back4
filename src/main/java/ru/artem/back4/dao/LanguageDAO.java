package ru.artem.back4.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.artem.back4.dto.LanguageDTO;
import ru.artem.back4.model.Language;

import java.util.List;

@Component

public class LanguageDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LanguageDAO(JdbcTemplate jdbcTemplate){this.jdbcTemplate=jdbcTemplate;}

    public List<LanguageDTO> index(){
        return jdbcTemplate.query("SELECT p.name, p.surname, l.name as language\n" +
                "FROM language_person lp\n" +
                "JOIN persons p ON lp.person_id = p.id\n" +
                "JOIN languages l ON lp.language_id = l.id;\n",new BeanPropertyRowMapper<LanguageDTO>(LanguageDTO.class));
    }
}
