package es.upm.dit.isst.grupo01.medcon01.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.grupo01.medcon01.model.Cita;

public interface CitaRepository extends CrudRepository<Cita,Integer> {
    Cita findByPaciente (String paciente);
    Cita findByMedico (String medico);


}
