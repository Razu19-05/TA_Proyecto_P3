package pe.edu.pucp.SIME.model.DTO;

import java.util.Date;

public class VacanteMatriculaDTO {
    private int idMatriculaCabecera;
    private String periodo;
    private String nivel;
    private String grado;
    private String aula;
    private int totalVacantes;
    private int vacantesOcupadas;
    private int vacantesDisponibles;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean activo;

    public int getIdMatriculaCabecera() {
        return idMatriculaCabecera;
    }

    public void setIdMatriculaCabecera(int idMatriculaCabecera) {
        this.idMatriculaCabecera = idMatriculaCabecera;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
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

    public int getVacantesDisponibles() {
        return vacantesDisponibles;
    }

    public void setVacantesDisponibles(int vacantesDisponibles) {
        this.vacantesDisponibles = vacantesDisponibles;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}