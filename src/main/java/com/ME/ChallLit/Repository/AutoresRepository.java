package com.ME.ChallLit.Repository;

import com.ME.ChallLit.Entity.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autores, Long> {
    Optional<Autores> findByName(String name);
    @Query("SELECT a FROM Autores a")
    List<Autores> listarAutores();
    @Query("SELECT a FROM Autores a WHERE a.birth_year <= :year AND a.death_year >= :year")
    List<Autores> findAutoresByYear(@Param("year") int year);
}
