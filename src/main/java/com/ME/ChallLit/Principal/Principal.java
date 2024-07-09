package com.ME.ChallLit.Principal;

import com.ME.ChallLit.Entity.*;
import com.ME.ChallLit.Repository.AutoresRepository;
import com.ME.ChallLit.Service.ConsumoAPI;
import com.ME.ChallLit.Service.ConvierteDatos;
import com.ME.ChallLit.Repository.LibrosRepository;
import java.util.*;
import java.util.stream.Collectors;

public class Principal
{

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibrosRepository repositoryLibros;
    private AutoresRepository repositoryAutores;

    public Principal(LibrosRepository librosRepository, AutoresRepository autoresRepository)
    {
        this.repositoryLibros = librosRepository;
        this.repositoryAutores = autoresRepository;
    }

    public void menu()
    {
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                        1. Buscar libro por titulo
                        2. Listrar libros registrados
                        3. Listar autores registrados
                        4. Listrar libros por idioma
                        5. Autores vivos en determinado año
                        
                        0. Salir                
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarLibrosPorIdioma();
                    break;
                case 5:
                    autoresVivos();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
        //buscarLibroPorTitulo();
        //listarLibros();
        //listarAutores();
        //listarLibrosPorIdioma();
        // Otros métodos del menú
        //autoresVivos();
    }

    public DatosLibros getDatos()
    {
        System.out.println("Ingrese el nombre del libro: ");
        String nombreLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datos.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent())
        {
            System.out.println("LIBRO ENCONTRADO");
            System.out.println(libroBuscado.get());
            return libroBuscado.get();
        }
        else
        {
            System.out.println("LIBRO NO ENCONTRADO");
            return null;
        }
    }

    public void buscarLibroPorTitulo()
    {
        DatosLibros datos = getDatos();
        if (datos == null)
        {
            System.out.println("No se encontraron datos del libro.");
            return;
        }

        Optional<Libros> libroExistente = repositoryLibros.findByTitle(datos.titulo());
        if (libroExistente.isPresent())
        {
            System.out.println("El libro/autor ya existe en la base de datos. No se registraran cambios.");
            return;
        }

        // Guardar autores si no existen
        List<Autores> autoresGuardados = datos.autor().stream()
                .map(DatosAutor -> {
                    Optional<Autores> autorExistente = repositoryAutores.findByName(DatosAutor.name());
                    return autorExistente.orElseGet(() -> {
                        Autores nuevoAutor = new Autores(DatosAutor);
                        return repositoryAutores.save(nuevoAutor);
                    });
                })
                .collect(Collectors.toList());

        //crear y guardar el libro con los autores
        Libros libro = new Libros(datos, autoresGuardados);
        libro = repositoryLibros.save(libro);

        for (Autores autor : autoresGuardados)
        {
            autor.getLibros().add(libro);
            repositoryAutores.save(autor);
        }

        System.out.println("Libro y autores guardados exitosamente en la base de datos.");
    }

    public void listarLibros()
    {
        List<Libros> libros = repositoryLibros.listarLibros();
        System.out.println(libros);
    }

    public void listarAutores()
    {
        List<Autores> autores = repositoryAutores.listarAutores();
        System.out.println(autores);
    }

    public void listarLibrosPorIdioma()
    {
        System.out.println("Ingrese el idioma: \n" +
                "hu- español\n" +
                "en- ingles\n" +
                "fr- frances\n" +
                "pt- portugues\n");
        String idioma = teclado.nextLine();

        List<Libros> libros = repositoryLibros.findByLanguage(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("Libros encontrados en el idioma: " + idioma);
            for (Libros libro : libros) {
                System.out.println(libro);
            }
        }
    }

    public void autoresVivos()
    {
        System.out.println("Ingrese el año: ");
        var anio = teclado.nextInt();
        List<Autores> autores = repositoryAutores.findAutoresByYear(anio);
        System.out.println(autores);
    }
}
