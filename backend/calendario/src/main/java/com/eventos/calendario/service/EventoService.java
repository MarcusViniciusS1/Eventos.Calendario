package com.eventos.calendario.service;

import com.eventos.calendario.controller.dto.EventoDTO;
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
    
    private final EventoRepository eventoRepository;
    
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<EventoDTO> listarTodos() {
        return eventoRepository.findAllByOrderByDataAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EventoDTO> buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public EventoDTO criar(EventoDTO eventoDTO) {
        Evento evento = convertToEntity(eventoDTO);
        Evento eventoSalvo = eventoRepository.save(evento);
        return convertToDTO(eventoSalvo);
    }

    public EventoDTO atualizar(Long id, EventoDTO eventoDTO) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    evento.setTitulo(eventoDTO.getTitulo());
                    evento.setDescricao(eventoDTO.getDescricao());
                    evento.setData(LocalDate.parse(eventoDTO.getData()));
                    evento.setHora(LocalTime.parse(eventoDTO.getHora()));
                    evento.setLocal(eventoDTO.getLocal());
                    evento.setOrganizador(eventoDTO.getOrganizador());
                    
                    Evento eventoAtualizado = eventoRepository.save(evento);
                    return convertToDTO(eventoAtualizado);
                })
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
    }

    public void deletar(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new RuntimeException("Evento não encontrado com ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    public List<EventoDTO> buscar(String titulo, String organizador) {
        List<Evento> eventos;
        
        if (titulo != null && organizador != null) {
            eventos = eventoRepository.findByTituloAndOrganizador(titulo, organizador);
        } else if (titulo != null) {
            eventos = eventoRepository.findByTituloContainingIgnoreCase(titulo);
        } else if (organizador != null) {
            eventos = eventoRepository.findByOrganizadorContainingIgnoreCase(organizador);
        } else {
            eventos = eventoRepository.findAllByOrderByDataAsc();
        }
        
        return eventos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<EventoDTO> buscarPorData(LocalDate data) {
        return eventoRepository.findByData(data)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EventoDTO convertToDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setDescricao(evento.getDescricao());
        dto.setData(evento.getData().toString());
        dto.setHora(evento.getHora().toString());
        dto.setLocal(evento.getLocal());
        dto.setOrganizador(evento.getOrganizador());
        
        if (evento.getCreatedAt() != null) {
            dto.setCreatedAt(evento.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        if (evento.getUpdatedAt() != null) {
            dto.setUpdatedAt(evento.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        
        return dto;
    }

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