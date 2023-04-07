package es.upm.dit.isst.grupo01.medcon01.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Paciente {
    // para no generar manualmente un id para cada instancia. Si no creo un id para cada instancia el programa no va. 
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Id
    private String id;
    private String dni;
    private String numTarjeta;
    private int cita; 
    private boolean presente;
    private String nombre;
    
   
    private Date fechaCita;
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNTarjeta() {
        return numTarjeta;
    }

    public void setNTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public int getCita() {
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
   
}
