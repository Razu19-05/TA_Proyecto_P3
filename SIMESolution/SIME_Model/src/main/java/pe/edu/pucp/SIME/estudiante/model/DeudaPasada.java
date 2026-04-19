package pe.edu.pucp.SIME.estudiante.model;

public class DeudaPasada {
    private int idDeudaPasada;
    private TipoDeuda tipoDeuda; // buzo, pension, libro, documento
    private double monto;
    private String estado; // pendiente, pagado

    public int getIdDeudaPasada() {
        return idDeudaPasada;
    }

    public void setIdDeudaPasada(int idDeudaPasada) {
        this.idDeudaPasada = idDeudaPasada;
    }

    public TipoDeuda getTipoDeuda() {
        return tipoDeuda;
    }

    public void setTipoDeuda(TipoDeuda tipoDeuda) {
        this.tipoDeuda = tipoDeuda;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
