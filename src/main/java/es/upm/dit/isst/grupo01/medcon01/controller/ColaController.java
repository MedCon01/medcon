package es.upm.dit.isst.grupo01.medcon01.controller;

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
@GetMapping("/pantalla")
public String showPantallaEspera(){
    // sin terminar 
    cola.pendientes.add(null);
    return"sala_espera/pantalla";
}

}
