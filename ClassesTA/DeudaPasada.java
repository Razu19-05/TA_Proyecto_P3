enum TipoDeuda{
    BUZO , PENSION ,LIBRO, DOCUMENTO
}

class DeudaPasada {
    private int idDeudaPasada;
    private TipoDeuda tipoDeuda; // buzo, pension, libro, documento
    private double monto;
    private String estado; // pendiente, pagado

    private Alumno alumno;

    //SETTERS Y GETTERS

    public int getIdDeudaPasada() {
        return this.idDeudaPasada;
    }

    public void setIdDeudaPasada(int idDeudaPasada) {
        this.idDeudaPasada = idDeudaPasada;
    }

    public TipoDeuda getTipoDeuda() {
        return this.tipoDeuda;
    }

    public void setTipoDeuda(TipoDeuda tipoDeuda) {
        this.tipoDeuda = tipoDeuda;
    }

    public double getMonto() {
        return this.monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Alumno getAlumno() {
        return this.alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}