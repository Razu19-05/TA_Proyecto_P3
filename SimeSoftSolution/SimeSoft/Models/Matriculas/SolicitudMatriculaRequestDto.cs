namespace SimeSoft.Models.Matriculas;

// Mapea a pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO.
// Es el cuerpo que consume el endpoint MatriculaRS/alumnoExistente.
public class SolicitudMatriculaRequestDto
{
    public EstudianteRequestDto Estudiante { get; set; } = new();

    public List<ApoderadoDetalleRequestDto> ListaApoderados { get; set; } = new();

    // ID real de SIME_MATRICULA_CABECERA (grado/seccion/anio).
    public int IdMatriculaCabecera { get; set; }

    // ID real de SIME_TIPO_DESCUENTO (0 si no aplica ninguno).
    public int IdTipoDescuento { get; set; }
    public double PorcentajeDescuentoAplicar { get; set; }
    public string MotivoDescuento { get; set; } = "";
}
