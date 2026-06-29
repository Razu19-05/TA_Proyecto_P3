using System.Text.Json.Serialization;

namespace SimeSoft.Models.Matriculas;

public class MatriculaAlumnoResponseDto
{
    [JsonPropertyName("idMatriculaDetalle")]
    public int IdMatriculaDetalle { get; set; }

    [JsonPropertyName("idMatriculaCabecera")]
    public int IdMatriculaCabecera { get; set; }

    [JsonPropertyName("periodo")]
    public string Periodo { get; set; } = "";

    [JsonPropertyName("nivel")]
    public string Nivel { get; set; } = "";

    [JsonPropertyName("grado")]
    public string Grado { get; set; } = "";

    [JsonPropertyName("aula")]
    public string Aula { get; set; } = "";

    [JsonPropertyName("fechaInicio")]
    public string FechaInicio { get; set; } = "";

    [JsonPropertyName("fechaFin")]
    public string FechaFin { get; set; } = "";

    [JsonPropertyName("fechaMatricula")]
    public string FechaMatricula { get; set; } = "";

    [JsonPropertyName("estado")]
    public string Estado { get; set; } = "MATRICULADO";

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }
}
