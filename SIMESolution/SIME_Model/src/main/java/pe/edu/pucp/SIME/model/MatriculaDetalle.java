package pe.edu.pucp.SIME.model;

import java.util.Date;

public class MatriculaDetalle {
    private int idMatriculaDetalle;
    private Date fechaMatricula;
    private TipoEstado tipoEstado;
    private int activo;

//    private Alumno alumno;
    private MatriculaCabecera cabecera;

    public int getIdMatriculaDetalle() {
        return idMatriculaDetalle;
    }

    public void setIdMatriculaDetalle(int idMatriculaDetalle) {
        this.idMatriculaDetalle = idMatriculaDetalle;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public TipoEstado getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(TipoEstado tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public MatriculaCabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(MatriculaCabecera cabecera) {
        this.cabecera = cabecera;
    }
}
