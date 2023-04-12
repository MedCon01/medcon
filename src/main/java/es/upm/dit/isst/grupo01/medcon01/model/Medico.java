package es.upm.dit.isst.grupo01.medcon01.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List; 

@Entity
public class Medico {
    @Id
    private String dni;
    private String nombre;
    private String n_colegiado;
    private int sala_consulta;
    private String especialidad;
    
    @Transient 
    // Cola de los identificadores de los pacientes en la lista 
    private List<String> cola = new ArrayList<String>() ;
    // Constructor vacío
    public Medico() {}
    
    public Medico(String dni, String nombre, String n_colegiado, int sala_consulta, String especialidad, List<String> cola) {
        this.dni = dni;
        this.nombre = nombre;
        this.n_colegiado = n_colegiado;
        this.sala_consulta = sala_consulta;
        this.especialidad = especialidad;
        this.cola = cola;
    }

    // Getter y setter de 'dni'
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    // Getter y setter de 'nombre'
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // Getter y setter de 'n_colegiado'
    public String getNColegiado() {
        return n_colegiado;
    }
    
    public void setNColegiado(String n_colegiado) {
        this.n_colegiado = n_colegiado;
    }
    
    // Getter y setter de 'sala_consulta'
    public int getSala_consulta() {
        return sala_consulta;
    }
    
    public void setSala_consulta(int sala_consulta) {
        this.sala_consulta = sala_consulta;
    }
    
    // Getter y setter de 'especialidad'
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Getter y setter de 'cola_pacientes'
    public List<String> getCola(){
        return this.cola;
    }
    public void setCola(List<String> cola){
        this.cola = cola;
    }
    @Override
    public String toString() {
        return "Medico{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", n_colegiado='" + n_colegiado + '\'' +
                ", sala_consulta=" + sala_consulta +
                ", especialidad='" + especialidad + '\'' +
                '}';
    }
}
