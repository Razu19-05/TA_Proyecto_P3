enum TipoDeuda{
    BUZO , PENSION ,LIBRO, DOCUMENTO
}

class DeudaPasada {
    private int idDeudaPasada;
    private TipoDeuda tipoDeuda; // buzo, pension, libro, documento
    private double monto;
    private String estado; // pendiente, pagado

    private Alumno alumno;
}
