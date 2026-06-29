namespace SimeSoft.Models.Aulas;

public class AulaGuardarDto
{
    public int IdAula { get; set; }
    public string Codigo { get; set; } = "";
    public string Tipo { get; set; } = "AULA";
    public int Capacidad { get; set; }
    public bool Activo { get; set; } = true;
}
