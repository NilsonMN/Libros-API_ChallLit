package com.ME.ChallLit.Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String birth_year;
    private String death_year;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros = new ArrayList<>();

    public Autores() {
    }

    public Autores(DatosAutor datosAutor){
        this.name = datosAutor.name();
        /*try {
            this.birth_year = Date.parse(datosAutor.birth_year());
        } catch (DateTimeParseException e) {
            this.birth_year = Date.ofEpochDay(0);
        }
        try {
            this.death_year = LocalDate.parse(datosAutor.death_year());
        } catch (DateTimeParseException e) {
            this.death_year = LocalDate.ofEpochDay(0);
        }
         */
        this.birth_year = datosAutor.birth_year();
        this.death_year = datosAutor.death_year();
    }

    //get and set
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getDeath_year() {
        return death_year;
    }

    public void setDeath_year(String death_year) {
        this.death_year = death_year;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autores{" +
                ", name='" + name + '\'' +
                ", birth_year=" + birth_year +
                ", death_year=" + death_year +
                "}\n";
    }
}
