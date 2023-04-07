package es.upm.dit.isst.grupo01.medcon01.controller;

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
    // Aquí va el código para validar el DNI
    if (dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
        Paciente paciente = new Paciente();
        paciente.setPresente(true);
        PacienteRepository.save(paciente);
        model.addAttribute("paciente", paciente);
        return "redirect:/informacion_cita";
    } else {
        return "redirect:/error_cita";
    } 
}

    @PostMapping("/login_tarjeta")
    public String registratPacienteTarjeta(@RequestParam("n_tarjeta") String n_tarjeta, Model model ) {
        // Aquí va el código para validar el número
        if (n_tarjeta.length() == 10 && n_tarjeta.matches("[0-9]+")) {
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
