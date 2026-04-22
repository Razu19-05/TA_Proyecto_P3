package pe.edu.pucp.SIME.matricula.model;

import pe.edu.pucp.SIME.aula.model.GradoSeccion;
import pe.edu.pucp.SIME.estudiante.model.Alumno;

import java.util.Date;
import java.util.List;

public class Matricula {
    private int idMatricula;
    private Date fecha;
    private String estado; // activo, retirado
    private double monto;

    private Alumno alumno; // FK
    private Periodo periodo;
    private GradoSeccion grado;

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public GradoSeccion getGrado() {
        return grado;
    }

    public void setGrado(GradoSeccion grado) {
        this.grado = grado;
    }
}
