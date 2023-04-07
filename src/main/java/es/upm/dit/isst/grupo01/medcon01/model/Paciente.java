package es.upm.dit.isst.grupo01.medcon01.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Date;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

@Entity
// @Table(name = "Paciente")
public class Paciente {
    
   // @GeneratedValue(generator = "uuid")
   // @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Id
    private String id;
    private String dni;
    private String nombre;
    private Date fecha_nacimiento;
    private Long nTarjeta;
    private int cita; 
    private boolean presente;
    
    public Paciente(String id, String dni, String nombre, Date fecha_nacimiento, Long nTarjeta, int cita, Boolean presente){
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nTarjeta = nTarjeta;
        this.cita = cita;
        this.presente = presente;
    }
    public Paciente(){}

    public String getDNI() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Long getnTarjeta() {
        return nTarjeta;
    }

    public void setnTarjeta(Long nTarjeta) {
        this.nTarjeta = nTarjeta;
    }
    @OneToOne
    public int getcita() {
        return cita;
    }

    public void setCita(int cita) {
        this.cita = cita;
    }
     
    public boolean getPresente(){
        return presente;
    }

    public void setPresente(boolean presente){
        this.presente = presente;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setId(String id){
        this.id = id;

    }

    public String getId(){
        return id;
    }

}
