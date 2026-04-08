import java.util.List;
import java.util.ArrayList;
import java.util.Date;
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
    protected Date fechaNacimiento;
    
    protected List<Matricula> matriculas; // relación 1:1 (actual)
    protected List<Apoderado> apoderados;
    protected List<TrabajadorAlumno> trabajadores;
    protected Descuento descuento;
    protected List<DeudaPasada> deudasPasadas;
    protected GradoSeccion gradoSeccion;
}
