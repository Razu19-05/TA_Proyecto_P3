package pe.edu.pucp.SIME.model.gestionAcademica;

public class GradoSeccion {
    private int idGradoSeccion;
    private TipoSeccion tipo;
    private String grado;
    private int vacantesMaximas;
    private boolean activo;

    public int getIdGradoSeccion() {
        return idGradoSeccion;
    }

    public void setIdGradoSeccion(int idGradoSeccion) {
        this.idGradoSeccion = idGradoSeccion;
    }

    public TipoSeccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoSeccion tipo) {
        this.tipo = tipo;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public int getVacantesMaximas() {
        return vacantesMaximas;
    }

    public void setVacantesMaximas(int vacantesMaximas) {
        this.vacantesMaximas = vacantesMaximas;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
