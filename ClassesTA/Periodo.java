import java.util.Date;
class Periodo {
    private int idPeriodo;
    private int anioEscolar;
    private Date fechaInicio;
    private Date fechaFin;

    //SETTERS Y GETTERS
    
    public int getIdPeriodo() {
        return this.idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getAnioEscolar() {
        return this.anioEscolar;
    }

    public void setAnioEscolar(int anioEscolar) {
        this.anioEscolar = anioEscolar;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}