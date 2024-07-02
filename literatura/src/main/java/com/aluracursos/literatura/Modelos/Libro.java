package com.aluracursos.literatura.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String titulo;

    private String lenguaje;
    private Integer numeroDescargas;

    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autores().get(0));
        this.lenguaje = datosLibro.lenguajes().get(0);
        this.numeroDescargas = datosLibro.numDescargas();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguajes) {
        this.lenguaje = lenguajes;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "\n" + "*".repeat(30) + "\n" +
                "Titulo= " + titulo + "\n" +
                "Autor= " + autor.getNombre() + "\n" +
                "Lenguajes= " + lenguaje + "\n" +
                "Numero de descargas: " + numeroDescargas + "\n" +
                "*".repeat(30) + "\n";
    }
}
