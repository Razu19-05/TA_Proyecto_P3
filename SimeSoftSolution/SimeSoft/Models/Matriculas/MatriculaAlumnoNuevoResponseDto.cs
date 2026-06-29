namespace SimeSoft.Models.Matriculas;

public class MatriculaAlumnoNuevoResponseDto
{
    public int IdAlumno { get; set; }
    public int IdMatriculaDetalle { get; set; }
    public string Mensaje { get; set; } = "";
    public List<PagoMatriculaResponseDto> Pagos { get; set; } = new();
}
