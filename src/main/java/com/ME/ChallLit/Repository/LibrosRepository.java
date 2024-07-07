package com.ME.ChallLit.Repository;

import com.ME.ChallLit.Entity.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libros, Long> {
    Optional<Libros> findByTitle(String title);
    @Query("SELECT DISTINCT l FROM Libros l LEFT JOIN FETCH l.autores")
    List<Libros> listarLibros();
    @Query(value = "SELECT DISTINCT l.* FROM libros l LEFT JOIN libros_autores la ON l.id = la.libro_id " +
            "LEFT JOIN autores a ON la.autor_id = a.id " +
            "WHERE :idioma = ANY(l.languages)", nativeQuery = true)
    List<Libros> findByLanguage(@Param("idioma") String idioma);

}
