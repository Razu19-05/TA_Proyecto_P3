package pe.edu.pucp.SIME.model.gestionDescuento;

import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;

public class Descuento {
    private int idDescuento;
    private MatriculaDetalle matriculaDetalle;
    private TipoDeDescuento tipoDeDescuento;
    private double porcentaje;
    private String motivo;
    private boolean activo;

    public int getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(int idDescuento) {
        this.idDescuento = idDescuento;
    }

    public MatriculaDetalle getMatriculaDetalle() {
        return matriculaDetalle;
    }

    public void setMatriculaDetalle(MatriculaDetalle matriculaDetalle) {
        this.matriculaDetalle = matriculaDetalle;
    }

    public TipoDeDescuento getTipoDeDescuento() {
        return tipoDeDescuento;
    }

    public void setTipoDeDescuento(TipoDeDescuento tipoDeDescuento) {
        this.tipoDeDescuento = tipoDeDescuento;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
