package com.aluracursos.literatura.Main;

import com.aluracursos.literatura.Modelos.Autor;
import com.aluracursos.literatura.Modelos.DatosLibro;
import com.aluracursos.literatura.Modelos.Libro;
import com.aluracursos.literatura.Modelos.Resultados;
import com.aluracursos.literatura.Repository.AutorRepository;
import com.aluracursos.literatura.Repository.LibrosRepository;
import com.aluracursos.literatura.service.ConsumoAPI;
import com.aluracursos.literatura.service.ConvertirDatos;

import java.util.*;

public class Principal {
    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos convertirDatos = new ConvertirDatos();
    private final String direccion = "https://gutendex.com/";
    private String menu = """
            Selecciona el numero de la opción:
            1. Buscar libro por titulo
            2. Lista de libros registrados
            3. Lista de autores registrados
            4. Conocer autores vivos durante un año
            5. Listar libros por idioma
            0. salir
            """;
    private LibrosRepository libroRepo;
    private AutorRepository autorRepo;
    private Optional<Autor> autorBuscado;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibrosRepository librosRepository, AutorRepository autorRepository){
        this.libroRepo = librosRepository;
        this.autorRepo = autorRepository;
    }

    int eleccion = -1;

    public void mostrarMenu(){
        try {
            while (eleccion != 0) {
                System.out.println(menu);
                eleccion = input.nextInt();
                input.nextLine();
                switch (eleccion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        buscarTodosLosLibros();
                        break;
                    case 3:
                        buscarAutoresRegistrados();
                        break;
                    case 4:
                        buscarAutoresVivios();
                        break;
                    case 5:
                        buscarLibrosPorIdioma();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("\nIngrese una opcion valida\n");
                }
            }
        } catch (Exception e){
            System.out.println("\nLibro no encontrado\n");
            mostrarMenu();
        }
    }

    private DatosLibro getDataLibro(){
        System.out.println("Ingresa el nombre del libro");
        String nombre = input.nextLine().replace(" ","%20");
        var json = consumoAPI.obtenerDatos(direccion + "books/?search=" + nombre);
        return convertirDatos.obtenerDatos(json, Resultados.class).resultados().get(0);
    }

    private void buscarLibro(){
        DatosLibro datosLibro = getDataLibro();
        Libro libro = new Libro(datosLibro);
        Autor autor = new Autor(datosLibro.autores().get(0));

        autorBuscado = autorRepo.findByNombre(autor.getNombre());
        try {
            if (!autorBuscado.isPresent()) {
                autorRepo.save(autor);
            }
            libro.setAutor(autor);
            libroRepo.save(libro);
        } catch (Exception e){
            System.out.println("El libro ya existe en la base");
        }
        System.out.println(libro);
    }

    private void buscarTodosLosLibros(){
        libros = libroRepo.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void buscarAutoresRegistrados(){
        autores = autorRepo.findAll();
        System.out.println("\nLos autores registrados son:");
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void buscarAutoresVivios(){
        Integer anio = null; // Inicializa el año

        while (anio == null) {
            try {
                System.out.println("Ingrese el año para buscar el autor:");
                if (input.hasNextInt()) {
                    anio = input.nextInt();
                } else {
                    input.next(); // Descarta la entrada no válida
                    System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                input.next(); // Descarta la entrada no válida
            }
        }
        autores = autorRepo.autoresVivosPorAnio(anio);
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNacimiento))
                .forEach(System.out::println);
    }

    private void buscarLibrosPorIdioma(){
        System.out.println("""
                Ingrese el lenguaje:
                - en -> Inglés
                - es -> Español
                - fr -> Francés
                - pt -> Portugués
                - it -> Italiano
                """);
        String lenguaje = input.nextLine().toLowerCase();
        Integer recuento = libroRepo.recuentoDeLibrosPorIdioma(lenguaje);
        System.out.println("\nEl numero de libros en este idioma son: " + recuento + "\n");
        libros = libroRepo.findByLenguaje(lenguaje);
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }
}
