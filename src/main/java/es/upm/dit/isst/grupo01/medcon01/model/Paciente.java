package es.upm.dit.isst.grupo01.medcon01.model;

public class Paciente {
    
    private String DNI;
    private int n_tarjeta;
    private int cita; 
    
    public String getDNI() {
        return DNI;
    }

    public void set(String DNI) {
        this.DNI = DNI;
    }

    public int getn_tarjeta() {
        return n_tarjeta;
    }

    public void setn_tarjeta(int n_tarjeta) {
        this.n_tarjeta = n_tarjeta;
    }

    public int getcita() {
        return cita;
    }

    public void cita(int cita) {
        this.cita = cita;
    }






}
