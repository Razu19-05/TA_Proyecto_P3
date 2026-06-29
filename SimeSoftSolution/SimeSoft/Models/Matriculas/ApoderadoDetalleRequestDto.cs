namespace SimeSoft.Models.Matriculas;

// Mapea a pe.edu.pucp.SIME.model.DTO.ApoderadoDetalleDTO.
// El backend anida la Persona dentro de "apoderado".
public class ApoderadoDetalleRequestDto
{
    public PersonaRequestDto Apoderado { get; set; } = new();

    // El backend lo deserializa como enum TipoRelacionFamiliar:
    // PADRE, MADRE, ABUELO, ABUELA, TIO, TIA, HERMANO, OTRO.
    public string Parentesco { get; set; } = "";

    public bool ContactoEmergencia { get; set; }
    public string ObservacionesFamiliares { get; set; } = "";
}

// Mapea a pe.edu.pucp.SIME.model.gestionDePersonal.Persona.
public class PersonaRequestDto
{
    public int IdPersona { get; set; }
    public string Nombres { get; set; } = "";
    public string ApellidoPaterno { get; set; } = "";
    public string ApellidoMaterno { get; set; } = "";
    public string Dni { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";
    public string Direccion { get; set; } = "";

    // Enum TipoPersona: EXTERNO, PROFESOR, ADMINISTRADOR, PERSONAL_SERVICIO.
    public string Tipo { get; set; } = "EXTERNO";
    public string Especialidad { get; set; } = "";
    public string Cargo { get; set; } = "";
    public string Area { get; set; } = "";
    public bool Activo { get; set; } = true;
}
