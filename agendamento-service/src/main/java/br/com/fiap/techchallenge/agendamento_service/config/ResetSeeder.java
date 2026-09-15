package br.com.fiap.techchallenge.agendamento_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class ResetSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public ResetSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        jdbcTemplate.execute("TRUNCATE TABLE consultas, usuarios RESTART IDENTITY CASCADE");
    }
}
