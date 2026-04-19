import java.util.List;
import java.util.ArrayList;

class RelacionFamiliar {
    private int idApoderadoAlumno;
    private String tipoRelacion; // hermano,primo
    private String contactoEmergencia;
    private String observaciones;
    //relaciones
    private Apoderado apoderado;
    private Alumno alumno;

    //SETTERS Y GETTERS

    public int getIdApoderadoAlumno() {
        return this.idApoderadoAlumno;
    }

    public void setIdApoderadoAlumno(int idApoderadoAlumno) {
        this.idApoderadoAlumno = idApoderadoAlumno;
    }

    public String getTipoRelacion() {
        return this.tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public String getContactoEmergencia() {
        return this.contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Apoderado getApoderado() {
        return this.apoderado;
    }

    public void setApoderado(Apoderado apoderado) {
        this.apoderado = apoderado;
    }

    public Alumno getAlumno() {
        return this.alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
