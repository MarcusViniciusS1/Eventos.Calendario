package com.eventos.calendario.repository;

import com.eventos.calendario.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
<<<<<<< HEAD
    
    // Buscar eventos por título (case insensitive)
    List<Evento> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar eventos por organizador (case insensitive)
    List<Evento> findByOrganizadorContainingIgnoreCase(String organizador);
    
    // Buscar eventos por título E organizador
    @Query("SELECT e FROM Evento e WHERE " +
           "(:titulo IS NULL OR LOWER(e.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) AND " +
           "(:organizador IS NULL OR LOWER(e.organizador) LIKE LOWER(CONCAT('%', :organizador, '%')))")
    List<Evento> findByTituloAndOrganizador(@Param("titulo") String titulo, 
                                           @Param("organizador") String organizador);
    
    // Buscar eventos por data
    List<Evento> findByData(LocalDate data);
    
    // Buscar eventos a partir de uma data
    List<Evento> findByDataGreaterThanEqualOrderByDataAsc(LocalDate data);
    
    // Buscar eventos entre duas datas
    List<Evento> findByDataBetweenOrderByDataAsc(LocalDate dataInicio, LocalDate dataFim);
    
    // Buscar todos os eventos ordenados por data
=======


    List<Evento> findByTituloContainingIgnoreCase(String titulo);


    List<Evento> findByOrganizadorContainingIgnoreCase(String organizador);


    @Query("SELECT e FROM Evento e WHERE " +
            "(:titulo IS NULL OR LOWER(e.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) AND " +
            "(:organizador IS NULL OR LOWER(e.organizador) LIKE LOWER(CONCAT('%', :organizador, '%')))")
    List<Evento> findByTituloAndOrganizador(@Param("titulo") String titulo,
                                            @Param("organizador") String organizador);


    List<Evento> findByData(LocalDate data);


    List<Evento> findByDataGreaterThanEqualOrderByDataAsc(LocalDate data);


    List<Evento> findByDataBetweenOrderByDataAsc(LocalDate dataInicio, LocalDate dataFim);


>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
    List<Evento> findAllByOrderByDataAsc();
}