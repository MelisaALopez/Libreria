/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.servicios;

import egg.Libreria.entidades.Editorial;
import egg.Libreria.excepciones.WebException;
import egg.Libreria.repositorios.EditorialRepositorio;
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
public class EditorialServicio {
    
    //Defino como atributo EditorialRepositorio
    @Autowired //El objeto queda instanciado
    private EditorialRepositorio editorialRepositorio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    //Cuando se modifica la base de datos se usa Transactional
    @Transactional
    public Editorial guardarEditorial(String nombre){
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        return editorialRepositorio.save(editorial);
    }
    
    @Transactional
    public Editorial guardarEditorial(Editorial editorial) throws WebException{
        if(editorial.getNombre().isEmpty() || editorial.getNombre() == null){
            throw new WebException("El nombre no puede ser nulo");
        }
        return editorialRepositorio.save(editorial);
    }
  
    public List<Editorial> listarEditoriales(){
        return editorialRepositorio.findAll();
    }
    
    public List<Editorial> listarEditoriales(String q){
        return editorialRepositorio.buscarEditorialPorQuery("%"+q+"%");
    }
    
    public Optional<Editorial> buscarEditorialPorId(String id) {
        return editorialRepositorio.findById(id);
    }
    
    public Editorial buscarEditorial(Editorial editorial) {
        Optional<Editorial> opcional = editorialRepositorio.findById(editorial.getId());
        if (opcional.isPresent()) {
            editorial = opcional.get();
        }
        return editorial;
    }
    
    public Editorial buscarEditorial(String nombre) {
        return editorialRepositorio.buscarEditorialPorNombre(nombre);
    }
    
    public EditorialRepositorio getEditorialRepositorio() {
        return editorialRepositorio;
    }

    public void setEditorialRepositorio(EditorialRepositorio editorialRepositorio) {
        this.editorialRepositorio = editorialRepositorio;
    }
    
    @Transactional
    public void eliminarEditorial(Editorial editorial){
        editorialRepositorio.delete(editorial);
    }
    
    @Transactional
    public void eliminarEditorial(String nombre){
        Editorial editorial = editorialRepositorio.buscarEditorialPorNombre(nombre);
        editorialRepositorio.delete(editorial);
    }
    
    @Transactional
    public void eliminarEditorialPorId(String id){
        Optional<Editorial> editorial = editorialRepositorio.findById(id);
        if(editorial.isPresent()){
            libroServicio.eliminarCampoEditorial(editorial.get());
            editorialRepositorio.delete(editorial.get());
        }   
    }
    
    
    
}
