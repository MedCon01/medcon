package es.upm.dit.isst.grupo01.medcon01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.grupo01.medcon01.model.Cita;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.CitaRepository;
import es.upm.dit.isst.grupo01.medcon01.repository.PacienteRepository;

@Controller
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private CitaRepository citaRepository;
    public PacienteController(){}
    public PacienteController(PacienteRepository pacienteRepository, CitaRepository citaRepository){
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
    }
    // Inicio kiosko
    @GetMapping("inicio_kiosko")
    public String showInicioKiosko(){
        return "paciente/inicio_kiosko";
    }
    // Si se selecciona DNI
    @GetMapping("/login_DNI")
    public String showLoginDNI() {
        return "paciente/login_DNI";
    }
    // Registro con DNI
    @PostMapping("/login_DNI")
    public String registrarPacienteDni(@RequestParam("dni") String dni,Model model){
        // Asigno paciente buscando por DNI
        Paciente paciente = pacienteRepository.findByDni(dni);
        model.addAttribute("paciente",paciente);
        if (paciente != null){ // Se comprueba que el paciente existe en la BBDD 
         // Marcar paciente como presente y actualizar BBDD
         paciente.setPresente(true);
         pacienteRepository.save(paciente);
         // Busco la cita del paciente
         Cita cita_pendiente = citaRepository.findByPacienteId(paciente.getIdpaciente());
         model.addAttribute("cita_pendiente",cita_pendiente);
         // Presento la informacion del paciente
         return ("/paciente/identificador_cita");
        } else {
            return ("/paciente/error_cita");
        }
    }

    // Si se selecciona tarjeta
    @GetMapping("/login_tarjeta")
    public String showLoginTarjeta() {
        return "paciente/login_tarjeta";
    }
    // Registro con tarjeta
    @PostMapping("/login_tarjeta")
    public String registrarPacienteTarjeta(@RequestParam("ntarjeta") String ntarjeta,Model model){
        // Asigno paciente buscando por ntarjeta
        Paciente paciente = pacienteRepository.findByNtarjeta(ntarjeta);
        model.addAttribute("paciente",paciente);
        if (paciente != null){ // Se comprueba que el paciente existe en la BBDD 
         // Marcar paciente como presente y actualizar BBDD
         paciente.setPresente(true);
         pacienteRepository.save(paciente);
         // Busco la cita del paciente
         Cita cita_pendiente = citaRepository.findByPacienteId(paciente.getIdpaciente());
         model.addAttribute("cita_pendiente",cita_pendiente);
         // Presento la informacion del paciente
         return ("/paciente/identificador_cita");
        } else {
            return ("/paciente/error_cita");
        }
    }

}
