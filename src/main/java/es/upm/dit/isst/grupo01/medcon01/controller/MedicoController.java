package es.upm.dit.isst.grupo01.medcon01.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import es.upm.dit.isst.grupo01.medcon01.model.Medico;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.MedicoRepository;


@Controller
public class MedicoController {
   private MedicoRepository medicoRepository;
   public MedicoController(MedicoRepository medicoRepository) {
     this.medicoRepository = medicoRepository;
     }
    public MedicoController(){}
    private String tiempoConsulta= "00:00:00"; //esto va en el modelo
     
    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }
    
    @GetMapping("/error")
    public String showErrorPage() {
        return "medico/error";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "medico/login_medico";
    }
    // esto se tiene que emplear la base de datos
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
            return "medico/welcome";
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

}
