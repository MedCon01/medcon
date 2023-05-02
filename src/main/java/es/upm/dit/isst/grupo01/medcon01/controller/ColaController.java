package es.upm.dit.isst.grupo01.medcon01.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Consumer;

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

@Controller
public class ColaController {
    public final String GESTORCITAScitas_STRING = "http://localhost:8083/citas/";
    public final String GESTORCITASmedicos_STRING = "http://localhost:8083/medicos/";
    public final String GESTORCITASpacientes_STRING = "http://localhost:8083/pacientes/";
    private Cola cola = new Cola(1,new ArrayList<Cita>());
    Paciente ultimollamado = new Paciente("000000","00000000o","Ultimo paciente",LocalDate.now(),"000000000000",LocalTime.now(),false,LocalDateTime.now());
    public ColaController(){}
   
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/pantalla")
    public String showPantallaEspera(Model model){
        // Mostrar cola de pacientes
        List<Cita> citas = null;
        try { citas = Arrays.asList(restTemplate.getForObject(GESTORCITAScitas_STRING, Cita[].class));
        } catch (HttpClientErrorException.NotFound ex) {}
        List<Cita> citaspresentes = new ArrayList<>(); 
        for (Cita c : citas){
            Paciente p= restTemplate.getForObject(GESTORCITASpacientes_STRING + c.getPacienteId(), Paciente.class);
            if (p.getPresente().equals(true)){
                citaspresentes.add(c);
            }
        }
        // Mostrar cola llamados
        try { cola.llamados =  Arrays.asList(restTemplate.getForEntity(GESTORCITASpacientes_STRING, Paciente[].class).getBody());
        } catch (HttpClientErrorException.NotFound ex) {}
        Stream<Paciente> llamados = cola.llamados.stream()
        .filter(p -> p.getLlamado() != null)
        .sorted(Comparator.comparing(Paciente::getLlamado).reversed());
        cola.llamados = llamados.collect(Collectors.toList());
        // Si se ha llamado a un nuevo paciente, se reproducirá una alerta
        Boolean nuevallamada = false;
        if (cola.llamados.size() > 0){
            nuevallamada = !((ultimollamado.getIdpaciente().equals(cola.llamados.get(0).getIdpaciente())));
            ultimollamado = cola.llamados.get(0);
        }
        model.addAttribute("nuevallamada", nuevallamada);
        citaspresentes.sort(Comparator.comparing(c -> ((Cita) c).getHora()));
        cola.setPendientes(citaspresentes);
        model.addAttribute("salaespera", cola.getSalaEspera());
        model.addAttribute("colacitas", cola.getPendientes());
        model.addAttribute("llamados", cola.llamados);
        model.addAttribute("numllamados", cola.llamados.size());
        return "sala_espera/pantalla";
        
    }
}
