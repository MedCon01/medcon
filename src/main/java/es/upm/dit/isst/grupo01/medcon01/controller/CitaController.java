package es.upm.dit.isst.grupo01.medcon01.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import es.upm.dit.isst.grupo01.medcon01.model.Cita;
import es.upm.dit.isst.grupo01.medcon01.repository.CitaRepository;

@RestController
public class CitaController {
    private final CitaRepository citaRepository;
    public CitaController(CitaRepository c){
        this.citaRepository = c;
    }
   
@GetMapping("/citas") // lee todas las citas 
List<Cita> readAll(){
    return(List<Cita>) citaRepository.findAll();
}
@PostMapping("/citas") // crea cita -en cuerpo
ResponseEntity<Cita> create(@RequestBody Cita newCita) throws URISyntaxException{
    Cita result = citaRepository.save(newCita);
    return ResponseEntity.created(new URI("/citas/" + result.getId())).body(result);
}
@GetMapping("/citas/{id}") // lee la cita con ese id
ResponseEntity<Cita> read(@PathVariable int id){
    return citaRepository.findById(id).map(cita -> 
    ResponseEntity.ok().body(cita)).orElse(new ResponseEntity<Cita>(HttpStatus.NOT_FOUND));
}

@PutMapping("/citas/{id}") // actualiza cita con ese id -en cuerpo
ResponseEntity<Cita> update(@RequestBody Cita newCita, @PathVariable int id){
    return citaRepository.findById(id).map(cita -> { 
        cita.setFecha(newCita.getFecha());
        cita.setHora(newCita.getHora());
        cita.setMedicoDni(newCita.getMedicoDni());
        cita.setPacienteId(newCita.getPacienteId());
        citaRepository.save(cita);
        return ResponseEntity.ok().body(cita);
        }).orElse(new ResponseEntity<Cita>(HttpStatus.NOT_FOUND));
}
@DeleteMapping("/citas/{id}") // borra la cita con ese id
ResponseEntity<Cita> delete(@PathVariable int id){
    citaRepository.deleteById(id);
    return ResponseEntity.ok().body(null);
}
@GetMapping("/citas/paciente/{pacienteId}") // lee las citas de ese paciente  
List<Cita> readPaciente(@PathVariable String pacienteId){
    return(List<Cita>) citaRepository.findByPacienteId(pacienteId);
}
@GetMapping("/citas/medico/{medicoDni}") // lee las citas con ese medico 
List<Cita> readMedico(@PathVariable String medicoDni){
    return(List<Cita>) citaRepository.findByMedicoDni(medicoDni);
} 
}
