using SimeSoft.Components.State;

namespace SimeSoft.Services.Matriculas;

public class PagoMatriculaAlumnoNuevoService
{
    public List<PagoGeneradoMatriculaModel> GenerarPagosAlumnoNuevo(
        IEnumerable<ConceptoPagoMatriculaModel> conceptosPago,
        decimal porcentajeDescuento,
        string tipoDescuento,
        string motivoDescuento)
    {
        return conceptosPago
            .Where(c => c.Activo && EsConceptoAlumnoNuevo(c.Nombre))
            .OrderBy(c => ObtenerOrdenConcepto(c.Nombre))
            .Select(c =>
            {
                decimal descuento = 0;
                string conceptoNormalizado = NormalizarConcepto(c.Nombre);

                if (conceptoNormalizado == "MATRICULA" && porcentajeDescuento > 0)
                {
                    descuento = Math.Round(c.Monto * porcentajeDescuento / 100m, 2);
                }

                return new PagoGeneradoMatriculaModel
                {
                    Concepto = conceptoNormalizado,
                    MontoOriginal = c.Monto,
                    MontoDescuento = descuento,
                    MontoFinal = c.Monto - descuento,
                    FechaEmision = DateTime.Today,
                    FechaVencimiento = conceptoNormalizado == "PENSION"
                        ? new DateTime(2026, 3, 30)
                        : new DateTime(2026, 1, 20),
                    FechaPago = null,
                    Estado = "PENDIENTE",
                    Observacion = descuento > 0
                        ? $"Descuento {tipoDescuento}. Motivo: {motivoDescuento}"
                        : "Pago generado por matrícula"
                };
            })
            .ToList();
    }

    public EstadoDescuentoMatricula CambiarTipoDescuento(string tipoDescuento)
    {
        if (tipoDescuento == "NINGUNO")
        {
            return new EstadoDescuentoMatricula
            {
                MotivoDescuento = "Sin descuento",
                PorcentajeDescuento = 0
            };
        }

        return new EstadoDescuentoMatricula
        {
            MotivoDescuento = "",
            PorcentajeDescuento = 0
        };
    }

    public EstadoDescuentoMatricula AplicarMotivoDescuento(
        string tipoDescuento,
        string motivoDescuento)
    {
        if (tipoDescuento == "NINGUNO")
        {
            return new EstadoDescuentoMatricula
            {
                MotivoDescuento = "Sin descuento",
                PorcentajeDescuento = 0
            };
        }

        if (string.IsNullOrWhiteSpace(motivoDescuento))
        {
            return new EstadoDescuentoMatricula
            {
                MotivoDescuento = "",
                PorcentajeDescuento = 0
            };
        }

        return new EstadoDescuentoMatricula
        {
            MotivoDescuento = motivoDescuento,
            PorcentajeDescuento = ObtenerPorcentajePorMotivo(tipoDescuento, motivoDescuento)
        };
    }

    public decimal ObtenerPorcentajePorMotivo(string tipo, string motivo)
    {
        if (tipo == "HERMANOS")
        {
            return motivo switch
            {
                "Primer hermano matriculado" => 12.50m,
                "Segundo hermano matriculado" => 25.00m,
                "Tercer hermano matriculado" => 50.00m,
                _ => 0m
            };
        }

        if (tipo == "BECA_CONCURSO")
        {
            return motivo switch
            {
                "Primer puesto en concurso del colegio" => 50.00m,
                "Segundo puesto en concurso del colegio" => 25.00m,
                _ => 0m
            };
        }

        return 0m;
    }

    public void RecalcularPagos(
        List<PagoGeneradoMatriculaModel> pagosGenerados,
        string tipoDescuento,
        string motivoDescuento)
    {
        foreach (var pago in pagosGenerados)
        {
            pago.MontoDescuento = 0;
            pago.MontoFinal = pago.MontoOriginal;
            pago.Observacion = "Pago generado por matrícula";
        }

        if (tipoDescuento == "NINGUNO" || string.IsNullOrWhiteSpace(motivoDescuento))
        {
            return;
        }

        decimal porcentajeDescuento = ObtenerPorcentajePorMotivo(tipoDescuento, motivoDescuento);

        var pagoMatricula = pagosGenerados.FirstOrDefault(p =>
            NormalizarConcepto(p.Concepto) == "MATRICULA");

        if (pagoMatricula is not null && porcentajeDescuento > 0)
        {
            pagoMatricula.MontoDescuento =
                Math.Round(pagoMatricula.MontoOriginal * porcentajeDescuento / 100m, 2);

            pagoMatricula.MontoFinal =
                pagoMatricula.MontoOriginal - pagoMatricula.MontoDescuento;

            pagoMatricula.Observacion =
                $"Descuento {tipoDescuento}. Motivo: {motivoDescuento}";
        }
    }

    private bool EsConceptoAlumnoNuevo(string concepto)
    {
        string conceptoNormalizado = NormalizarConcepto(concepto);

        return conceptoNormalizado == "MATRICULA" ||
               conceptoNormalizado == "PENSION" ||
               conceptoNormalizado == "UTILES" ||
               conceptoNormalizado == "INSCRIPCION" ||
               conceptoNormalizado == "EXAMEN_PSICOLOGICO";
    }

    private int ObtenerOrdenConcepto(string concepto)
    {
        string conceptoNormalizado = NormalizarConcepto(concepto);

        return conceptoNormalizado switch
        {
            "MATRICULA" => 1,
            "PENSION" => 2,
            "INSCRIPCION" => 3,
            "EXAMEN_PSICOLOGICO" => 4,
            "UTILES" => 5,
            _ => 99
        };
    }

    private string NormalizarConcepto(string concepto)
    {
        return concepto
            .Trim()
            .ToUpper()
            .Replace("Á", "A")
            .Replace("É", "E")
            .Replace("Í", "I")
            .Replace("Ó", "O")
            .Replace("Ú", "U")
            .Replace(" ", "_");
    }
}

public class EstadoDescuentoMatricula
{
    public string MotivoDescuento { get; set; } = "";
    public decimal PorcentajeDescuento { get; set; }
}