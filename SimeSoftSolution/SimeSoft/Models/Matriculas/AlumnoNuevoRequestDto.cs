namespace SimeSoft.Models.Matriculas;

public class AlumnoNuevoRequestDto
{
    public string Dni { get; set; } = "";
    public string Nombres { get; set; } = "";
    public string ApellidoPaterno { get; set; } = "";
    public string ApellidoMaterno { get; set; } = "";
    public string FechaNacimiento { get; set; } = "";
    public string Direccion { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";
}
