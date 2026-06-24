package pe.edu.pucp.SIME.model.gestionMatricula;

import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

import java.util.Date;

public class MatriculaDetalle {
    private int idMatriculaDetalle;
    private MatriculaCabecera matriculaCabecera;
    private Alumno alumno;
    private Date fechaMatricula;
    private TipoMatricula estado;
    private boolean activo;

    public int getIdMatriculaDetalle() {
        return idMatriculaDetalle;
    }

    public void setIdMatriculaDetalle(int idMatriculaDetalle) {
        this.idMatriculaDetalle = idMatriculaDetalle;
    }

    public MatriculaCabecera getMatriculaCabecera() {
        return matriculaCabecera;
    }

    public void setMatriculaCabecera(MatriculaCabecera matriculaCabecera) {
        this.matriculaCabecera = matriculaCabecera;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public TipoMatricula getEstado() {
        return estado;
    }

    public void setEstado(TipoMatricula estado) {
        this.estado = estado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
