package es.upm.dit.isst.grupo01.medcon01.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import es.upm.dit.isst.grupo01.medcon01.model.Cita;
import es.upm.dit.isst.grupo01.medcon01.model.Medico;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;

@Controller
public class MedicoController {
    public final String GESTORCITAScitas_STRING = "http://localhost:8083/citas/";
    public final String GESTORCITASmedicos_STRING = "http://localhost:8083/medicos/";
    public final String GESTORCITASpacientes_STRING = "http://localhost:8083/pacientes/";
    Medico medico;
    Paciente pacientellamado;
    private RestTemplate restTemplate = new RestTemplate();
    ArrayList<Long> tiempos = new ArrayList<Long>();
    long tiempoinicio;
    long tiempofinal;
    long tiempototal_ms;
    long tiempomedio_ms;
    String tiempo_medio;
    String tiempo_total;

    
    // Constructor vacío
    public MedicoController(){}

    // Login_medico
    @GetMapping("/login_medico")
    public String showLoginPage(){
        return "medico/login_medico";
    }


    @PostMapping("/login_medico")
    public String processLoginForm(@RequestParam("usuario") String usuario, @RequestParam("password") String password,Model model){
        if (usuario.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
            // Validar DNI del medico
            try { medico = restTemplate.getForObject(GESTORCITASmedicos_STRING + usuario, Medico.class);
            } catch (HttpClientErrorException.NotFound ex) {}
            if (medico.getPassword().equals(password)){
                model.addAttribute("medico", medico);
                return "redirect:/iniciomedico/" + medico.getDni();
            } else{
                return "medico/loginmedicoerror";
            }
        } else if (usuario.matches("\\d{12}")){
            List<Medico> medicos = null;
            try { medicos =  Arrays.asList(restTemplate.getForEntity(GESTORCITASmedicos_STRING,Medico[].class).getBody());
            } catch (HttpClientErrorException.NotFound ex) {}
            for (Medico m : medicos){
                if (m.getNColegiado().equals(usuario)){
                    medico = m;
                }
            }
            if (medico != null){
                if (medico.getPassword().equals(password)){
                    model.addAttribute("medico", medico);
                    return "redirect:/iniciomedico/" + medico.getDni();
                } else {
                    return "medico/loginmedicoerror";
                }
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
            try { medico = restTemplate.getForObject(GESTORCITASmedicos_STRING + usuario, Medico.class);
            } catch (HttpClientErrorException.NotFound ex) {}
            if (medico.getPassword().equals(password)){
                model.addAttribute("medico", medico);
                return "redirect:/iniciomedico/" + medico.getDni();
            } else{
                return "medico/loginmedicoerror";
            }
        } else if (usuario.matches("\\d{12}")){
            List<Medico> medicos = null;
            try { medicos =  Arrays.asList(restTemplate.getForEntity(GESTORCITASmedicos_STRING,Medico[].class).getBody());
            } catch (HttpClientErrorException.NotFound ex) {}
            for (Medico m : medicos){
                if (m.getNColegiado().equals(usuario)){
                    medico = m;
                }
            }
            if (medico != null){
                if (medico.getPassword().equals(password)){
                    model.addAttribute("medico", medico);
                    return "redirect:/iniciomedico/" + medico.getDni();
                } else {
                    return "medico/loginmedicoerror";
                }
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
        try { medico = restTemplate.getForObject(GESTORCITASmedicos_STRING + medicoDni, Medico.class);
        } catch (HttpClientErrorException.NotFound ex) {}
    model.addAttribute("medico", medico);
    List<Cita> citas = null;
    try { citas = Arrays.asList(restTemplate.getForObject(GESTORCITAScitas_STRING+ "medico/" + medico.getDni(), Cita[].class));
    } catch (HttpClientErrorException.NotFound ex) {}
    List<Cita> citas_pendientes = new ArrayList<Cita>(); 
    List<Paciente> pacientes_pendientes = new ArrayList<Paciente>(); 
    for (Cita c : citas){
            String pacienteId = c.getPacienteId();
            Paciente paciente = null;
            try { paciente = restTemplate.getForObject(GESTORCITASpacientes_STRING + c.getPacienteId(), Paciente.class);
            } catch (HttpClientErrorException.NotFound ex) {}
            if (paciente.getPresente().equals(true)){
                pacientes_pendientes.add(paciente);
                citas_pendientes.add(c);
            }
    }
    citas_pendientes.sort(Comparator.comparing(c -> ((Cita) c).getHora()));
    model.addAttribute("tiempototal", tiempo_total);
    model.addAttribute("tiempomedio", tiempo_medio);
    model.addAttribute("pacientes", pacientes_pendientes); 
    model.addAttribute("citas_pendientes", citas_pendientes);  
        return "medico/iniciomedico";
    }
    //llamada a siguiente paciente 
    @GetMapping("/siguiente_paciente")
    public String showPacientePage(@RequestParam("idpaciente") String idpaciente, Model model){
        //Buscar paciente en API citas
        try { pacientellamado = restTemplate.getForObject(GESTORCITASpacientes_STRING + idpaciente, Paciente.class);
        } catch (HttpClientErrorException.NotFound ex) {}
        //Llamar
        pacientellamado.setLlamado(LocalDateTime.now());
        // Guardar paciente en API citas
        try{ restTemplate.postForObject(GESTORCITASpacientes_STRING, pacientellamado, Paciente.class);
        } catch(Exception e) {}
        model.addAttribute("pacientellamado",pacientellamado);
        tiempoinicio = System.currentTimeMillis();
        return "redirect:/paciente/" + pacientellamado.getIdpaciente();
    }
    // Consulta en curso
    @GetMapping("/paciente/{paciente}")
    public String showConsultaEnCursoPage(Model model, @PathVariable(value = "paciente") String idpaciente){
        try { pacientellamado = restTemplate.getForObject(GESTORCITASpacientes_STRING + idpaciente, Paciente.class);
        } catch (HttpClientErrorException.NotFound ex) {}
        model.addAttribute("pacientellamado",pacientellamado);
        return "medico/paciente"; 
    }

    @GetMapping("/historial")
    public String showhistorial(){
        return "aplicaciones_externas/historial";
    }

    @GetMapping("/receta")
    public String showreceta(){
        return "aplicaciones_externas/receta";
    }

    @GetMapping("/pruebas_medicas")
    public String showpruebasMedicas(){
        return "aplicaciones_externas/pruebas_medicas";
    }

    @GetMapping("/gestion_citas")
    public String showgestion_citas(){
        return "aplicaciones_externas/gestion_citas";
    }

    @GetMapping("/suspender_consulta")
    public String showsuspenderConsulta(Model model){
       pacientellamado.setPresente(true);
       try{ restTemplate.postForObject(GESTORCITASpacientes_STRING, pacientellamado, Paciente.class);
       } catch(Exception e) {}
    List<Cita> citas = null;
    try { citas = Arrays.asList(restTemplate.getForObject(GESTORCITAScitas_STRING+ "medico/" + medico.getDni(), Cita[].class));
    } catch (HttpClientErrorException.NotFound ex) {}
    List<Cita> citas_pendientes = new ArrayList<Cita>(); 
    List<Paciente> pacientes_pendientes = new ArrayList<Paciente>(); 
    boolean pacienteConsulta = false; 
    Cita citaPacienteLlamado = null;
    for (Cita c : citas){
             String pacienteId = c.getPacienteId();
             Paciente paciente = null;
             try { paciente = restTemplate.getForObject(GESTORCITASpacientes_STRING + c.getPacienteId(), Paciente.class);
             } catch (HttpClientErrorException.NotFound ex) {}   
            if(paciente.getIdpaciente().equals(pacientellamado.getIdpaciente())){
                pacienteConsulta = true;
                citaPacienteLlamado = c;
            } else if(paciente.getPresente().equals(true)) {
              pacientes_pendientes.add(paciente);
              citas_pendientes.add(c);
            }
    }
    if (pacienteConsulta==true){
        pacientes_pendientes.add(1, pacientellamado);
        citas_pendientes.add(1, citaPacienteLlamado);
    }
    // Actualizar el modelo con la lista de pacientes pendientes modificada
    model.addAttribute("pacientes", pacientes_pendientes);
    model.addAttribute("citas_pendientes", citas_pendientes);

       return "redirect:/iniciomedico/" + medico.getDni();
    }

    @GetMapping("/finalizar_consulta")
    public String showsfinalizarConsulta(Model model){
        pacientellamado.setPresente(false);
        pacientellamado.setLlamado(null);
        // Guardar paciente en API citas
        try{ restTemplate.postForObject(GESTORCITASpacientes_STRING, pacientellamado, Paciente.class);
        } catch(Exception e) {}
        tiempofinal=System.currentTimeMillis();
        long tiempoconsulta = tiempofinal - tiempoinicio;
        tiempos.add(tiempoconsulta);
        for (int i = 0; i < tiempos.size(); i++) {
            tiempototal_ms += tiempos.get(i);
            tiempomedio_ms = tiempototal_ms / tiempos.size();
        }

        int segundos_totales = (int) (tiempototal_ms / 1000);
        int minutos_totales = segundos_totales / 60;
        int horas_totales = minutos_totales / 60;
        int segundos_medios = (int) (tiempomedio_ms / 1000);
        int minutos_medios = segundos_medios / 60;
        int horas_medios = minutos_medios / 60;
    
        int segundos_restantes_totales = segundos_totales % 60;
        int minutos_restantes_totales = minutos_totales % 60;
        int horas_restantes_totales = horas_totales;
    
        int segundos_restantes_medios = segundos_medios % 60;
        int minutos_restantes_medios = minutos_medios % 60;
        int horas_restantes_medios = horas_medios;
    
        if (segundos_restantes_totales == 60) {
            segundos_restantes_totales = 0;
            minutos_restantes_totales++;
        }
        if (minutos_restantes_totales == 60) {
            minutos_restantes_totales = 0;
            horas_restantes_totales++;
        }
    
        if (segundos_restantes_medios == 60) {
            segundos_restantes_medios = 0;
            minutos_restantes_medios++;
        }
        if (minutos_restantes_medios == 60) {
            minutos_restantes_medios = 0;
            horas_restantes_medios++;
        }
    
        tiempo_medio = String.format("%02d:%02d:%02d", horas_restantes_medios, minutos_restantes_medios, segundos_restantes_medios);
        tiempo_total = String.format("%02d:%02d:%02d", horas_restantes_totales, minutos_restantes_totales, segundos_restantes_totales);
    
        return "redirect:/iniciomedico/" + medico.getDni();
    }
    
}
