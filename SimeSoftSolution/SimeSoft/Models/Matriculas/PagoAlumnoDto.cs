using System.Text.Json.Serialization;

namespace SimeSoft.Models.Matriculas;

// Mapea a pe.edu.pucp.SIME.model.gestionPagos.Pago devuelto por
// GET PagoRS/listar_pagos_alumno/{idAlumno}.
public class PagoAlumnoDto
{
    [JsonPropertyName("idPago")]
    public int IdPago { get; set; }

    [JsonPropertyName("matriculaDetalle")]
    public MatriculaDetalleAlumnoDto? MatriculaDetalle { get; set; }

    [JsonPropertyName("conceptoPago")]
    public ConceptoPagoAlumnoDto? ConceptoPago { get; set; }

    [JsonPropertyName("montoDescuento")]
    public double MontoDescuento { get; set; }

    [JsonPropertyName("montoFinal")]
    public double MontoFinal { get; set; }

    // El backend serializa java.util.Date; se recibe como string y se parsea.
    [JsonPropertyName("fechaEmision")]
    public string? FechaEmision { get; set; }

    [JsonPropertyName("fechaVencimiento")]
    public string? FechaVencimiento { get; set; }

    [JsonPropertyName("fechaPago")]
    public string? FechaPago { get; set; }

    // Enum TipoEstado: PENDIENTE, PAGADO, ANULADO.
    [JsonPropertyName("estado")]
    public string Estado { get; set; } = "";

    [JsonPropertyName("observacion")]
    public string Observacion { get; set; } = "";
}

// Solo necesitamos el id para poder relacionar el pago con su matrícula
// (PagosDelDetalle en la ficha del alumno). El backend manda el objeto
// MatriculaDetalle completo, pero el resto de sus campos no se usan aquí.
public class MatriculaDetalleAlumnoDto
{
    [JsonPropertyName("idMatriculaDetalle")]
    public int IdMatriculaDetalle { get; set; }
}

public class ConceptoPagoAlumnoDto
{
    [JsonPropertyName("nombre")]
    public string Nombre { get; set; } = "";

    [JsonPropertyName("monto")]
    public double Monto { get; set; }
}
