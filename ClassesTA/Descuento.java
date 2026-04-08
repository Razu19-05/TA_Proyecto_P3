enum TipoDescuento{
    BECAS, FAMILIAR, OTRAS_RAZONES
}

class Descuento {
    private int idDescuento;
    private TipoDescuento tipoDescuento;
    private String descripcion;
    private double descuento;
}
