package com.aluracursos.literatura.Repository;

import com.aluracursos.literatura.Modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibrosRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByLenguaje(String lenguaje);
    @Query("SELECT COUNT(l) FROM Libro l WHERE l.lenguaje = :lenguaje")
    Integer recuentoDeLibrosPorIdioma(String lenguaje);
}
