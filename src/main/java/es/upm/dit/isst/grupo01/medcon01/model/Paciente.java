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

@Entity
public class Paciente {
    
    @Id
    private String id;
    private String dni;
    private String nombre;
    private LocalDate fecha_nacimiento;
    private String ntarjeta;
    
    @Transient // poner siempre que se cree un atributo que no vaya a la BBDD
    private LocalTime hora_llegada;
    @Transient // poner siempre que se cree un atributo que no vaya a la BBDD
    private Boolean presente;
    
    // Constructor sin argumentos
    public Paciente() {
    }
    
    // Constructor con argumentos
    public Paciente(String id, String dni, String nombre, LocalDate fecha_nacimiento, String ntarjeta, LocalTime hora_llegada,
                    Boolean presente) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.ntarjeta = ntarjeta;
        this.hora_llegada = hora_llegada;
        this.presente = presente;
    }
    
    // Getter y setter Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    // Getter y setter dni
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    // Getter y setter nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // Getter y setter fecha_nacimiento
    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }
    
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    // Getter y setter ntarjeta
    public String getNtarjeta() {
        return ntarjeta;
    }

    public void setNtarjeta(String ntarjeta) {
        this.ntarjeta = ntarjeta;
    }
    // Getter y setter hora_llegada
    public LocalTime getHora_llegada() {
        return this.hora_llegada;
    }

    public void setHora_llegada(LocalTime hora_llegada) {
        this.hora_llegada = hora_llegada;
    }
    // Getter y setter presente
    public Boolean getPresente(){
        return this.presente;
    }
    public void setPresente(boolean presente){
        this.presente = presente;
    }
    // Método toString
    @Override
    public String toString() {
        return "Paciente{" +
                "id='" + id + '\'' +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", ntarjeta='" + ntarjeta + '\'' +
                ", hora_llegada=" + hora_llegada +
                ", presente=" + presente +
                '}';
    }
}