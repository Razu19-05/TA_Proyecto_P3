package pe.edu.pucp.SIME.model.DTO;

public class PagoMatriculaDTO {

    private String concepto;
    private double montoOriginal;
    private double montoDescuento;
    private double montoFinal;
    private String fechaVencimiento;
    private String estado;

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public double getMontoOriginal() { return montoOriginal; }
    public void setMontoOriginal(double montoOriginal) { this.montoOriginal = montoOriginal; }

    public double getMontoDescuento() { return montoDescuento; }
    public void setMontoDescuento(double montoDescuento) { this.montoDescuento = montoDescuento; }

    public double getMontoFinal() { return montoFinal; }
    public void setMontoFinal(double montoFinal) { this.montoFinal = montoFinal; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}