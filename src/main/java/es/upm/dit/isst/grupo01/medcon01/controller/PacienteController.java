package es.upm.dit.isst.grupo01.medcon01.controller;

import java.time.LocalDate;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

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

import es.upm.dit.isst.grupo01.medcon01.model.Cita;
import es.upm.dit.isst.grupo01.medcon01.model.Medico;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.CitaRepository;
import es.upm.dit.isst.grupo01.medcon01.repository.MedicoRepository;
import es.upm.dit.isst.grupo01.medcon01.repository.PacienteRepository;

@Controller
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    private MedicoController medicoController;
    private Cita cita_pendiente;
    public PacienteController(){}
    public PacienteController(PacienteRepository pacienteRepository, CitaRepository citaRepository, MedicoRepository medicoRepository,
                            Medico medico, MedicoController medicoController){
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
        this.medicoRepository = medicoRepository;
        this.medicoController = medicoController;
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
         // Marcar paciente como presente
         paciente.setPresente(true);
         // Busco la cita del paciente
         Cita cita_pendiente = citaRepository.findByPacienteId(paciente.getId());
         model.addAttribute("cita_pendiente",cita_pendiente);
         // Busco el medico de la cita 
         Medico medico = medicoRepository.findByDni(cita_pendiente.getMedicoDni());
         model.addAttribute("medico",medico);
         // this.addPacienteCola - desarrollar
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
         // Marcar paciente como presente
         paciente.setPresente(true);
         // Busco la cita del paciente
         Cita cita_pendiente = citaRepository.findByPacienteId(paciente.getId());
         model.addAttribute("cita_pendiente",cita_pendiente);
         // Busco el medico de la cita 
         Medico medico = medicoRepository.findByDni(cita_pendiente.getMedicoDni());
         model.addAttribute("medico",medico);
         // this.addPacienteCola - desarrollar
         // Presento la informacion del paciente
         return ("/paciente/identificador_cita");
        } else {
            return ("/paciente/error_cita");
        }
    }

    @GetMapping("/informacion_cita")
    public String showInformacionCita() {
        return "paciente/informacion_cita";
    }
    @GetMapping("/error_cita")
    public String showErrorCita() {
        return "paciente/error_cita";
    }
    

}
