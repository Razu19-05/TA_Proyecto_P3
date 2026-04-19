package pe.edu.pucp.SIME.personal.model;

import pe.edu.pucp.SIME.estudiante.model.Alumno;

public class TrabajorAlumno {
    private int idTrabajadorAlumno;
    private String tipoRelacion; // hermano,primo

    private Trabajador trabajador;
    private Alumno alumno;

    public int getIdTrabajadorAlumno() {
        return idTrabajadorAlumno;
    }

    public void setIdTrabajadorAlumno(int idTrabajadorAlumno) {
        this.idTrabajadorAlumno = idTrabajadorAlumno;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
