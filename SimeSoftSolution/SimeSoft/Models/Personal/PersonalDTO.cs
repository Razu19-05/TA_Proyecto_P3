using System.Text.Json.Serialization;

namespace SimeSoft.Models.Personal;

public class PersonalDto
{
    public int IdPersona { get; set; }

    public string Dni { get; set; } = "";
    public string Nombres { get; set; } = "";
    public string ApellidoPaterno { get; set; } = "";
    public string ApellidoMaterno { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";
    public string Direccion { get; set; } = "";

    // En Java debe llegar como: PROFESOR, ADMINISTRADOR, PERSONAL_SERVICIO
    public string Tipo { get; set; } = "";

    public string? Especialidad { get; set; }
    public string? Cargo { get; set; }
    public string? Area { get; set; }

    public bool Activo { get; set; } = true;

    [JsonIgnore]
    public string NombreCompleto =>
        $"{Nombres} {ApellidoPaterno} {ApellidoMaterno}";

    [JsonIgnore]
    public string Categoria =>
        Tipo switch
        {
            "PROFESOR" => "Profesor",
            "ADMINISTRADOR" => "Administrador",
            "PERSONAL_SERVICIO" => "Personal Servicio",
            _ => Tipo
        };

    [JsonIgnore]
    public string DatoAdicional =>
        Tipo switch
        {
            "PROFESOR" => Especialidad ?? "",
            "ADMINISTRADOR" => Cargo ?? "",
            "PERSONAL_SERVICIO" => Area ?? "",
            _ => ""
        };
}