package es.upm.dit.isst.grupo01.medcon01.controller;

import java.time.*;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.PacienteRepository;



@Controller
public class PacienteController {

    @Autowired
    private PacienteRepository PacienteRepository;
    public PacienteController(PacienteRepository pacienteRepository){
        this.PacienteRepository = pacienteRepository;
    }

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

    /*sin consulta a la base de datos */
    @PostMapping("/login_DNI")
    public String registrarPacienteDNI(@RequestParam("dni") String dni, Model model) {
     if ( dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
        Paciente paciente = new Paciente();
        // marcamos el paciente como presente
           paciente.setPresente(true);
           paciente = PacienteRepository.save(paciente);
           // Agregamos al paciente a la lista del médico
           medicoController.addPaciente(paciente);
           // Agregamos los datos del paciente a la vista
         model.addAttribute("dni", dni);
         model.addAttribute("nombre", paciente.getNombre());
         model.addAttribute("cita", paciente.getCita());
         model.addAttribute("paciente", paciente);
        
          return "redirect:/informacion_cita";     
     } else {  
        return "redirect:/error_cita";
         
     } 
}
/* sin consulta a la base de datos  */
@PostMapping("/login_tarjeta")
public String registratPacienteTarjeta(@RequestParam("numTarjeta") String numTarjeta, Model model ) {
    // Aquí va el código para validar el numero
    if (numTarjeta.length() == 10 && numTarjeta.matches("[0-9]+")) {
        Paciente paciente = new Paciente();
        // Marcamos al paciente como presente
    paciente.setPresente(true);
    paciente = PacienteRepository.save(paciente);
    // Agregamos al paciente a la lista del médico
    medicoController.addPaciente(paciente);
    model.addAttribute("paciente", paciente);
        return "redirect:/informacion_cita";
    } else {
        return "redirect:/error_cita";
    }
}
   

    /* buscando en la base de datos 
    @PostMapping("/login_DNI")
   public String registrarPacienteDNI(@RequestParam("dni") String dni, Model model) {
    // Buscamos el paciente en la base de datos
    Paciente paciente = PacienteRepository.findByDni(dni);
    
    if (paciente == null) {
        model.addAttribute("error", "El paciente no está registrado.");
        return "redirect:/error_cita";
    } else if (!dni.matches("\\d{8}[A-HJ-NP-TV-Z]") || !paciente.getDni().equals(dni)) {
        return "redirect:/error_cita";
    } else {
        // Marcamos al paciente como presente
        paciente.setPresente(true);
        paciente = PacienteRepository.save(paciente);
        // Agregamos al paciente a la lista del médico
        medicoController.addPaciente(paciente);
         
         // Agregamos los datos del paciente a la vista
         model.addAttribute("dni", dni);
         model.addAttribute("nombre", paciente.getNombre());
         model.addAttribute("cita", paciente.getCita());
         model.addAttribute("paciente", paciente);
        
       return "redirect:/informacion_cita";
       // return "redirect:/medico/welcome";
        
    } 
}
    */

/* consultando a la base de datos  
    @PostMapping("/login_tarjeta")
    public String registrarPacienteTarjeta(@RequestParam("nTarjeta") Long numTarjeta, Model model ) {
        Paciente paciente = PacienteRepository.findByNTarjeta(numTarjeta);
        if (paciente == null) {
            model.addAttribute("error", "El paciente ya está registrado.");
            return "redirect:/error_registro";
        }
        // Aquí va el código para validar el numero
        if (nTarjeta.toString().length() == 12 && nTarjeta.toString().matches("[0-9]+")) {
           // Marcamos al paciente como presente
        paciente.setPresente(true);
        paciente = PacienteRepository.save(paciente);
        // Agregamos al paciente a la lista del médico
        medicoController.addPaciente(paciente);
        model.addAttribute("paciente", paciente);
            return "redirect:/informacion_cita";
        } else {
            return "redirect:/error_cita";
        }
    }
    */



    @PostMapping("/comparar_hora_llegada")
    public String compararHoraLlegada(@RequestParam("id") String id, @RequestParam("horaLlegada") String horaLlegada, Model model) {
        // Obtener el paciente de la base de datos por su ID
        Paciente paciente = PacienteRepository.findById(id).orElse(null);
    
        if (paciente != null) {
            // Obtener la hora de la cita del paciente
            LocalDate horaCita = paciente.getHoraCita();
    
            // Obtener la hora de llegada del paciente
            LocalDateTime horaLlegadaParsed = LocalDateTime.parse(horaLlegada, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    
            // Calcular la diferencia de tiempo entre la hora de llegada y la hora de la cita
            Duration diferenciaTiempo = Duration.between((Temporal) horaCita, horaLlegadaParsed);
            long minutosDiferencia = diferenciaTiempo.toMinutes();
    
            if (minutosDiferencia <= 0) {
                // Si la hora de llegada es anterior o igual a la hora de la cita, se coloca en la cola como el paciente inmediatamente siguiente al último que esté en la cola
                Paciente ultimoPaciente = PacienteRepository.findTopByOrderByOrdenDesc();
                int orden = ultimoPaciente != null ? ultimoPaciente.getOrden() + 1 : 1;
                paciente.setOrden(orden);

            } else if (minutosDiferencia <= 14) {
                // Si la hora de llegada del paciente es entre 1 y 14 minutos (inclusive) después de la hora de la cita,
                // el paciente se coloca inmediatamente después del paciente que se encuentre en la cola con hora de cita más próxima a la del paciente que acaba de llegar.
                Paciente pacienteMasProximo = PacienteRepository.findTop1ByCita_HoraAfterOrderByCita_HoraAsc(horaLlegadaParsed);
                int orden = pacienteMasProximo != null ? pacienteMasProximo.getOrden() + 1 : 1;
                paciente.setOrden(orden);

            } else {
                // Si la hora de llegada es 15 minutos posterior a la hora de la cita, se coloca al paciente en el último lugar de la lista aunque su hora de cita sea anterior.
                Paciente ultimoPaciente = PacienteRepository.findTopByOrderByOrdenDesc();
                int orden = ultimoPaciente != null ? ultimoPaciente.getOrden() + 1 : 1;
                paciente.setOrden(orden);
            }
    
            // Guardar los cambios en la base de datos
            PacienteRepository.save(paciente);
    
            // Redirigir a la página de información de la cita
            model.addAttribute("paciente", paciente);
            return "redirect:/informacion_cita";
            
        } else {
            // Si no se encuentra el paciente, redirigir a la página de error de cita
            return "redirect:/error_cita";
        }
    }
}

  




