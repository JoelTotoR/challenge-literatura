package com.aluracursos.literatura.Repository;

import com.aluracursos.literatura.Modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);
    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :anio AND a.fallecimiento >= :anio")
    List<Autor> autoresVivosPorAnio(Integer anio);
}
