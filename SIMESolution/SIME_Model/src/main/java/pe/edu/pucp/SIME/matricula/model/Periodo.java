package pe.edu.pucp.SIME.matricula.model;

import java.util.Date;
import java.util.List;

public class Periodo {
    private int idPeriodo;
    private int anioEscolar;
    private Date fechaInicio;
    private Date fechaFin;

    private List<Matricula> matriculas;

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
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
}
