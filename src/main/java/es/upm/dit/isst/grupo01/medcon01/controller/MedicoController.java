package es.upm.dit.isst.grupo01.medcon01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.grupo01.medcon01.model.Paciente;

import java.util.List;
import java.util.*;

@Controller
public class MedicoController {
    String tiempoConsulta= "00:00:00";

    @GetMapping("/")
    public String showIndexPage() {
        return "medico/index";
    }
    
    @GetMapping("/error")
public String showErrorPage() {
    return "medico/error";
}

    @GetMapping("/login")
    public String showLoginPage() {
        return "medico/login";
    }
    List<String> items = Arrays.asList("Raúl Cervantes Nuñez", "María Rodriguez Ruíz", "Ana López Bilbao");

    @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        model.addAttribute("tiempoConsulta", tiempoConsulta);
        model.addAttribute("items", items);
        // Agregar el atributo "username" al modelo
        model.addAttribute("username", "Arturo Pérez Sevilla");
        return "medico/welcome";
    }

    @PostMapping("/login")
    public String processLoginForm(@RequestParam String username, @RequestParam String password, Model model) {
        // Aquí se realizaría la autenticación del usuario
        // Si el usuario es autenticado exitosamente, se mostraría la página de bienvenida
        // De lo contrario, se volvería a mostrar la página de inicio de sesión con un mensaje de error
        if (username.equals("12345678A") && password.equals("contraseña")) {
            model.addAttribute("username", username); // AQUÍ
            return "redirect:/medico/welcome";
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "medico/loginerror";
        }
    }

    @PostMapping("/loginerror")
    public String processLoginErrorForm(@RequestParam String username, @RequestParam String password, Model model) {
        // Aquí se realizaría la autenticación del usuario
        // Si el usuario es autenticado exitosamente, se mostraría la página de bienvenida
        // De lo contrario, se volvería a mostrar la página de inicio de sesión con un mensaje de error
        if (username.equals("12345678A") && password.equals("contraseña")) {
            model.addAttribute("username", username);
            return "medico/welcome";
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
            return "medico/loginerror";
        }
    }

    
    @GetMapping("/paciente")
    public String mostrarPaciente(Model model) {
        return "medico/paciente";
    }

    private List<Paciente> pacientes = new ArrayList<>();
    
    public void addPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }
    
    @GetMapping("/paciente")
    public String mostrarListaPacientes(Model model) {
        model.addAttribute("pacientes", pacientes);
        return "medico/paciente";
    }


}
