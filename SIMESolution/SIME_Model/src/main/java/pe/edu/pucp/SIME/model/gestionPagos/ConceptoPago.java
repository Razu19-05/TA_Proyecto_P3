package pe.edu.pucp.SIME.model.gestionPagos;

public class ConceptoPago {
    private int idConceptoPago;
    private String nombre;
    private double monto;
    private boolean activo;

    public int getIdConceptoPago() {
        return idConceptoPago;
    }

    public void setIdConceptoPago(int idConceptoPago) {
        this.idConceptoPago = idConceptoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
