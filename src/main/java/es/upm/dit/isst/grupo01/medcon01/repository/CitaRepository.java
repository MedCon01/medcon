package es.upm.dit.isst.grupo01.medcon01.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.grupo01.medcon01.model.Cita;

public interface CitaRepository extends CrudRepository<Cita,Integer> {
    Cita findByPacienteId(String pacienteId);
    Cita findByMedicoDni(String medicoDni);
    List<Cita> findAllByMedicoDni(String medicoDni);
    Cita findByHora(int hora);

}
