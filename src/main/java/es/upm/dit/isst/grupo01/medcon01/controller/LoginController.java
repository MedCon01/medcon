package es.upm.dit.isst.grupo01.medcon01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/login")
    public String processLoginForm(@RequestParam String username, @RequestParam String password, Model model) {
        // Aquí se realizaría la autenticación del usuario
        // Si el usuario es autenticado exitosamente, se mostraría la página de bienvenida
        // De lo contrario, se volvería a mostrar la página de inicio de sesión con un mensaje de error
        if (username.equals("usuario") && password.equals("contraseña")) {
            model.addAttribute("username", username);
            return "welcome";
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/welcome")
    public String showWelcomePage(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "welcome";
    }
}
