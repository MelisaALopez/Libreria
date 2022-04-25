/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Usr
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    @GetMapping("")
    public String login(Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String mail){
        if(error != null){
            model.addAttribute("error", "El usuario o la contraseña ingresada es incorrecta");
        }
        if(mail != null){
            model.addAttribute("mail", mail);
        }
        return "login";
    }
    
}
