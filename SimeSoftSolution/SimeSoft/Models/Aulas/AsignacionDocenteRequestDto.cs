namespace SimeSoft.Models.Aulas;

public class AsignacionDocenteRequestDto
{
    public int IdPersona { get; set; }
    public int? IdAula { get; set; }
    public int? IdMatriculaCabecera { get; set; }
    public bool EsTutor { get; set; }
    public string Observacion { get; set; } = "";
}
