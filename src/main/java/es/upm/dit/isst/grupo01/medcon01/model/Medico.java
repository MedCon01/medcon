package es.upm.dit.isst.grupo01.medcon01.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.GenericGenerator;
import es.upm.dit.isst.grupo01.medcon01.model.Paciente;

@Entity
@Table(name= "Medico")
public class Medico {
    @Id
    private String dni;
    private String nombre;
    private String n_colegiado;
    private int salaConsulta;
    private String especialidad;
    private Duration tiempoGlobal;
    private Duration tiempoConsulta_avg;
    private List<Paciente> cola;

    public Medico (String dni, String nombre, String n_colegiado, int salaConsulta, String especialidad, Duration tiempoGlobal, 
                   Duration tiempoConsulta_avg, List<Paciente> cola){
        this.dni = dni;
        this.nombre = nombre;
        this.n_colegiado = n_colegiado;
        this.salaConsulta = salaConsulta;
        
        this.especialidad = especialidad;
        this.tiempoGlobal = tiempoGlobal;
        this.tiempoConsulta_avg = tiempoConsulta_avg;
        this.cola = cola;
    }
    public Medico(){}

    // getter, setter DNI
    public String getDNI() {
        return this.dni;
    }
    public void setDNI(String dni){
        this.dni = dni;
    }
    // getter, setter nombre
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    // getter, setter n_colegiado
    public String getN_colegiado() {
        return this.n_colegiado;
    }
    public void setN_colegiado(String n_colegiado){
        this.n_colegiado = n_colegiado;
    }
    // getter, setter salaConsulta
    public int getSalaConsulta() {
        return this.salaConsulta;
    }
    public void setSalaConsulta(Integer salaConsulta){
        this.salaConsulta = salaConsulta;
    }
    // getter, setter especialidad
    public String getEspecialidad() {
        return this.especialidad;
    }
    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }
    // getter, setter tiempoGlobal
    public Duration getTiempoGlobal() {
        return this.tiempoGlobal;
    }
    public void setTiempoGlobal(Duration tiempoGlobal){
        this.tiempoGlobal = tiempoGlobal;
    }
    // getter, setter tiempoConsulta_avg
    public Duration getTiempoConsulta_avg() {
        return this.tiempoConsulta_avg;
    }
    public void setTiempoConsulta_Avg(Duration tiempoConsulta_avg){
        this.tiempoConsulta_avg = tiempoConsulta_avg;
    }
    // getter, setter cola
    public List<Paciente> getCola() {
        return this.cola;
    }
    public void setCola(List<Paciente> cola){
        this.cola = cola;
    }
    @Override
    public String toString() {
        return "Medico{" +
            "dni='" + dni + '\'' +
            ", nombre='" + nombre + '\'' +
            ", n_colegiado='" + n_colegiado + '\'' +
            ", salaConsulta=" + salaConsulta +
            ", especialidad='" + especialidad + '\'' +
            ", tiempoGlobal=" + tiempoGlobal +
            ", tiempoConsulta_avg=" + tiempoConsulta_avg +
            ", cola=" + cola +
            '}';
    }
}
