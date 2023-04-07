package es.upm.dit.isst.grupo01.medcon01.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
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
    
<<<<<<< HEAD
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
=======
   
    private Date fechaCita;
    public String getDni() {
>>>>>>> c42f1bf6227b40e1856612068f8b0d0986560f78
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
<<<<<<< HEAD
    @OneToOne
    public int getcita() {
=======

    public int getCita() {
>>>>>>> c42f1bf6227b40e1856612068f8b0d0986560f78
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

<<<<<<< HEAD
=======
    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Date getHoraCita() {
        return getHoraCita();
    }

    public void setHoraCita(Date horaCita) {
    }

    public Date getHoraLlegada() {
        return getHoraLlegada();
    }

    public void setHoraLlegada(Date horaLlegada) {
    }

    public int getOrden() {
        return 0;
    }

    public void setOrden(int orden) {
    }
   
>>>>>>> c42f1bf6227b40e1856612068f8b0d0986560f78
}
