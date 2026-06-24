package pe.edu.pucp.SIME.model.DTO;

public class ProfesorDTO {
    private int idPersona;
    private int idAsignacionDocente;
    private String dni;
    private String nombreCompleto;
    private String especialidad;
    private boolean esTutor;

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdAsignacionDocente() {
        return idAsignacionDocente;
    }

    public void setIdAsignacionDocente(int idAsignacionDocente) {
        this.idAsignacionDocente = idAsignacionDocente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEsTutor() {
        return esTutor;
    }

    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }
}
