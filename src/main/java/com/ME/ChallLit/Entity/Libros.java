package com.ME.ChallLit.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private List<String> languages;
    private Double download_count;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autores> autores;

    public Libros() {
    }

    public Libros(DatosLibros datosLibros, List<Autores> autores) {
        this.id = Long.valueOf(datosLibros.id());
        this.title = datosLibros.titulo();
        this.languages = datosLibros.idiomas();
        this.download_count = datosLibros.numeroDeDescargas();
        this.autores = autores;
    }

    // Getters y setters omitidos para brevedad

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Double getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Double download_count) {
        this.download_count = download_count;
    }

    public List<Autores> getAutores() {
        return autores;
    }

    public void setAutores(List<Autores> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Libros{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", languages=" + languages +
                ", download_count=" + download_count +
                ", autores=" + autores +
                "}\n";
    }
}
