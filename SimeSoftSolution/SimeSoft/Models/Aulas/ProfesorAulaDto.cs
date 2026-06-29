namespace SimeSoft.Models.Aulas;

public class ProfesorAulaDto
{
    public int IdAsignacionDocente { get; set; }
    public int IdPersona { get; set; }
    public int? IdMatriculaCabecera { get; set; }

    public string Dni { get; set; } = "";
    public string NombresCompletos { get; set; } = "";
    public string Tipo { get; set; } = "";
    public string Especialidad { get; set; } = "";
    public string Cargo { get; set; } = "";
    public string Area { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";

    public bool EsTutor { get; set; }
    public string Observacion { get; set; } = "";
    public string Estado { get; set; } = "";
}