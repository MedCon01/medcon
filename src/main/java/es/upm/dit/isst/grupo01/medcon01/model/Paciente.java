package es.upm.dit.isst.grupo01.medcon01.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import java.util.function.Consumer;

@Entity
public class Paciente {
    
    @Id
    private String idpaciente;
    private String dni;
    private String nombre;
    private LocalDate fecha_nacimiento;
    private String ntarjeta;
    private Boolean presente;
    @Transient // poner siempre que se cree un atributo que no vaya a la BBDD
    private LocalTime hora_llegada;
    private LocalDateTime llamado;
    private int consultallamada;
    
    // Constructor sin argumentos
    public Paciente() {
    }
    
    // Constructor con argumentos
    public Paciente(String idpaciente, String dni, String nombre, LocalDate fecha_nacimiento, String ntarjeta, LocalTime hora_llegada,
                    Boolean presente, LocalDateTime llamado, int consultallamada) {
        this.idpaciente = idpaciente;
        this.dni = dni;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.ntarjeta = ntarjeta;
        this.hora_llegada = hora_llegada;
        this.presente = presente;
        this.llamado = llamado;
        this.consultallamada = consultallamada;

    }
    
    // Getter y setter idpaciente
    public String getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(String idpaciente) {
        this.idpaciente = idpaciente;
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
    // Getter y setter llamado
    public LocalDateTime getLlamado() {
        return this.llamado;
    }

    public void setLlamado(LocalDateTime llamado) {
        this.llamado = llamado;
    }

    // Getter y setter consulta
    public int getConsultallamada() {
        return this.consultallamada;
    }

    public void setConsultallamada(int consultallamada) {
        this.consultallamada = consultallamada;
    }

    // Método toString
    @Override
    public String toString() {
        return "Paciente{" +
                "idpaciente='" + idpaciente + '\'' +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", ntarjeta='" + ntarjeta + '\'' +
                ", hora_llegada=" + hora_llegada +
                ", presente=" + presente +
                ", llamado=" + llamado +
                ", consultaLlamada=" + consultallamada +
                '}';
    }
}