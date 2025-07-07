package com.eventos.calendario.controller;

import com.eventos.calendario.dto.EventoDTO;
import com.eventos.calendario.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = {"http://localhost:3000"})
public class EventoController {
    
    private final EventoService eventoService;
    
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }
    
    // Endpoint público - listar todos os eventos
    @GetMapping("/publico")
    public ResponseEntity<List<EventoDTO>> listarEventosPublico() {
        System.out.println("📋 Listando eventos públicos...");
        List<EventoDTO> eventos = eventoService.listarTodos();
        System.out.println("✅ Retornando " + eventos.size() + " eventos");
        return ResponseEntity.ok(eventos);
    }
    
    // Listar todos os eventos (admin) - agora público também
    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarTodos() {
        List<EventoDTO> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }
    
    // Buscar evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Long id) {
        return eventoService.buscarPorId(id)
                .map(evento -> ResponseEntity.ok(evento))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Criar novo evento
    @PostMapping
    public ResponseEntity<EventoDTO> criar(@Valid @RequestBody EventoDTO eventoDTO) {
        try {
            EventoDTO eventoCriado = eventoService.criar(eventoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Atualizar evento
    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizar(@PathVariable Long id, 
                                              @Valid @RequestBody EventoDTO eventoDTO) {
        try {
            EventoDTO eventoAtualizado = eventoService.atualizar(id, eventoDTO);
            return ResponseEntity.ok(eventoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Deletar evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            eventoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Buscar eventos por título e/ou organizador
    @GetMapping("/buscar")
    public ResponseEntity<List<EventoDTO>> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String organizador) {
        
        List<EventoDTO> eventos = eventoService.buscar(titulo, organizador);
        return ResponseEntity.ok(eventos);
    }
    
    // Buscar eventos por data
    @GetMapping("/data/{data}")
    public ResponseEntity<List<EventoDTO>> buscarPorData(@PathVariable String data) {
        try {
            LocalDate localDate = LocalDate.parse(data);
            List<EventoDTO> eventos = eventoService.buscarPorData(localDate);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Endpoint para tratamento de erros
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Erro: " + e.getMessage()));
    }
    
    // Classe para resposta de erro
    public static class ErrorResponse {
        private String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
}