package pe.edu.pucp.SIME.model.gestionDescuento;

public class TipoDeDescuento {
    private int idTipoDeDescuento;
    private String nombre;
    private String descripcion;
    private boolean activo;

    public int getIdTipoDeDescuento() {
        return idTipoDeDescuento;
    }

    public void setIdTipoDeDescuento(int idTipoDeDescuento) {
        this.idTipoDeDescuento = idTipoDeDescuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
