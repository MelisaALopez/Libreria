/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.controladores;


import egg.Libreria.entidades.Editorial;
import egg.Libreria.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/list")
    public String listarEditoriales(Model model, @RequestParam(required = false) String q){
        if(q != null){
            model.addAttribute("editoriales", editorialServicio.listarEditoriales(q));
        } else {
            model.addAttribute("editoriales", editorialServicio.listarEditoriales());
        }
        return "editorial-list";
    }
    
    @GetMapping("/form")
    public String crearEditorial(Model model, @RequestParam(required = false) String id){
        if (id != null){
            Optional<Editorial> editorial = editorialServicio.buscarEditorialPorId(id);
            if (editorial != null){
                model.addAttribute("editorial", editorial);
            } else {
                return "redirect:/editorial/list";
            }
        } else {
            model.addAttribute("editorial", new Editorial());
        }
        return "editorial-form";
    }
    
    @PostMapping("/save")
    public String cargarEditorial(RedirectAttributes redirectAttributes, @ModelAttribute Editorial editorial){
        try {
            editorialServicio.guardarEditorial(editorial);
            redirectAttributes.addFlashAttribute("success", "Editorial guardada con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }     
        return "redirect:/editorial/list";
    }
    
    @GetMapping("/delete")
    public String eliminarEditorial(@RequestParam(required = true) String id){
        editorialServicio.eliminarEditorialPorId(id);
        return "redirect:/editorial/list";
    }
    
}
