package es.upm.dit.isst.grupo01.medcon01.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    Boolean error = false;
    Paciente pacientellamado;
    private RestTemplate restTemplate = new RestTemplate();
    ArrayList<Long> tiempos1 = new ArrayList<Long>();
    ArrayList<Long> tiempos2 = new ArrayList<Long>();
    ArrayList<Long> tiempos3 = new ArrayList<Long>();
    ArrayList<Long> tiempos4 = new ArrayList<Long>();
    ArrayList<Long> tiempos5 = new ArrayList<Long>();
    long tiempoinicio;
    long tiempofinal;
    long tiempototal_ms;
    long tiempomedio_ms;
    String tiempo_medio1;
    String tiempo_total1;
    String tiempo_medio2;
    String tiempo_total2;
    String tiempo_medio3;
    String tiempo_total3;
    String tiempo_medio4;
    String tiempo_total4;
    String tiempo_medio5;
    String tiempo_total5;
    // lista de pacientes al dar a suspender consulta
    List<Cita> citas_actualizadas = new ArrayList<Cita>(); 
    List<Paciente> pacientes_actualizados = new ArrayList<Paciente>(); 
    boolean suspenderconsulta = false;

    
    // Constructor vacío
    public MedicoController(){} 

    // Login_medico
    @GetMapping("/login_medico")
    public String showLoginPage(){
        return "medico/login_medico.html";
    }


    @PostMapping("/login_medico")
    public String processLoginForm(Authentication auth,Model model){
        String username = auth.getName();
                // if (username.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
            // Validar DNI del medico
            try { medico = restTemplate.getForObject(GESTORCITASmedicos_STRING + username, Medico.class);
            } catch (HttpClientErrorException.NotFound ex) {}
           // if (medico.getPassword().equals(password)){
                model.addAttribute("medico", medico);
                return "redirect:/iniciomedico/";
            
            /*
             * } else{
                model.addAttribute("error",true);
                return "medico/login_medico";
            }
             */
         /*
   
        } else (username.matches("\\d{12}")){
            List<Medico> medicos = null;
            try { medicos =  Arrays.asList(restTemplate.getForEntity(GESTORCITASmedicos_STRING,Medico[].class).getBody());
            } catch (HttpClientErrorException.NotFound ex) {}
            for (Medico m : medicos){
                if (m.getNColegiado().equals(username)){
                    medico = m;
                }
            }
            /*
             * if (medico != null){
               if (medico.getPassword().equals(password)){
                    model.addAttribute("medico", medico);
                    return "redirect:/iniciomedico/" + medico.getDni();
                } else {
                    model.addAttribute("error",true);
                    return "medico/login_medico";
                }
            } else {
                model.addAttribute("error",true);
                return "medico/login_medico";
            }
        
             
        }else {
        model.addAttribute("error",true);
        return "medico/login_medico";
        }
        */
    }

    // Iniciomedico
    @GetMapping("/iniciomedico")
     public String getIniciomedico(Model model, Authentication auth) {
        if(auth == null){
            return "redirect:/login_medico";
        }
        String medicoDni = auth.getName();
        try { medico = restTemplate.getForObject(GESTORCITASmedicos_STRING + medicoDni, Medico.class);
        } catch (HttpClientErrorException.NotFound ex) {
            return "redirect:/login_medico?error=true";
        }
        model.addAttribute("medico", medico);
        if (suspenderconsulta == false){
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
                    Comparator<Cita> citaComparator = Comparator.comparing(Cita::getHora);
                    citas_pendientes.sort(citaComparator);
            }
        model.addAttribute("pacientes", pacientes_pendientes); 
        model.addAttribute("citas_pendientes", citas_pendientes);  
    }
    // aqui solo se metera si se ha pulsado el boton de suspender consulta
    if (suspenderconsulta == true){
        model.addAttribute("pacientes", pacientes_actualizados); 
        model.addAttribute("citas_pendientes", citas_actualizadas); 
        suspenderconsulta = false; 
    }
   
        if (medico.getDni().equals("11111111A")){
            model.addAttribute("tiempototal", tiempo_total1);
            model.addAttribute("tiempomedio", tiempo_medio1);
            } else if(medico.getDni().equals("22222222B")){
                model.addAttribute("tiempototal", tiempo_total2);
                model.addAttribute("tiempomedio", tiempo_medio2);
                } else if(medico.getDni().equals("33333333C")){
                    model.addAttribute("tiempototal", tiempo_total3);
                    model.addAttribute("tiempomedio", tiempo_medio3);
                    }else if(medico.getDni().equals("44444444D")){
                        model.addAttribute("tiempototal", tiempo_total4);
                        model.addAttribute("tiempomedio", tiempo_medio4);
                        }else if(medico.getDni().equals("55555555E")){
                            model.addAttribute("tiempototal", tiempo_total5);
                            model.addAttribute("tiempomedio", tiempo_medio5);
                        }else {
                            model.addAttribute("tiempototal", 00);
                            model.addAttribute("tiempomedio", 00);
                        }
        return "medico/iniciomedico";
    }
    //llamada a siguiente paciente 
    @GetMapping("/siguiente_paciente")
    public String showPacientePage(@RequestParam("idpaciente") String idpaciente, Model model){
        suspenderconsulta = false;
        //Buscar paciente en API citas
        try { pacientellamado = restTemplate.getForObject(GESTORCITASpacientes_STRING + idpaciente, Paciente.class);
        } catch (HttpClientErrorException.NotFound ex) {}
        //Llamar
        pacientellamado.setLlamado(LocalDateTime.now());
        pacientellamado.setConsultallamada(medico.getConsulta());
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
    public String showhistorial(Model model){
        model.addAttribute("paciente",pacientellamado);
        model.addAttribute("medico", medico);
        return "aplicaciones_externas/historial";
    }

    @GetMapping("/receta")
    public String showreceta(Model model){
        model.addAttribute("paciente",pacientellamado);
        model.addAttribute("medico", medico);
        return "aplicaciones_externas/receta";
    }

    @GetMapping("/pruebas_medicas")
    public String showpruebasMedicas(Model model){
        model.addAttribute("paciente",pacientellamado);
        model.addAttribute("medico", medico);
        return "aplicaciones_externas/pruebas_medicas";
    }

    @GetMapping("/gestion_citas")
    public String showgestion_citas(Model model){
        model.addAttribute("paciente",pacientellamado);
        model.addAttribute("medico", medico);
        return "aplicaciones_externas/gestion_citas";
    }
    
    @GetMapping("/suspender_consulta")
    public String showsuspenderConsulta(Model model){
       pacientellamado.setPresente(true);
       suspenderconsulta = true;
       pacientes_actualizados.clear();
       citas_actualizadas.clear();
       try{ restTemplate.postForObject(GESTORCITASpacientes_STRING, pacientellamado, Paciente.class);
       } catch(Exception e) {}
    List<Cita> citas = null;
    try { citas = Arrays.asList(restTemplate.getForObject(GESTORCITAScitas_STRING+ "medico/" + medico.getDni(), Cita[].class));
    } catch (HttpClientErrorException.NotFound ex) {}
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
              pacientes_actualizados.add(paciente);
              citas_actualizadas.add(c);
              Comparator<Cita> citaComparator = Comparator.comparing(Cita::getHora);
              citas_actualizadas.sort(citaComparator);
            }
    }
    if (pacienteConsulta==true){
        if (pacientes_actualizados.isEmpty()) {
            pacientes_actualizados.add(pacientellamado);
            citas_actualizadas.add(citaPacienteLlamado);
        } else {
            pacientes_actualizados.add(1, pacientellamado);
            citas_actualizadas.add(1, citaPacienteLlamado);
        }
    }
       return "redirect:/iniciomedico";
    }

    @GetMapping("/finalizar_consulta")
    public String showsfinalizarConsulta(Model model,Authentication auth){
        pacientellamado.setPresente(false);
        pacientellamado.setLlamado(null);
        // Guardar paciente en API citas
        try{ restTemplate.postForObject(GESTORCITASpacientes_STRING, pacientellamado, Paciente.class);
        } catch(Exception e) {}
        tiempofinal=System.currentTimeMillis();
        long tiempoconsulta = tiempofinal - tiempoinicio;
        if (medico.getDni().equals("11111111A")){
            tiempos1.add(tiempoconsulta);
            long tiempototal_ms = 0;
            for (int i = 0; i < tiempos1.size(); i++) {
                tiempototal_ms += tiempos1.get(i);
            }
            double tiempomedio_ms = (double) tiempototal_ms / tiempos1.size();
            long horas_total = TimeUnit.MILLISECONDS.toHours(tiempototal_ms);
            long minutos_total = TimeUnit.MILLISECONDS.toMinutes(tiempototal_ms) - TimeUnit.HOURS.toMinutes(horas_total);
            long segundos_total = TimeUnit.MILLISECONDS.toSeconds(tiempototal_ms) - TimeUnit.HOURS.toSeconds(horas_total) - TimeUnit.MINUTES.toSeconds(minutos_total);
            long horas_medio = TimeUnit.MILLISECONDS.toHours((long) tiempomedio_ms);
            long minutos_medio = TimeUnit.MILLISECONDS.toMinutes((long) tiempomedio_ms) - TimeUnit.HOURS.toMinutes(horas_medio);
            long segundos_medio = TimeUnit.MILLISECONDS.toSeconds((long) tiempomedio_ms) - TimeUnit.HOURS.toSeconds(horas_medio) - TimeUnit.MINUTES.toSeconds(minutos_medio);
            tiempo_medio1 = String.format("%02d:%02d:%02d", horas_medio, minutos_medio, segundos_medio );
            tiempo_total1 = String.format("%02d:%02d:%02d", horas_total, minutos_total, segundos_total);
            }
        if (medico.getDni().equals("22222222B")){
            tiempos2.add(tiempoconsulta);
            long tiempototal_ms = 0;
            for (int i = 0; i < tiempos2.size(); i++) {
                tiempototal_ms += tiempos2.get(i);
            }
            double tiempomedio_ms = (double) tiempototal_ms / tiempos2.size();
            long horas_total = TimeUnit.MILLISECONDS.toHours(tiempototal_ms);
            long minutos_total = TimeUnit.MILLISECONDS.toMinutes(tiempototal_ms) - TimeUnit.HOURS.toMinutes(horas_total);
            long segundos_total = TimeUnit.MILLISECONDS.toSeconds(tiempototal_ms) - TimeUnit.HOURS.toSeconds(horas_total) - TimeUnit.MINUTES.toSeconds(minutos_total);
            long horas_medio = TimeUnit.MILLISECONDS.toHours((long) tiempomedio_ms);
            long minutos_medio = TimeUnit.MILLISECONDS.toMinutes((long) tiempomedio_ms) - TimeUnit.HOURS.toMinutes(horas_medio);
            long segundos_medio = TimeUnit.MILLISECONDS.toSeconds((long) tiempomedio_ms) - TimeUnit.HOURS.toSeconds(horas_medio) - TimeUnit.MINUTES.toSeconds(minutos_medio);
            tiempo_medio2 = String.format("%02d:%02d:%02d", horas_medio, minutos_medio, segundos_medio );
            tiempo_total2 = String.format("%02d:%02d:%02d", horas_total, minutos_total, segundos_total);
            }
        if (medico.getDni().equals("33333333C")){
            tiempos3.add(tiempoconsulta);
            long tiempototal_ms = 0;
            for (int i = 0; i < tiempos3.size(); i++) {
                tiempototal_ms += tiempos3.get(i);
            }
            double tiempomedio_ms = (double) tiempototal_ms / tiempos3.size();
            long horas_total = TimeUnit.MILLISECONDS.toHours(tiempototal_ms);
            long minutos_total = TimeUnit.MILLISECONDS.toMinutes(tiempototal_ms) - TimeUnit.HOURS.toMinutes(horas_total);
            long segundos_total = TimeUnit.MILLISECONDS.toSeconds(tiempototal_ms) - TimeUnit.HOURS.toSeconds(horas_total) - TimeUnit.MINUTES.toSeconds(minutos_total);
            long horas_medio = TimeUnit.MILLISECONDS.toHours((long) tiempomedio_ms);
            long minutos_medio = TimeUnit.MILLISECONDS.toMinutes((long) tiempomedio_ms) - TimeUnit.HOURS.toMinutes(horas_medio);
            long segundos_medio = TimeUnit.MILLISECONDS.toSeconds((long) tiempomedio_ms) - TimeUnit.HOURS.toSeconds(horas_medio) - TimeUnit.MINUTES.toSeconds(minutos_medio);
            tiempo_medio3 = String.format("%02d:%02d:%02d", horas_medio, minutos_medio, segundos_medio );
            tiempo_total3 = String.format("%02d:%02d:%02d", horas_total, minutos_total, segundos_total);
            }
        if (medico.getDni().equals("44444444D")){
            tiempos4.add(tiempoconsulta);
            long tiempototal_ms = 0;
            for (int i = 0; i < tiempos4.size(); i++) {
                tiempototal_ms += tiempos4.get(i);
            }
            double tiempomedio_ms = (double) tiempototal_ms / tiempos4.size();
            long horas_total = TimeUnit.MILLISECONDS.toHours(tiempototal_ms);
            long minutos_total = TimeUnit.MILLISECONDS.toMinutes(tiempototal_ms) - TimeUnit.HOURS.toMinutes(horas_total);
            long segundos_total = TimeUnit.MILLISECONDS.toSeconds(tiempototal_ms) - TimeUnit.HOURS.toSeconds(horas_total) - TimeUnit.MINUTES.toSeconds(minutos_total);
            long horas_medio = TimeUnit.MILLISECONDS.toHours((long) tiempomedio_ms);
            long minutos_medio = TimeUnit.MILLISECONDS.toMinutes((long) tiempomedio_ms) - TimeUnit.HOURS.toMinutes(horas_medio);
            long segundos_medio = TimeUnit.MILLISECONDS.toSeconds((long) tiempomedio_ms) - TimeUnit.HOURS.toSeconds(horas_medio) - TimeUnit.MINUTES.toSeconds(minutos_medio);
            tiempo_medio4 = String.format("%02d:%02d:%02d", horas_medio, minutos_medio, segundos_medio );
            tiempo_total4 = String.format("%02d:%02d:%02d", horas_total, minutos_total, segundos_total);
            }
        if (medico.getDni().equals("55555555E")){
            tiempos5.add(tiempoconsulta);
            long tiempototal_ms = 0;
            for (int i = 0; i < tiempos5.size(); i++) {
                tiempototal_ms += tiempos5.get(i);
            }
            double tiempomedio_ms = (double) tiempototal_ms / tiempos5.size();
            long horas_total = TimeUnit.MILLISECONDS.toHours(tiempototal_ms);
            long minutos_total = TimeUnit.MILLISECONDS.toMinutes(tiempototal_ms) - TimeUnit.HOURS.toMinutes(horas_total);
            long segundos_total = TimeUnit.MILLISECONDS.toSeconds(tiempototal_ms) - TimeUnit.HOURS.toSeconds(horas_total) - TimeUnit.MINUTES.toSeconds(minutos_total);
            long horas_medio = TimeUnit.MILLISECONDS.toHours((long) tiempomedio_ms);
            long minutos_medio = TimeUnit.MILLISECONDS.toMinutes((long) tiempomedio_ms) - TimeUnit.HOURS.toMinutes(horas_medio);
            long segundos_medio = TimeUnit.MILLISECONDS.toSeconds((long) tiempomedio_ms) - TimeUnit.HOURS.toSeconds(horas_medio) - TimeUnit.MINUTES.toSeconds(minutos_medio);
            tiempo_medio5 = String.format("%02d:%02d:%02d", horas_medio, minutos_medio, segundos_medio );
            tiempo_total5 = String.format("%02d:%02d:%02d", horas_total, minutos_total, segundos_total);
            }
        else{
            tiempototal_ms= 00;
            tiempomedio_ms= 00;
        }
        return "redirect:/iniciomedico";
    }

    
}
