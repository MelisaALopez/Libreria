/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.repositorios;

import egg.Libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usr
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE :titulo")
    public Libro buscarLibroPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> buscarLibroPorAutor(@Param("nombre") String nombre);
    
    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombre")
    public List<Libro> buscarLibroPorEditorial(@Param("nombre") String nombre);

    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE :q or l.anio = :q or l.autor.nombre LIKE :q or l.editorial.nombre LIKE :q")
    public List<Libro> buscarLibroPorQuery(@Param("q")String q);
    
}
