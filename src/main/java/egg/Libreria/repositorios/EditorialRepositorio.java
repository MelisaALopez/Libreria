/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.repositorios;

import egg.Libreria.entidades.Editorial;
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
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    @Query("SELECT e FROM Editorial e WHERE e.nombre LIKE :nombre")
    public Editorial buscarEditorialPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre LIKE :q")
    public List<Editorial> buscarEditorialPorQuery(@Param("q")String q);
    
}
