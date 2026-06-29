namespace SimeSoft.Models.Matriculas;

// Mapea a pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar
// devuelto por GET ApoderadosRS/listar/{dni}.
// El backend anida la Persona y serializa el parentesco como string del enum
// TipoRelacionFamiliar (PADRE, MADRE, ABUELO, ABUELA, TIO, TIA, HERMANO, OTRO).
public class RelacionFamiliarDto
{
    public int IdRelacionFamiliar { get; set; }

    public PersonaRequestDto Persona { get; set; } = new();

    public string Parentesco { get; set; } = "";

    public bool ContactoEmergencia { get; set; }

    public string Observaciones { get; set; } = "";

    public bool Activo { get; set; } = true;
}
