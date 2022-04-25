/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.controladores;

import egg.Libreria.entidades.Libro;
import egg.Libreria.servicios.AutorServicio;
import egg.Libreria.servicios.EditorialServicio;
import egg.Libreria.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("")
    public String Libro() {
        return "libro";
    }

    @GetMapping("/list")
    public String listarLibros(Model model, @RequestParam(required = false) String q) {
        if(q != null){
            model.addAttribute("libros", libroServicio.listarLibros(q));
        } else {
            model.addAttribute("libros", libroServicio.listarLibros());
        }
        return "libro-list";
    }

    @GetMapping("/form")
    public String crearLibro(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Libro> libro = libroServicio.buscarLibroPorId(id);
            if (libro != null) {
                model.addAttribute("libro", libro);
            } else {
                return "redirect:/libro/list";
            }
        } else {
            model.addAttribute("libro", new Libro());
        }
        model.addAttribute("autores", autorServicio.listarAutores());
        model.addAttribute("editoriales", editorialServicio.listarEditoriales());
        return "libro-form";
    }

    @PostMapping("/save")
    public String cargarLibro(RedirectAttributes redirectAttributes, @ModelAttribute Libro libro) {
        try {
            libroServicio.guardarLibro(libro);
            redirectAttributes.addFlashAttribute("success", "Libro guardado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/libro/list";
    }

    @GetMapping("/delete")
    public String eliminarLibro(@RequestParam(required = true) String id) {
        libroServicio.eliminarLibroPorId(id);
        return "redirect:/libro/list";
    }

}
