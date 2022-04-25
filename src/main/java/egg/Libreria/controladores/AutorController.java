/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.controladores;


import egg.Libreria.entidades.Autor;
import egg.Libreria.servicios.AutorServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Usr
 */
@Controller
@RequestMapping("/autor")
public class AutorController {
    
    //Instancia un solo objeto de tipo autorRepositorio(Spring solo trabaja con ese objeto)
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/list")
    public String listarAutores(Model model, @RequestParam(required = false) String q){
        if(q != null){
            model.addAttribute("autores", autorServicio.listarAutores(q));
        } else {
             model.addAttribute("autores", autorServicio.listarAutores());
        }
        return "autor-list";
    }
    
    @GetMapping("/form")
    public String crearAutor(Model model, @RequestParam(required = false) String id){
        if (id != null){
            Optional<Autor> autor = autorServicio.buscarAutorPorId(id);
            if (autor != null){
                model.addAttribute("autor", autor);
            } else {
                return "redirect:/autor/list";
            }
        } else {
            model.addAttribute("autor", new Autor());
        }
        return "autor-form";
    }  
    
    @PostMapping("/save")
    public String cargarAutor(RedirectAttributes redirectAttributes, @ModelAttribute Autor autor){
        try {
            autorServicio.guardarAutor(autor);
            redirectAttributes.addFlashAttribute("success", "Autor guardado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } 
        return "redirect:/autor/list";
    }
    
        
    @GetMapping("/delete")
    public String eliminarAutor(@RequestParam(required = true) String id){
        autorServicio.eliminarAutorPorId(id);
        return "redirect:/autor/list";
    }
}
