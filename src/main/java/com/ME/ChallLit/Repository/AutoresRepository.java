package com.ME.ChallLit.Repository;

import com.ME.ChallLit.Entity.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autores, Long> {
    Optional<Autores> findByName(String name);
    @Query("SELECT a FROM Autores a")
    List<Autores> listarAutores();
}
