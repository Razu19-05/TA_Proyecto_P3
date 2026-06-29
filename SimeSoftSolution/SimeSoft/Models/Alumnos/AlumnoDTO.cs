using System.Text.Json.Serialization;

namespace SimeSoft.Models.Alumnos;

public class AlumnoDto
{
    [JsonPropertyName("idAlumno")]
    public int IdAlumno { get; set; }

    [JsonPropertyName("dni")]
    public string Dni { get; set; } = "";

    [JsonPropertyName("nombres")]
    public string Nombres { get; set; } = "";

    [JsonPropertyName("apellidoPaterno")]
    public string ApellidoPaterno { get; set; } = "";

    [JsonPropertyName("apellidoMaterno")]
    public string ApellidoMaterno { get; set; } = "";

    [JsonPropertyName("direccion")]
    public string Direccion { get; set; } = "";

    [JsonPropertyName("telefono")]
    public string Telefono { get; set; } = "";

    [JsonPropertyName("correo")]
    public string Correo { get; set; } = "";

    [JsonPropertyName("fechaNacimiento")]
    public string FechaNacimiento { get; set; } = "";

    [JsonPropertyName("alumnoNuevo")]
    public bool AlumnoNuevo { get; set; }

    [JsonPropertyName("activo")]
    public bool Activo { get; set; }
}