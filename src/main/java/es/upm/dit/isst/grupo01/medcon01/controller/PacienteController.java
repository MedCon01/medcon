package es.upm.dit.isst.grupo01.medcon01.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.PacienteRepository;



@Controller
public class PacienteController {

    @Autowired
    private PacienteRepository PacienteRepository;

    @Autowired 
    private MedicoController medicoController;
    
    @GetMapping("/inicio_kiosko")
    public String showInicioKiosko() {
        return "inicio_kiosko";
    }
  
    @GetMapping("/login_DNI")
    public String showLoginDNI() {
        return "login_DNI";
    }

    @GetMapping("/login_tarjeta")
    public String showLoginTarjeta() {
        return "login_tarjeta";
    }

    @GetMapping("/error_cita")
    public String showErrorCita() {
        return "error_cita";
    }

    @GetMapping("/informacion_cita")
    public String showInformacionCita() {
        return "informacion_cita";
    }
   

    
    @PostMapping("/login_DNI")
   public String registrarPacienteDNI(@RequestParam("dni") String dni, Model model) {
    // Buscamos el paciente en la base de datos
    Paciente paciente = PacienteRepository.findByDni(dni);
    if (paciente != null) {
        model.addAttribute("error", "El paciente ya está registrado.");
        return "redirect:/error_registro";
    }
    // Aquí va el código para validar el DNI
    if (dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
       // Paciente paciente = new Paciente();
        paciente.setPresente(true);
        paciente = PacienteRepository.save(paciente);
        medicoController.addPaciente(paciente);
        model.addAttribute("paciente", paciente);
        return "redirect:/informacion_cita";
    } else {
        return "redirect:/error_cita";
    } 
}

    @PostMapping("/login_tarjeta")
    public String registratPacienteTarjeta(@RequestParam("numTarjeta") String numTarjeta, Model model ) {
        // Aquí va el código para validar el numero
        if (numTarjeta.length() == 10 && numTarjeta.matches("[0-9]+")) {
            Paciente paciente = new Paciente();
            paciente.setPresente(true);
            paciente = PacienteRepository.save(paciente);
            model.addAttribute("paciente", paciente);
            return "redirect:/informacion_cita";
        } else {
            return "redirect:/error_cita";
        }
    }
}
