package com.farm404.samyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DatabaseTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test/db")
    public Map<String, Object> testDatabaseConnection() {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = jdbcTemplate.queryForObject("SELECT VERSION()", String.class);
            response.put("status", "success");
            response.put("message", "Database connected successfully");
            response.put("version", result);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to connect to database");
            response.put("error", e.getMessage());
        }
        return response;
    }
}