package pe.edu.pucp.SIME.model.gestionAcademica;

import java.util.Date;

public class PeriodoAcademico {
    private int idPeriodoAcademico;
    private int anioEscolar;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean activo;

    public int getIdPeriodoAcademico() {
        return idPeriodoAcademico;
    }

    public void setIdPeriodoAcademico(int idPeriodoAcademico) {
        this.idPeriodoAcademico = idPeriodoAcademico;
    }

    public int getAnioEscolar() {
        return anioEscolar;
    }

    public void setAnioEscolar(int anioEscolar) {
        this.anioEscolar = anioEscolar;
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
