package pe.edu.pucp.SIME.model.gestionAlumnos;


import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

public class RelacionFamiliar {
    private int idRelacionFamiliar;
    private Alumno alumno;
    private Persona persona;
    private TipoRelacionFamiliar parentesco;
    private boolean contactoEmergencia;
    private String observaciones;
    private boolean activo;

    public int getIdRelacionFamiliar() {
        return idRelacionFamiliar;
    }

    public void setIdRelacionFamiliar(int idRelacionFamiliar) {
        this.idRelacionFamiliar = idRelacionFamiliar;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumnos(Alumno alumno) {
        this.alumno = alumno;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoRelacionFamiliar getParentesco() {
        return parentesco;
    }

    public void setParentesco(TipoRelacionFamiliar parentesco) {
        this.parentesco = parentesco;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public boolean isContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(boolean contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
