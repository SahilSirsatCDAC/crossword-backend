package com.example.crossword.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Profile("dev") // only runs when dev profile is active
public class StartupLogger implements CommandLineRunner {

    private final JdbcTemplate jdbc;

    public StartupLogger(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) {
        System.out.println(">>> Running JDBC diagnostics...");

        // Example: check if crossword_id = 1 exists
        Long testId = 1L;
        List<Map<String,Object>> rows = jdbc.queryForList(
                "SELECT crossword_id, name FROM crossword WHERE crossword_id = ?", testId
        );
        String dbName = jdbc.queryForObject("SELECT DATABASE()", String.class);
        System.out.println(">>> App sees database: " + dbName);
        System.out.println(">>> JDBC query result for crossword_id=" + testId + ": " + rows);

        List<String> tables = jdbc.queryForList("SHOW TABLES", String.class);
        System.out.println(">>> Tables visible to app user: " + tables);

        // Extra: count total rows
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM crossword", Integer.class);
        System.out.println(">>> Total rows in crossword table: " + count);
    }
}
