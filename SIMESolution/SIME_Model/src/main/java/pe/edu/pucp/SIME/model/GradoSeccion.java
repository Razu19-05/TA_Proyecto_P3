package pe.edu.pucp.SIME.model;

public class GradoSeccion {
    private int idGradoSeccion;
    private String grado;
    private String seccion;
    private int vacantesMaximas;
    private int activo;

    public int getIdGradoSeccion() {
        return idGradoSeccion;
    }

    public void setIdGradoSeccion(int idGradoSeccion) {
        this.idGradoSeccion = idGradoSeccion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getVacantesMaximas() {
        return vacantesMaximas;
    }

    public void setVacantesMaximas(int vacantesMaximas) {
        this.vacantesMaximas = vacantesMaximas;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
