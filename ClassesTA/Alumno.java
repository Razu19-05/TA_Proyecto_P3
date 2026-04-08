import java.util.List;
class Alumno {
    protected int idAlumno;
    protected String codigo;
    protected String nombres;
    protected String apellidos;
    protected String dni;
    protected String direccion;
    protected String telefono;
    protected boolean estadoAlumno; // activo, retirado
    protected int edad;
    protected boolean discapacidad;
    protected String correo;
    
    protected Matricula matricula; // relación 1:1 (actual)
    protected Apoderado aporado;
    protected Descuento descuento;
    protected List<DeudaPasada> deuda;
    protected GradoSeccion grado;
}
