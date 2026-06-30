using System.Text.Json.Serialization;

namespace SimeSoft.Models.Matriculas;

public class HistorialMatriculaResponseDto
{
    [JsonPropertyName("idMatriculaDetalle")]
    public int IdMatriculaDetalle { get; set; }

    [JsonPropertyName("idAlumno")]
    public int IdAlumno { get; set; }

    [JsonPropertyName("dni")]
    public string Dni { get; set; } = "";

    [JsonPropertyName("nombresCompletos")]
    public string NombresCompletos { get; set; } = "";

    [JsonPropertyName("periodo")]
    public string Periodo { get; set; } = "";

    [JsonPropertyName("nivel")]
    public string Nivel { get; set; } = "";

    [JsonPropertyName("grado")]
    public string Grado { get; set; } = "";

    [JsonPropertyName("aula")]
    public string Aula { get; set; } = "";

    [JsonPropertyName("fechaMatricula")]
    public string FechaMatricula { get; set; } = "";

    [JsonPropertyName("estado")]
    public string Estado { get; set; } = "MATRICULADO";

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }
}