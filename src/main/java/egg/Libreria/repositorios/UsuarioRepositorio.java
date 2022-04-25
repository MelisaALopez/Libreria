/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.repositorios;

import egg.Libreria.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usr
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
    @Query("SELECT u FROM Usuario u WHERE u.mail LIKE :mail")
    public Usuario buscarUsuarioPorMail(@Param("mail")String mail);
    
}
                