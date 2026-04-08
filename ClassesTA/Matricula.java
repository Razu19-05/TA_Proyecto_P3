import java.util.Date;

class Matricula {
    private int idMatricula;
    private Date fecha;
    private String estado; // activo, retirado
    private double monto;
    
    private Alumno alumno; // FK
    private GradoSeccion gradoSeccion;
}
