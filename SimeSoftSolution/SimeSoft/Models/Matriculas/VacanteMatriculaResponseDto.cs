using System.Text.Json.Serialization;

namespace SimeSoft.Models.Matriculas;

public class VacanteMatriculaResponseDto
{
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

    [JsonPropertyName("totalVacantes")]
    public int TotalVacantes { get; set; }

    [JsonPropertyName("vacantesOcupadas")]
    public int VacantesOcupadas { get; set; }

    [JsonPropertyName("vacantesDisponibles")]
    public int VacantesDisponibles { get; set; }

    [JsonPropertyName("fechaInicio")]
    public string FechaInicio { get; set; } = "";

    [JsonPropertyName("fechaFin")]
    public string FechaFin { get; set; } = "";

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }
}