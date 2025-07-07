package com.eventos.calendario.service;

<<<<<<< HEAD
import com.eventos.calendario.controller.dto.EventoDTO;
=======
import com.eventos.calendario.dto.EventoDTO;
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
import com.eventos.calendario.model.Evento;
import com.eventos.calendario.repository.EventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventoService {
<<<<<<< HEAD
    
    private final EventoRepository eventoRepository;
    
=======

    private final EventoRepository eventoRepository;

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<EventoDTO> listarTodos() {
        return eventoRepository.findAllByOrderByDataAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    public Optional<EventoDTO> buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .map(this::convertToDTO);
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    public EventoDTO criar(EventoDTO eventoDTO) {
        Evento evento = convertToEntity(eventoDTO);
        Evento eventoSalvo = eventoRepository.save(evento);
        return convertToDTO(eventoSalvo);
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    public EventoDTO atualizar(Long id, EventoDTO eventoDTO) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    evento.setTitulo(eventoDTO.getTitulo());
                    evento.setDescricao(eventoDTO.getDescricao());
                    evento.setData(LocalDate.parse(eventoDTO.getData()));
                    evento.setHora(LocalTime.parse(eventoDTO.getHora()));
                    evento.setLocal(eventoDTO.getLocal());
                    evento.setOrganizador(eventoDTO.getOrganizador());
<<<<<<< HEAD
                    
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
                    Evento eventoAtualizado = eventoRepository.save(evento);
                    return convertToDTO(eventoAtualizado);
                })
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    public void deletar(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new RuntimeException("Evento não encontrado com ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

<<<<<<< HEAD
    public List<EventoDTO> buscar(String titulo, String organizador) {
        List<Evento> eventos;
        
=======

    public List<EventoDTO> buscar(String titulo, String organizador) {
        List<Evento> eventos;

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
        if (titulo != null && organizador != null) {
            eventos = eventoRepository.findByTituloAndOrganizador(titulo, organizador);
        } else if (titulo != null) {
            eventos = eventoRepository.findByTituloContainingIgnoreCase(titulo);
        } else if (organizador != null) {
            eventos = eventoRepository.findByOrganizadorContainingIgnoreCase(organizador);
        } else {
            eventos = eventoRepository.findAllByOrderByDataAsc();
        }
<<<<<<< HEAD
        
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
        return eventos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    public List<EventoDTO> buscarPorData(LocalDate data) {
        return eventoRepository.findByData(data)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

<<<<<<< HEAD
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    private EventoDTO convertToDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setDescricao(evento.getDescricao());
        dto.setData(evento.getData().toString());
        dto.setHora(evento.getHora().toString());
        dto.setLocal(evento.getLocal());
        dto.setOrganizador(evento.getOrganizador());
<<<<<<< HEAD
        
=======

>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
        if (evento.getCreatedAt() != null) {
            dto.setCreatedAt(evento.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        if (evento.getUpdatedAt() != null) {
            dto.setUpdatedAt(evento.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
<<<<<<< HEAD
        
        return dto;
    }

=======

        return dto;
    }


>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    private Evento convertToEntity(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(LocalDate.parse(dto.getData()));
        evento.setHora(LocalTime.parse(dto.getHora()));
        evento.setLocal(dto.getLocal());
        evento.setOrganizador(dto.getOrganizador());
        return evento;
    }
}