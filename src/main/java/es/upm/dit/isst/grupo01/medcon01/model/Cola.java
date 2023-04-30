package es.upm.dit.isst.grupo01.medcon01.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


public class Cola {
    private int salaespera;
    private List<Cita> pendientes = new ArrayList<Cita>();
    public Cola(){}
    public List<Paciente> llamados = new ArrayList<Paciente>();

    public Cola(int salaespera, List<Cita> pendientes){
        this.salaespera = salaespera;
        this.pendientes = pendientes;
    }

    // Getter y setter salaespera
    public int getSalaEspera() {
        return salaespera;
    }
    
    public void setSalaEspera(int salaespera) {
        this.salaespera = salaespera;
    }

    // Getter y setter pendientes

    public List<Cita> getPendientes() {
        return this.pendientes;
    }
    
    public void setPendientes(List<Cita> pendientes) {
        this.pendientes = pendientes; 
    }

}
