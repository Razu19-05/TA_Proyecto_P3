package pe.edu.pucp.SIME.model.gestionAcademica;

import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;

public class AsignacionDocente {
    private int idAsignacionDocente;
    private Persona persona;
    private MatriculaCabecera matriculaCabecera;
    private boolean esTutor;
    private String observacion;
    private boolean activo;

    public int getIdAsignacionDocente() {
        return idAsignacionDocente;
    }

    public void setIdAsignacionDocente(int idAsignacionDocente) {
        this.idAsignacionDocente = idAsignacionDocente;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public MatriculaCabecera getMatriculaCabecera() {
        return matriculaCabecera;
    }

    public void setMatriculaCabecera(MatriculaCabecera matriculaCabecera) {
        this.matriculaCabecera = matriculaCabecera;
    }

    public boolean isEsTutor() {
        return esTutor;
    }

    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
