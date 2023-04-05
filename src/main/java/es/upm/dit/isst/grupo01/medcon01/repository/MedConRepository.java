package es.upm.dit.isst.grupo01.medcon01.repository;

import org.springframework.stereotype.Repository;

import es.upm.dit.isst.grupo01.medcon01.model.Paciente;

@Repository
public interface MedConRepository extends CrudRepository<Paciente, Long> {
    Paciente findByDni(String dni);

    static void save(Paciente paciente) {
    }
}
