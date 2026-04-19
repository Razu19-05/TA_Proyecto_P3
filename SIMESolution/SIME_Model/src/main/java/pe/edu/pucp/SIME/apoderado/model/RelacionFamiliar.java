package pe.edu.pucp.SIME.apoderado.model;

import pe.edu.pucp.SIME.estudiante.model.Alumno;

public class RelacionFamiliar {
    private int idApoderadoAlumno;
    private String tipoRelacion; // hermano,primo
    private String contactoEmergencia;
    private String observaciones;
    //relaciones
    private Apoderado apoderado;
    private Alumno alumno;

    public int getIdApoderadoAlumno() {
        return idApoderadoAlumno;
    }

    public void setIdApoderadoAlumno(int idApoderadoAlumno) {
        this.idApoderadoAlumno = idApoderadoAlumno;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Apoderado getApoderado() {
        return apoderado;
    }

    public void setApoderado(Apoderado apoderado) {
        this.apoderado = apoderado;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
