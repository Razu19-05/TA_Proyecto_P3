namespace SimeSoft.Models.Matriculas;

public class ApoderadoNuevoModel
{
    public int Id { get; set; }

    public string Dni { get; set; } = "";
    public string Nombres { get; set; } = "";
    public string ApellidoPaterno { get; set; } = "";
    public string ApellidoMaterno { get; set; } = "";

    public string Parentesco { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";
    public string Direccion { get; set; } = "";

    public string Tipo { get; set; } = "EXTERNO";
    public string Especialidad { get; set; } = "";
    public string Cargo { get; set; } = "";
    public string Area { get; set; } = "";

    public string Observaciones { get; set; } = "";
    public bool ContactoEmergencia { get; set; }

    public string NombreCompleto => $"{Nombres} {ApellidoPaterno} {ApellidoMaterno}";
}