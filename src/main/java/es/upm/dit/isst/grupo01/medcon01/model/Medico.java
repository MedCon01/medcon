package es.upm.dit.isst.grupo01.medcon01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.*;

@Controller
public class LoginController {
  
    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }
    
    @GetMapping("/error")
public String showErrorPage() {
    return "error";
}

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    List<String> items = Arrays.asList("Raúl Cervantes Nuñez", "María Rodriguez Ruíz", "Ana López Bilbao");

    @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        
        model.addAttribute("items", items);
        // Agregar el atributo "username" al modelo
        model.addAttribute("username", "Arturo Pérez Sevilla");
        return "welcome";
    }

    @PostMapping("/login")
    public String processLoginForm(@RequestParam String username, @RequestParam String password, Model model) {
        // Aquí se realizaría la autenticación del usuario
        // Si el usuario es autenticado exitosamente, se mostraría la página de bienvenida
        // De lo contrario, se volvería a mostrar la página de inicio de sesión con un mensaje de error
        if (username.equals("12345678A") && password.equals("contraseña")) {
            model.addAttribute("username", username); // AQUÍ
            return "redirect:/welcome";
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "loginerror";
        }
    }

    @PostMapping("/loginerror")
    public String processLoginErrorForm(@RequestParam String username, @RequestParam String password, Model model) {
        // Aquí se realizaría la autenticación del usuario
        // Si el usuario es autenticado exitosamente, se mostraría la página de bienvenida
        // De lo contrario, se volvería a mostrar la página de inicio de sesión con un mensaje de error
        if (username.equals("12345678A") && password.equals("contraseña")) {
            model.addAttribute("username", username);
            return "welcome";
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "loginerror";
        }
    }

    
    @GetMapping("/paciente")
    public String mostrarPaciente(Model model) {
        return "paciente";
    }


}
