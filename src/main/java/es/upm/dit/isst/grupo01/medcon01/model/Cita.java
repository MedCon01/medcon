package es.upm.dit.isst.grupo01.medcon01.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "Cita")
public class Cita {
    @Id
    private String id;

    @ManyToOne
    private Medico medico;

    @ManyToOne
    private Paciente paciente;

}



