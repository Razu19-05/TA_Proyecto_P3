package pe.edu.pucp.SIME.model;

import java.util.Date;

public class MatriculaCabecera {
    private int idMatriculaCabecera;
    private Date fechaInicioMatricula;
    private Date fechaFinMatricula;
    private int totalVacantes;
    private int vacantesOcupadas;
    private int vacantesLibres;
    private int activo;

    private PeriodoAcademico periodo;
    private GradoSeccion gradoSeccion;
    private Aula aula;
    private Persona trabajador;

    public int getIdMatriculaCabecera() {
        return idMatriculaCabecera;
    }

    public void setIdMatriculaCabecera(int idMatriculaCabecera) {
        this.idMatriculaCabecera = idMatriculaCabecera;
    }

    public Date getFechaInicioMatricula() {
        return fechaInicioMatricula;
    }

    public void setFechaInicioMatricula(Date fechaInicioMatricula) {
        this.fechaInicioMatricula = fechaInicioMatricula;
    }

    public Date getFechaFinMatricula() {
        return fechaFinMatricula;
    }

    public void setFechaFinMatricula(Date fechaFinMatricula) {
        this.fechaFinMatricula = fechaFinMatricula;
    }

    public int getTotalVacantes() {
        return totalVacantes;
    }

    public void setTotalVacantes(int totalVacantes) {
        this.totalVacantes = totalVacantes;
    }

    public int getVacantesOcupadas() {
        return vacantesOcupadas;
    }

    public void setVacantesOcupadas(int vacantesOcupadas) {
        this.vacantesOcupadas = vacantesOcupadas;
    }

    public int getVacantesLibres() {
        return vacantesLibres;
    }

    public void setVacantesLibres(int vacantesLibres) {
        this.vacantesLibres = vacantesLibres;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public PeriodoAcademico getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoAcademico periodo) {
        this.periodo = periodo;
    }

    public GradoSeccion getGradoSeccion() {
        return gradoSeccion;
    }

    public void setGradoSeccion(GradoSeccion gradoSeccion) {
        this.gradoSeccion = gradoSeccion;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Persona getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Persona trabajador) {
        this.trabajador = trabajador;
    }
}
