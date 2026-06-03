package pe.edu.pucp.SIME.model.gestionPagos;

import pe.edu.pucp.SIME.model.gestionDescuento.Descuento;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;

import java.util.Date;

public class Pago {
    private int idPago;
    private MatriculaDetalle matriculaDetalle;
    private ConceptoPago conceptoPago;
    private double montoDescuento;
    private double montoFinal;
    private Date fechaEmision;
    private Date fechaVencimiento;
    private Date fechaPago;
    private TipoEstado estado;
    private String observacion;
    private boolean activo;

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public MatriculaDetalle getMatriculaDetalle() {
        return matriculaDetalle;
    }

    public void setMatriculaDetalle(MatriculaDetalle matriculaDetalle) {
        this.matriculaDetalle = matriculaDetalle;
    }

    public ConceptoPago getConceptoPago() {
        return conceptoPago;
    }

    public void setConceptoPago(ConceptoPago conceptoPago) {
        this.conceptoPago = conceptoPago;
    }

    public double getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(double montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public double getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(double montoFinal) {
        this.montoFinal = montoFinal;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    public void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
