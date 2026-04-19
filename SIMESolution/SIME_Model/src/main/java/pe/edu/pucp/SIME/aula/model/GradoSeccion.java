package pe.edu.pucp.SIME.aula.model;

import pe.edu.pucp.SIME.estudiante.model.Alumno;
import java.util.List;

public class GradoSeccion {
    private int idGrado;
    private String grado;   // 1ro primaria, etc.
    private String seccion; // A, B

    private Salon salonAsignado;
    private List<Alumno> alumnos;

    public int getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
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

    public Salon getSalonAsignado() {
        return salonAsignado;
    }

    public void setSalonAsignado(Salon salonAsignado) {
        this.salonAsignado = salonAsignado;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
