package es.upm.dit.isst.grupo01.medcon01.controller;

import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.grupo01.medcon01.model.Cita;
import es.upm.dit.isst.grupo01.medcon01.model.Cola;
import es.upm.dit.isst.grupo01.medcon01.model.Medico;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.CitaRepository;
import es.upm.dit.isst.grupo01.medcon01.repository.MedicoRepository;
import es.upm.dit.isst.grupo01.medcon01.repository.PacienteRepository;

@Controller
public class ColaController {
    private Cola cola;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

//muestra la pantalla de la sala de espera
@PostMapping("/pantalla")
public String showPantallaEspera(Model model, @RequestParam("idpaciente") String idpaciente){
    Paciente paciente = pacienteRepository.findByIdpaciente(idpaciente);
    Cita cita_pendiente = citaRepository.findByPacienteId(paciente.getIdpaciente());
    if (paciente.getPresente().equals(true)){
        if (cita_pendiente.getHora().isBefore(paciente.getHora_llegada()) || cita_pendiente.getHora().plusMinutes(15).isBefore(paciente.getHora_llegada()) ){
            cola.pendientes.add(cita_pendiente);
        } else {
            int posicion = -1;
            for (int i = 0; i < cola.pendientes.size(); i++) {
                Cita c = cola.pendientes.get(i);
                int comp = c.getHora().compareTo(cita_pendiente.getHora());
                // Si la hora de la cita actual es mayor que la hora de cita_pendiente
                if (comp > 0) {
                    // Guardar la posición de la cita actual
                    posicion = i;
                    break;
                }
            }
                // Si no se encontró ninguna cita con hora mayor que la hora de cita_pendiente
                // se inserta cita_pendiente al final de la lista
            if (posicion == -1) {
                cola.pendientes.add(cita_pendiente);
            } else {
                // Insertar cita_pendiente en la posición correspondiente
                cola.pendientes.add(posicion, cita_pendiente);
            }  
        }
    }
    model.addAttribute("cita_pendiente", cita_pendiente); 
    model.addAttribute("paciente", paciente); 
    return"sala_espera/pantalla";
}

}
