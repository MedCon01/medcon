package es.upm.dit.isst.grupo01.medcon01.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import es.upm.dit.isst.grupo01.medcon01.model.Cola;
import es.upm.dit.isst.grupo01.medcon01.model.Medico;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.MedicoRepository;
import es.upm.dit.isst.grupo01.medcon01.repository.PacienteRepository;

@Controller
public class ColaController {
    public final String GESTORCITAS_STRING = "http://localhost:8080/citas/";
    private Cola cola = new Cola(1,new ArrayList<Cita>());
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    
    public ColaController(){}
   
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/pantalla")
    public String showPantallaEspera(Model model){
        List<Cita> citas = null;
        try { citas = Arrays.asList(restTemplate.getForObject(GESTORCITAS_STRING, Cita[].class));
        } catch (HttpClientErrorException.NotFound ex) {}
        List<Cita> citaspresentes = new ArrayList<>();
        for (Cita c : citas){
            Paciente paciente = pacienteRepository.findByIdpaciente(c.getPacienteId());
            if (paciente.getPresente().equals(true)){
                citaspresentes.add(c);
            }
        }
        citaspresentes.sort(Comparator.comparing(c -> ((Cita) c).getHora()));
        cola.setPendientes(citaspresentes);
        model.addAttribute("salaespera", cola.getSalaEspera());
        model.addAttribute("colacitas", cola.getPendientes());
        return "sala_espera/pantalla";
    }
}
