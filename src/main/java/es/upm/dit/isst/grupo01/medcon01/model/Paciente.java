package es.upm.dit.isst.grupo01.medcon01.model;

public class Paciente {
    
    private String dni;
    private int n_tarjeta;
    private int cita; 
    private boolean presente;
    private String nombre;
    
    public String getDNI() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getn_tarjeta() {
        return n_tarjeta;
    }

    public void setN_tarjeta(int n_tarjeta) {
        this.n_tarjeta = n_tarjeta;
    }

    public int getcita() {
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

}
