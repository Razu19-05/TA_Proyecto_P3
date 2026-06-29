namespace SimeSoft.Models.Matriculas;

public class PagoMatriculaResponseDto
{
    public string Concepto { get; set; } = "";
    public double MontoOriginal { get; set; }
    public double MontoDescuento { get; set; }
    public double MontoFinal { get; set; }
    public string FechaVencimiento { get; set; } = "";
    public string Estado { get; set; } = "";
}
