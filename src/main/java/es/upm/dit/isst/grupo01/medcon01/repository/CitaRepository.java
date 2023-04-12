package es.upm.dit.isst.grupo01.medcon01.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.grupo01.medcon01.model.Cita;
import es.upm.dit.isst.grupo01.medcon01.model.Medico;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;

public interface CitaRepository extends CrudRepository<Cita,Integer> {
    Cita findByPacienteId(String pacienteId);
    Cita findByMedicoDni(String medicoDni);

}
