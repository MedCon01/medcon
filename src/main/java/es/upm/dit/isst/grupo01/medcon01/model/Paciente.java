package es.upm.dit.isst.grupo01.medcon01.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Paciente")
public class Paciente {
    
    @Id
    // Creo que esto en teoría no es necesario, ya que nosotros introducimos a mano el identificador.
    // @GeneratedValue(generator = "uuid")
    // @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String dni;
    private String nombre;
    private LocalDate fecha_nacimiento;
    private String nTarjeta;
    private int cita; 
    private boolean presente;
    private LocalDate fechaCita;
    private LocalTime horaLlegada;
    
    public Paciente(String id, String dni, String nombre, LocalDate fecha_nacimiento, String nTarjeta, int cita, Boolean presente){
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nTarjeta = nTarjeta;
        this.cita = cita;
        this.presente = presente;
    }
    public Paciente(){}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getnTarjeta() {
        return nTarjeta;
    }

    public void setnTarjeta(String nTarjeta) {
        this.nTarjeta = nTarjeta;
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
    public LocalDate getFechaCita() {
        return fechaCita;
    }
    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }
    public LocalDate getHoraCita() {
        return getHoraCita();
    }
    public void setHoraCita(LocalTime horaCita) {
    }
    public Date getHoraLlegada() {
        return getHoraLlegada();
    }
    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }
    public int getOrden() {
        return 0;
    }
    public void setOrden(int orden) {
    }
    @Override
    public String toString() {
        return "Paciente{" +
            "id='" + id + '\'' +
            ", dni='" + dni + '\'' +
            ", nombre='" + nombre + '\'' +
            ", fecha_nacimiento=" + fecha_nacimiento +
            ", nTarjeta='" + nTarjeta + '\'' +
            ", cita=" + cita +
            ", presente=" + presente +
            ", fechaCita=" + fechaCita +
            ", horaLlegada=" + horaLlegada +
            '}';
    }
}
