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
import java.util.regex.*;

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
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    Medico medico;
    
    // Constructor vacío
    public MedicoController(){}

    // Constructor normal
    public MedicoController(MedicoRepository medicoRepository, CitaRepository citaRepository, PacienteRepository pacienteRepository, Medico medico){
        this.medicoRepository = medicoRepository;
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medico=medico;
    }

    // Login_medico
    @GetMapping("/login_medico")
    public String showLoginPage(){
        return "medico/login_medico";
    }
    @PostMapping("/login_medico")
    public String processLoginForm(@RequestParam("usuario") String usuario, @RequestParam("password") String password,Model model){
        if (usuario.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
            // Validar DNI del medico
            this.medico = medicoRepository.findByDni(usuario);
            if (medico.getPassword().equals(password)){
                model.addAttribute("medico", medico);
                return "redirect:/iniciomedico/" + medico.getDni();
            } else{
                return "medico/loginmedicoerror";
            }
        } else if (usuario.matches("\\d{12}")){
            this.medico = medicoRepository.findByNcolegiado(usuario);
            if (medico.getPassword().equals(password)){
                model.addAttribute("medico", medico);
                return "redirect:/iniciomedico/" + medico.getDni();
            } else {
                return "medico/loginmedicoerror";
            }
        } else {
            return "medico/loginmedicoerror";
        }
    }
    // Loginmedicoerror
    @PostMapping("/loginmedicoerror")
    public String processLoginFormAgain(@RequestParam("usuario") String usuario, @RequestParam("password") String password,Model model){
        if (usuario.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
            // Validar DNI del medico
            this.medico = medicoRepository.findByDni(usuario);
            if (medico.getPassword().equals(password)){
                model.addAttribute("medico", medico);
                return "redirect:/iniciomedico/" + medico.getDni();
            } else{
                return "medico/loginmedicoerror";
            }
        } else if (usuario.matches("\\d{12}")){
            this.medico = medicoRepository.findByNcolegiado(usuario);
            if (medico.getPassword().equals(password)){
                model.addAttribute("medico", medico);
                return "redirect:/iniciomedico/" + medico.getDni();
            } else {
                return "medico/loginmedicoerror";
            }
        } else {
            return "medico/loginmedicoerror";
        }
    }
    // Iniciomedico
    @GetMapping("/iniciomedico/{medico}")
    public String getIniciomedico(Model model, @PathVariable(value = "medico") String medicoDni) {
    medico = medicoRepository.findByDni(medicoDni);
    model.addAttribute("medico", medico);
    List<Cita> citas = citaRepository.findAllByMedicoDni(medico.getDni());
    List<Cita> citas_pendientes = new ArrayList<Cita>(); 
    List<Paciente> pacientes_pendientes = new ArrayList<Paciente>(); 
    for (Cita c : citas){
            String pacienteId = c.getPacienteId();
            Paciente paciente = pacienteRepository.findByIdpaciente(pacienteId);
            if (paciente.getPresente().equals(true)){
                pacientes_pendientes.add(paciente);
                citas_pendientes.add(c);

            }
    }
    model.addAttribute("pacientes", pacientes_pendientes); 
    model.addAttribute("citas_pendientes", citas_pendientes);   
        return "medico/iniciomedico";
    }
}
