/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.servicios;

import egg.Libreria.entidades.Usuario;
import egg.Libreria.excepciones.WebException;
import egg.Libreria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usr
 */
@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public Usuario guardarUsuario(String nombre, String apellido, Integer edad, String mail, String password, String password2) throws WebException {
        Usuario usuario = new Usuario();
        if(mail == null || mail.isEmpty()){
            throw new WebException("El mail del usuario no puede estar vacío");
        }
        if(buscarUsuarioPorMail(mail) != null){
            throw new WebException("El mail ingresado ya existe");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new WebException("El nombre del usuario n0o puede estar vacío");
        }
        if(apellido == null || apellido.isEmpty()){
            throw new WebException("El apellido del usuario no puede estar vacío");
        }
        if(edad == null){
            throw new WebException("La edad del usuario no puede ser nula");
        }
        
        if(password == null || password2 == null || password.isEmpty() || password2.isEmpty()){
            throw new WebException("La contraseña no puede estar vacía");
        }
        if(!password.equals(password2)){
            throw new WebException("Las contraseñas deben ser iguales");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEdad(edad);
        usuario.setMail(mail);
        usuario.setPassword(encoder.encode(password));
        
        return usuarioRepositorio.save(usuario);
        
    }
    
    
    
    
    
    public Usuario buscarUsuarioPorMail(String mail) {
        return usuarioRepositorio.buscarUsuarioPorMail(mail);
        
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(mail);
            User user;
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("CLIENTE"));
            
            return new User(mail, usuario.getPassword(), authorities);
            
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
    }
}
