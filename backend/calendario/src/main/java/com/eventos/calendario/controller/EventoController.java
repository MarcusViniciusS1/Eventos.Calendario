package com.eventos.calendario.controller;

<<<<<<< HEAD
import com.eventos.calendario.controller.dto.EventoDTO;
=======
import com.eventos.calendario.dto.EventoDTO;
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
import com.eventos.calendario.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
<<<<<<< HEAD
@RequestMapping("/eventos")
public class EventoController {
    
    private EventoService eventoService;
    
=======
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "http://localhost:5173")
public class EventoController {

    private final EventoService eventoService;

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/publico")
    public ResponseEntity<List<EventoDTO>> listarEventosPublico() {
        List<EventoDTO> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }

<<<<<<< HEAD
=======



>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarTodos() {
        List<EventoDTO> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Long id) {
        return eventoService.buscarPorId(id)
                .map(evento -> ResponseEntity.ok(evento))
                .orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    @PostMapping
    public ResponseEntity<EventoDTO> criar(@Valid @RequestBody EventoDTO eventoDTO) {
        try {
            EventoDTO eventoCriado = eventoService.criar(eventoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

<<<<<<< HEAD
    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizar(@PathVariable Long id, 
                                              @Valid @RequestBody EventoDTO eventoDTO) {
=======

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizar(@PathVariable Long id,
                                               @Valid @RequestBody EventoDTO eventoDTO) {
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
        try {
            EventoDTO eventoAtualizado = eventoService.atualizar(id, eventoDTO);
            return ResponseEntity.ok(eventoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            eventoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    @GetMapping("/buscar")
    public ResponseEntity<List<EventoDTO>> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String organizador) {
<<<<<<< HEAD
        
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
        List<EventoDTO> eventos = eventoService.buscar(titulo, organizador);
        return ResponseEntity.ok(eventos);
    }

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

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Erro: " + e.getMessage()));
    }

<<<<<<< HEAD
    public static class ErrorResponse {
        private String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
        
=======

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
        public void setMessage(String message) {
            this.message = message;
        }
    }
}