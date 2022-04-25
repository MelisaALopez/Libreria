/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.controladores;

import egg.Libreria.excepciones.WebException;
import egg.Libreria.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Usr
 */
@Controller
@RequestMapping("/registro")
public class RegistroController {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("")
    public String registro(){
        return "registro";
    }
    
    @PostMapping("")
    public String registroGuardar(Model model, @RequestParam String nombre, @RequestParam String apellido, @RequestParam Integer edad, @RequestParam String mail, @RequestParam String password, @RequestParam String password2){
        try {
            usuarioServicio.guardarUsuario(nombre, apellido, edad, mail, password, password2);
            return "redirect:/";
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("nombre", nombre);
            model.addAttribute("apellido", apellido);
            model.addAttribute("edad", edad);
            model.addAttribute("mail", mail);
            return "registro";
        }
        
    }
    
}
