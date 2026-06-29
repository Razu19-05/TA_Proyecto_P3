namespace SimeSoft.Models.Aulas;

public class AulaDto
{
    public int IdAula { get; set; }
    public int? IdMatriculaCabecera { get; set; }

    public string Periodo { get; set; } = "";
    public string Nivel { get; set; } = "";
    public string Grado { get; set; } = "";

    public string Codigo { get; set; } = "";
    public string TipoAula { get; set; } = "";

    public int Capacidad { get; set; }
    public int VacantesOcupadas { get; set; }
    public int VacantesDisponibles { get; set; }

    public string ProfesorEncargado { get; set; } = "Sin asignar";
    public string Estado { get; set; } = "";
    public bool EsCompartida { get; set; }

}
