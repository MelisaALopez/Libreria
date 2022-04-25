/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.servicios;

import egg.Libreria.entidades.Autor;
import egg.Libreria.excepciones.WebException;
import egg.Libreria.repositorios.AutorRepositorio;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usr
 */
@Service
public class AutorServicio {

    //Defino como atributo AutorRepositorio
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private LibroServicio libroServicio;

    //Cuando se modifica la base de datos se usa Transactional
    @Transactional
    public Autor guardarAutor(String nombre) {
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        return autorRepositorio.save(autor);
    }

    @Transactional
    public Autor guardarAutor(Autor autor) throws WebException {
        if (autor.getNombre().isEmpty() || autor.getNombre() == null) {
            throw new WebException("El nombre no puede ser nulo");
        }
        return autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores() {
        return autorRepositorio.findAll();
    }

    public List<Autor> listarAutores(String q) {
        return autorRepositorio.buscarAutorPorQuery("%" + q + "%");
    }

    public Optional<Autor> buscarAutorPorId(String id) {
        return autorRepositorio.findById(id);
    }

    public Autor buscarAutor(Autor autor) {
        Optional<Autor> opcional = autorRepositorio.findById(autor.getId());
        if (opcional.isPresent()) {
            autor = opcional.get();
        }
        return autor;
    }

    public Autor buscarAutor(String nombre) {
        return autorRepositorio.buscarAutorPorNombre(nombre);
    }

    public AutorRepositorio getAutorRepositorio() {
        return autorRepositorio;
    }

    public void setAutorRepositorio(AutorRepositorio autorRepositorio) {
        this.autorRepositorio = autorRepositorio;
    }

    @Transactional
    public void eliminarAutor(Autor autor) {
        autorRepositorio.delete(autor);
    }

    @Transactional
    public void eliminarAutorPorId(String id) {
        Optional<Autor> autor = autorRepositorio.findById(id);
        if (autor.isPresent()) {
            libroServicio.eliminarCampoAutor(autor.get());
            autorRepositorio.delete(autor.get());
        }
    }

}
