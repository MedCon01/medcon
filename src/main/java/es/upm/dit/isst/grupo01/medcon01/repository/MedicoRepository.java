
package es.upm.dit.isst.grupo01.medcon01.repository;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.grupo01.medcon01.model.Medico;

public interface MedicoRepository extends CrudRepository<Medico,String>{
   Medico findByDni(String dni);
   Medico findByNcolegiado(String ncolegiado);
}

