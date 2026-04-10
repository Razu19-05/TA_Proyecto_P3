import java.util.List;
import java.util.ArrayList;
class GradoSeccion {
    private int idGrado;
    private String grado;   // 1ro primaria, etc.
    private String seccion; // A, B
    
    
    private Salon salonAsignado;
    private List<Alumno> alumnos;

    //SETTERS Y GETTER

    public int getIdGrado() {
        return this.idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public String getGrado() {
        return this.grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return this.seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Salon getSalonAsignado() {
        return this.salonAsignado;
    }

    public void setSalonAsignado(Salon salonAsignado) {
        this.salonAsignado = salonAsignado;
    }

    public List<Alumno> getAlumnos() {
        return this.alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
