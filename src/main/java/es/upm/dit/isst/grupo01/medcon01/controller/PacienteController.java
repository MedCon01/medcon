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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import es.upm.dit.isst.grupo01.medcon01.model.Cita;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;

@Controller
public class PacienteController {
    public final String GESTORCITAScitas_STRING = "http://localhost:8083/citas/";
    public final String GESTORCITASmedicos_STRING = "http://localhost:8083/medicos/";
    public final String GESTORCITASpacientes_STRING = "http://localhost:8083/pacientes/";
    Paciente paciente;
    private RestTemplate restTemplate = new RestTemplate();
    public PacienteController(){}

    // Inicio kiosko
    @GetMapping("inicio_kiosko")
    public String showInicioKiosko(){
        return "kiosko/inicio_kiosko";
    }
    // Si se selecciona DNI
    @GetMapping("/registroDNI")
    public String showLoginDNI() {
        return "kiosko/registroDNI";
    }
    // Registro con DNI
    @PostMapping("/registroDNI")
    public String registrarPacienteDni(@RequestParam("dni") String dni,Model model){
        // Asigno paciente buscando por DNI
        List<Paciente> pacientes = null;
        try { pacientes =  Arrays.asList(restTemplate.getForEntity(GESTORCITASpacientes_STRING,Paciente[].class).getBody());
        } catch (HttpClientErrorException.NotFound ex) {
            return ("/kiosko/error_cita");
        }
        for (Paciente p : pacientes){
            if (p.getDni().equals(dni)){
                paciente = p;
            }
        }
        if (paciente != null){ // Se comprueba que el paciente existe en la BBDD 
         // Marcar paciente como presente y actualizar BBDD
        model.addAttribute("paciente",paciente);
         // Busco la cita del paciente
        List<Cita> citas = null;
        try { citas = Arrays.asList(restTemplate.getForObject(GESTORCITAScitas_STRING+ "paciente/" + paciente.getIdpaciente(), Cita[].class));
        } catch (HttpClientErrorException.NotFound ex) {
            return ("/kiosko/error_cita");
        }
        if (citas.isEmpty()){
           paciente = null;
            return ("/kiosko/sincita");
        }
         model.addAttribute("cita_pendiente",citas.get(0));
         // Presento la informacion del paciente
         paciente.setPresente(true);
         try{ restTemplate.postForObject(GESTORCITASpacientes_STRING, paciente, Paciente.class);
         } catch(Exception e) {}
         paciente = null;
         return ("/kiosko/identificador_cita");
        } else {
            return ("/kiosko/error_cita");
        }
    }

    // Si se selecciona tarjeta
    @GetMapping("/registrotarjeta")
    public String showLoginTarjeta() {
        return "kiosko/registrotarjeta";
    }
    // Registro con tarjeta
    @PostMapping("/registrotarjeta")
    public String registrarPacienteTarjeta(@RequestParam("ntarjeta") String ntarjeta,Model model){
        // Asigno paciente buscando por ntarjeta
        List<Paciente> pacientes = null;
        try { pacientes =  Arrays.asList(restTemplate.getForEntity(GESTORCITASpacientes_STRING,Paciente[].class).getBody());
        } catch (HttpClientErrorException.NotFound ex) {}
        for (Paciente p : pacientes){
            if (p.getNtarjeta().equals(ntarjeta)){
                paciente = p;
            }
        }
        if (paciente != null){ // Se comprueba que el paciente existe en la BBDD 
         // Busco la cita del paciente
        List<Cita> citas = null;
        try { citas = Arrays.asList(restTemplate.getForObject(GESTORCITAScitas_STRING+ "paciente/" + paciente.getIdpaciente(), Cita[].class));
        } catch (HttpClientErrorException.NotFound ex) {}
        if (citas.isEmpty()){
            model.addAttribute("paciente",paciente);
            paciente = null;
            return ("/kiosko/sincita");
        }
        model.addAttribute("paciente",paciente);
        model.addAttribute("cita_pendiente",citas.get(0));
         // Marcar paciente como presente y actualizar BBDD
        paciente.setPresente(true);
        try{ restTemplate.postForObject(GESTORCITASpacientes_STRING, paciente, Paciente.class);
        } catch(Exception e) {}
         // Presento la informacion del paciente
         paciente = null;
         return ("/kiosko/identificador_cita");
        } else {
            return ("/kiosko/error_cita");
        }
    }

}
