package es.upm.dit.isst.grupo01.medcon01.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class Cita {
    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;
    private String pacienteId;
    private String medicoDni;

    // Constructor
    public Cita(int id, LocalDate fecha, LocalTime hora, String pacienteId, String medicoDni) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.pacienteId = pacienteId;
        this.medicoDni = medicoDni;
    }
    public Cita(){}

    // Getter y Setter id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Getter y Setter fecha
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    // Getter y Setter hora
    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    // Getter y Setter pacienteId
    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }
    // Getter y Setter medicoDni
    public String getMedicoDni() {
        return medicoDni;
    }

    public void setMedicoDni(String medicoDni) {
        this.medicoDni = medicoDni;
    }
    
    // Método toString
    @Override
    public String toString() {
        return "Cita [id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", pacienteId=" + pacienteId + ", medicoDni=" + medicoDni
                + "]";
    }
}

