/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.servicios;

import egg.Libreria.entidades.Libro;
import egg.Libreria.entidades.Autor;
import egg.Libreria.entidades.Editorial;
import egg.Libreria.excepciones.ErrorServicio;
import egg.Libreria.excepciones.WebException;
import egg.Libreria.repositorios.AutorRepositorio;
import egg.Libreria.repositorios.EditorialRepositorio;
import egg.Libreria.repositorios.LibroRepositorio;
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
public class LibroServicio {

    //Defino como atributo LibroRepositorio
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    //Cuando se modifica la base de datos se usa Transactional
    @Transactional
    public Libro guardarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String autor, String editorial) {
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
        libro.setAutor(autorServicio.getAutorRepositorio().buscarAutorPorNombre(autor));
        libro.setEditorial(editorialServicio.getEditorialRepositorio().buscarEditorialPorNombre(editorial));
        libro.setAlta(true);
        return libroRepositorio.save(libro);
    }
    
    @Transactional
    public void guardarLibro(Libro libro) throws WebException {
        if(libro.getIsbn() < 1 || libro.getIsbn() == null){
            throw new WebException("El código ISBN no puede ser nulo o menor que 1");
        }
        if(libro.getTitulo().isEmpty() || libro.getTitulo() == null){
            throw new WebException("El título no puede ser nulo");
        }
        if(libro.getAnio() < 1000 || libro.getAnio() == null){
            throw new WebException("El año no puede ser nulo");
        }
        if(libro.getEjemplares() < 1 || libro.getEjemplares() == null){
            throw new WebException("La cantidad de ejemplares no puede ser nula o menor que 1");
        }
        if(libro.getEjemplaresPrestados() < 0 || libro.getEjemplaresPrestados() > libro.getEjemplares()){
            throw new WebException("La cantidad de ejemplares prestados no puede ser menor a 0 o mayor a la cantidad total de ejemplares");
        }
        if(libro.getAutor() == null){
            throw new WebException("El autor no puede ser nulo");
        } else {
            libro.setAutor(autorServicio.buscarAutor(libro.getAutor()));
        }
        if(libro.getEditorial()== null){
            throw new WebException("La editorial no puede ser nula");
        } else {
            libro.setEditorial(editorialServicio.buscarEditorial(libro.getEditorial()));
        }
        libroRepositorio.save(libro);
    }

//    public boolean verificarDatos(String titulo) {
//        Libro libro = libroRepositorio.buscarLibroPorTitulo(titulo);
//        return libro == null;
//    }
    
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }
    
    public List<Libro> listarLibros(String q) {
        return libroRepositorio.buscarLibroPorQuery("%"+q+"%");
    }
    
    public Optional<Libro> buscarLibroPorId(String id) {
        return libroRepositorio.findById(id);
    }
    
    public List<Libro> buscarLibroPorAutor(String nombre) {
        return libroRepositorio.buscarLibroPorAutor(nombre);
    }
    
    @Transactional
    public void eliminarLibro(Libro libro) {
        libroRepositorio.delete(libro);
    }
    
    @Transactional
    public void eliminarLibro(String titulo) {
        Libro libro = libroRepositorio.buscarLibroPorTitulo(titulo);
        libroRepositorio.delete(libro);
    }
    
    @Transactional
    public void eliminarLibroPorId(String id){
        Optional<Libro> libro = libroRepositorio.findById(id);
        if(libro.isPresent()){
            libroRepositorio.delete(libro.get());
        }   
    }
    
    @Transactional
    public void eliminarCampoAutor(Autor autor) {
        List<Libro> libros = libroRepositorio.buscarLibroPorAutor(autor.getNombre());
        for (Libro libro : libros) {
            libro.setAutor(null);
        }
        libroRepositorio.saveAll(libros);
    }
    
    @Transactional
    public void eliminarCampoEditorial(Editorial editorial) {
        List<Libro> libros = libroRepositorio.buscarLibroPorEditorial(editorial.getNombre());
        for (Libro libro : libros) {
            libro.setEditorial(null);
        }
        libroRepositorio.saveAll(libros);
    }

//    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String autor, String editorial) throws ErrorServicio {
//        Optional<Libro> respuesta = libroRepositorio.findById(id);
//        if (respuesta.isPresent()) {
//            Libro libro = respuesta.get();
//            libro.setIsbn(isbn);
//            libro.setTitulo(titulo);
//            libro.setAnio(anio);
//            libro.setEjemplares(ejemplares);
//            libro.setEjemplaresPrestados(ejemplaresPrestados);
//            libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
//            libroRepositorio.save(libro);
//        } else {
//            throw new ErrorServicio("No se encontró el libro ingresado");
//        }
//    }

    

    
    
    

}
