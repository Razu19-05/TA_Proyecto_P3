namespace SimeSoft.Components.State;

public class PagoState
{
    public bool AlumnoEncontrado { get; set; } = false;

    public string CriterioBusqueda { get; set; } = "";
    public string PeriodoSeleccionado { get; set; } = "";
    public string EstadoSeleccionado { get; set; } = "";

    public AlumnoPago Alumno { get; set; } = new();

    public List<DeudaPago> Deudas { get; set; } = new();

    public DeudaPago? DeudaSeleccionada { get; set; }

    public void BuscarAlumnoDemo(string criterio)
    {
        CriterioBusqueda = criterio;
        AlumnoEncontrado = true;

        Alumno = new AlumnoPago
        {
            Dni = "72483517",
            NombreCompleto = "Mateo Alejandro Bello Ruiz",
            Nivel = "Primaria",
            Grado = "5to Primaria"
        };

        if (!Deudas.Any())
        {
            Deudas = new List<DeudaPago>
            {
                new DeudaPago
                {
                    Concepto = "Matrícula",
                    Periodo = "2026",
                    FechaEmision = "10/01/2026",
                    FechaVencimiento = "30/01/2026",
                    MontoFinal = 150,
                    Estado = "Pagado",
                    FechaPago = "15/01/2026",
                    Observacion = "Pago confirmado"
                },
                new DeudaPago
                {
                    Concepto = "Pensión Marzo",
                    Periodo = "2026",
                    FechaEmision = "01/03/2026",
                    FechaVencimiento = "30/03/2026",
                    MontoFinal = 250,
                    Estado = "Pagado",
                    FechaPago = "16/03/2026",
                    Observacion = "Pago registrado correctamente"
                },
                new DeudaPago
                {
                    Concepto = "Pensión Abril",
                    Periodo = "2026",
                    FechaEmision = "01/04/2026",
                    FechaVencimiento = "30/04/2026",
                    MontoFinal = 250,
                    Estado = "Pendiente",
                    FechaPago = "--",
                    Observacion = "Pendiente de cancelación"
                },
                new DeudaPago
                {
                    Concepto = "Examen Psicológico",
                    Periodo = "2026",
                    FechaEmision = "08/01/2026",
                    FechaVencimiento = "20/01/2026",
                    MontoFinal = 100,
                    Estado = "Pendiente",
                    FechaPago = "--",
                    Observacion = "No corresponde a alumno regular"
                }
            };
        }
    }

    public void SeleccionarDeuda(DeudaPago deuda)
    {
        DeudaSeleccionada = deuda;
    }

    public void PagarDeuda(DateTime fechaPago, string observacion)
    {
        if (DeudaSeleccionada is null) return;

        DeudaSeleccionada.Estado = "Pagado";
        DeudaSeleccionada.FechaPago = fechaPago.ToString("dd/MM/yyyy");
        DeudaSeleccionada.Observacion = string.IsNullOrWhiteSpace(observacion)
            ? "Pago registrado correctamente"
            : observacion;
    }

    public void AnularDeuda(string observacion)
    {
        if (DeudaSeleccionada is null) return;

        DeudaSeleccionada.Estado = "Anulado";
        DeudaSeleccionada.FechaPago = "--";
        DeudaSeleccionada.Observacion = observacion;
    }
    public void LimpiarBusqueda()
    {
        AlumnoEncontrado = false;

        CriterioBusqueda = "";
        PeriodoSeleccionado = "";
        EstadoSeleccionado = "";

        Alumno = new AlumnoPago();
        Deudas.Clear();
        DeudaSeleccionada = null;
    }
    public bool TieneDeudasPendientes =>
    Deudas.Any(d => d.Estado == "Pendiente");

    public void CargarAlumnoRegularConDeudas()
    {
        AlumnoEncontrado = true;
        VieneDeMatriculaRegular = true;
        RutaRetornoMatricula = "/Matricula/matriculas/regular";

        CriterioBusqueda = "72483517";
        PeriodoSeleccionado = "";
        EstadoSeleccionado = "";

        Alumno = new AlumnoPago
        {
            Dni = "72483517",
            NombreCompleto = "Mateo Alejandro Bello Ruiz",
            Nivel = "Secundaria",
            Grado = "5to"
        };

        if (!Deudas.Any())
        {
            Deudas = new List<DeudaPago>
        {
            new DeudaPago
            {
                Concepto = "Pensión Marzo",
                Periodo = "2026",
                FechaEmision = "01/03/2026",
                FechaVencimiento = "30/03/2026",
                MontoFinal = 450,
                Estado = "Pendiente",
                FechaPago = "--",
                Observacion = "Pago pendiente"
            },
            new DeudaPago
            {
                Concepto = "Pensión Abril",
                Periodo = "2026",
                FechaEmision = "01/04/2026",
                FechaVencimiento = "30/04/2026",
                MontoFinal = 450,
                Estado = "Pendiente",
                FechaPago = "--",
                Observacion = "Pago pendiente"
            },
            new DeudaPago
            {
                Concepto = "Examen Psicológico",
                Periodo = "2026",
                FechaEmision = "08/01/2026",
                FechaVencimiento = "20/01/2026",
                MontoFinal = 100,
                Estado = "Pendiente",
                FechaPago = "--",
                Observacion = "No cancelado"
            }
        };
        }
    }

    public bool VieneDeMatriculaRegular { get; set; } = false;

    public string RutaRetornoMatricula { get; set; } = "/Matricula/matriculas/regular";

}

public class AlumnoPago
{
    public string Dni { get; set; } = "";
    public string NombreCompleto { get; set; } = "";
    public string Nivel { get; set; } = "";
    public string Grado { get; set; } = "";
}

public class DeudaPago
{
    public string Concepto { get; set; } = "";
    public string Periodo { get; set; } = "";
    public string FechaEmision { get; set; } = "";
    public string FechaVencimiento { get; set; } = "";
    public decimal MontoFinal { get; set; }
    public string Estado { get; set; } = "";
    public string FechaPago { get; set; } = "";
    public string Observacion { get; set; } = "";
}