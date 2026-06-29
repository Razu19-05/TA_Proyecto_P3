namespace SimeSoft.Models.Matriculas;

// Mapea a pe.edu.pucp.SIME.model.gestionAlumnos.Alumno (campo "estudiante" del SolicitudMatriculaDTO).
public class EstudianteRequestDto
{
    public int IdAlumno { get; set; }
    public string Dni { get; set; } = "";
    public string Nombres { get; set; } = "";
    public string ApellidoPaterno { get; set; } = "";
    public string ApellidoMaterno { get; set; } = "";
    public string Direccion { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";

    // El backend usa LocalDate con @JsonbDateFormat("yyyy-MM-dd'Z'"),
    // por eso se envia como string "2015-05-10Z" (con la Z literal al final).
    public string FechaNacimiento { get; set; } = "";

    public bool AlumnoNuevo { get; set; }
    public bool Activo { get; set; } = true;
}
