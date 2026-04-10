enum TipoDescuento{
    BECAS, FAMILIAR, OTRAS_RAZONES
}

class Descuento {
    private int idDescuento;
    private TipoDescuento tipoDescuento;
    private String descripcion;
    private double descuento;

    //SETTERS Y GETTERS

    public int getIdDescuento() {
        return this.idDescuento;
    }

    public void setIdDescuento(int idDescuento) {
        this.idDescuento = idDescuento;
    }

    public TipoDescuento getTipoDescuento() {
        return this.tipoDescuento;
    }

    public void setTipoDescuento(TipoDescuento tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDescuento() {
        return this.descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
