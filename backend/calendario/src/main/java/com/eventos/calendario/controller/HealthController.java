package com.eventos.calendario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@CrossOrigin(origins = {"http://localhost:3000"})
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Calendário de Eventos API");
        health.put("version", "1.0.0");
        
        Map<String, String> components = new HashMap<>();
        components.put("database", "UP");
        components.put("security", "UP");
        components.put("jwt", "UP");
        
        health.put("components", components);
        
        return ResponseEntity.ok(health);
    }
}