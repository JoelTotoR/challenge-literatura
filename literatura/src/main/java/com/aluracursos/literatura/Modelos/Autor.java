package com.aluracursos.literatura.Modelos;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nombre;

    private Integer nacimiento;
    private Integer fallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> librosAutor;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.nacimiento = datosAutor.anioDeNacimiento();
        this.fallecimiento = datosAutor.anioDeMuerte();
    }

    public List<Libro> getLibrosAutor() {
        return librosAutor;
    }
    public void setLibrosAutor(List<Libro> librosAutor) {
        librosAutor.forEach(e -> e.setAutor(this));
        this.librosAutor = librosAutor;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }
    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }
    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    @Override
    public String toString() {
        return "-> Autor: '" + nombre  +
                ", nacimiento: " + nacimiento +
                ", fallecimiento: " + fallecimiento;
    }
}
