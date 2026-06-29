namespace SimeSoft.Models.Matriculas;

public class MatriculaAlumnoNuevoRequestDto
{
    public AlumnoNuevoRequestDto Alumno { get; set; } = new();

    public List<ApoderadoNuevoRequestDto> Apoderados { get; set; } = new();

    public int IdMatriculaCabecera { get; set; }

    public string TipoDescuento { get; set; } = "NINGUNO";
    public string MotivoDescuento { get; set; } = "";
    public double PorcentajeDescuento { get; set; }
}
