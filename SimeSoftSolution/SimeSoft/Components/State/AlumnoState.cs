namespace SimeSoft.Components.State;

public class AlumnoState
{
    public AlumnoModel? AlumnoSeleccionado { get; set; }

    public List<AlumnoModel> Alumnos { get; set; } = new();
    public List<ApoderadoModel> Apoderados { get; set; } = new();
    public List<PagoAlumnoModel> Pagos { get; set; } = new();
    public List<MatriculaAlumnoModel> Matriculas { get; set; } = new();

    public AlumnoState()
    {
        CargarDemo();
    }

    public AlumnoModel? BuscarAlumno(string criterio)
    {
        if (string.IsNullOrWhiteSpace(criterio))
            return null;

        return Alumnos.FirstOrDefault(a =>
            a.Dni.Contains(criterio, StringComparison.OrdinalIgnoreCase) ||
            a.Nombres.Contains(criterio, StringComparison.OrdinalIgnoreCase) ||
            a.ApellidoPaterno.Contains(criterio, StringComparison.OrdinalIgnoreCase) ||
            a.ApellidoMaterno.Contains(criterio, StringComparison.OrdinalIgnoreCase));
    }

    public List<ApoderadoModel> ObtenerApoderados(int alumnoId)
    {
        return Apoderados.Where(a => a.AlumnoId == alumnoId).ToList();
    }

    public List<PagoAlumnoModel> ObtenerPagos(int alumnoId)
    {
        return Pagos.Where(p => p.AlumnoId == alumnoId).ToList();
    }

    public List<MatriculaAlumnoModel> ObtenerMatriculas(int alumnoId)
    {
        return Matriculas
            .Where(m => m.AlumnoId == alumnoId)
            .OrderByDescending(m => m.Periodo)
            .ToList();
    }

    public void GuardarApoderado(ApoderadoModel apoderado)
    {
        var actual = Apoderados.FirstOrDefault(a => a.Id == apoderado.Id);

        if (actual is null)
        {
            apoderado.Id = Apoderados.Count == 0 ? 1 : Apoderados.Max(a => a.Id) + 1;
            Apoderados.Add(apoderado);
            return;
        }

        actual.Nombres = apoderado.Nombres;
        actual.ApellidoPaterno = apoderado.ApellidoPaterno;
        actual.ApellidoMaterno = apoderado.ApellidoMaterno;
        actual.Dni = apoderado.Dni;
        actual.Telefono = apoderado.Telefono;
        actual.Correo = apoderado.Correo;
        actual.Direccion = apoderado.Direccion;
        actual.Parentesco = apoderado.Parentesco;
        actual.ContactoEmergencia = apoderado.ContactoEmergencia;
        actual.Observacion = apoderado.Observacion;
        actual.TipoPersona = apoderado.TipoPersona;
        actual.Especialidad = apoderado.Especialidad;
        actual.Cargo = apoderado.Cargo;
        actual.Area = apoderado.Area;
        actual.Activo = apoderado.Activo;
    }

    public void EliminarApoderado(int id)
    {
        var apoderado = Apoderados.FirstOrDefault(a => a.Id == id);

        if (apoderado is not null)
            Apoderados.Remove(apoderado);
    }

    private void CargarDemo()
    {
        Alumnos = new()
        {
            new()
            {
                Id = 1,
                Dni = "80000001",
                Nombres = "Luis",
                ApellidoPaterno = "Martínez",
                ApellidoMaterno = "Pérez",
                Direccion = "Lima",
                Telefono = "988 111 111",
                Correo = "luis@gmail.com",
                FechaNacimiento = "10/05/2015",
                AlumnoNuevo = true,
                Estado = "Activo",
                DescuentoActual = "BECA ACADÉMICA",
                PorcentajeDescuento = 50,
                TieneDescuento = true
            }
        };

        Apoderados = new()
        {
            new()
            {
                Id = 1,
                AlumnoId = 1,
                Nombres = "Juan",
                ApellidoPaterno = "Pérez",
                ApellidoMaterno = "López",
                Dni = "70000001",
                Telefono = "999 111 111",
                Correo = "juan@colegio.edu",
                Direccion = "Lima",
                Parentesco = "PADRE",
                ContactoEmergencia = true,
                Observacion = "Apoderado principal",
                TipoPersona = "PROFESOR",
                Especialidad = "Matemática",
                Cargo = "Docente",
                Area = "Académica",
                Activo = true
            }
        };

            Pagos = new()
    {
        new()
        {
            Id = 1,
            AlumnoId = 1,
            MatriculaId = 1,
            Concepto = "MATRICULA",
            MontoDescuento = 175.00m,
            MontoFinal = 175.00m,
            FechaEmision = "10/01/2026",
            FechaVencimiento = "20/01/2026",
            FechaPago = "15/01/2026",
            Estado = "PAGADO",
            Observacion = "Pago confirmado"
        },
        new()
        {
            Id = 2,
            AlumnoId = 1,
            MatriculaId = 1,
            Concepto = "PENSION",
            MontoDescuento = 0.00m,
            MontoFinal = 450.00m,
            FechaEmision = "01/03/2026",
            FechaVencimiento = "30/03/2026",
            FechaPago = "",
            Estado = "PENDIENTE",
            Observacion = "Pendiente de pago"
        },
        new()
        {
            Id = 3,
            AlumnoId = 1,
            MatriculaId = 1,
            Concepto = "EXAMEN_PSICOLOGICO",
            MontoDescuento = 0.00m,
            MontoFinal = 80.00m,
            FechaEmision = "10/01/2026",
            FechaVencimiento = "20/01/2026",
            FechaPago = "",
            Estado = "ANULADO",
            Observacion = "Anulado por administración"
        }
    };

        Matriculas = new()
        {
        new()
        {
            Id = 1,
            AlumnoId = 1,
            Periodo = "2026",
            Nivel = "Primaria",
            Grado = "5°",
            FechaMatricula = "10/01/2026",
            FechaInicio = "05/01/2026",
            FechaFin = "15/03/2026",
            Estado = "MATRICULADO",
            Observacion = "Alumno con beca académica"
        }
        };
    }
}

public class AlumnoModel
{
    public int Id { get; set; }
    public string Dni { get; set; } = "";
    public string Nombres { get; set; } = "";
    public string ApellidoPaterno { get; set; } = "";
    public string ApellidoMaterno { get; set; } = "";
    public string Direccion { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";
    public string FechaNacimiento { get; set; } = "";
    public bool AlumnoNuevo { get; set; }
    public string Estado { get; set; } = "Activo";

    public bool TieneDescuento { get; set; }
    public string DescuentoActual { get; set; } = "";
    public int PorcentajeDescuento { get; set; }

    public string NombreCompleto => $"{Nombres} {ApellidoPaterno} {ApellidoMaterno}";
}

public class ApoderadoModel
{
    public int Id { get; set; }
    public int AlumnoId { get; set; }

    public string Nombres { get; set; } = "";
    public string ApellidoPaterno { get; set; } = "";
    public string ApellidoMaterno { get; set; } = "";
    public string Dni { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";
    public string Direccion { get; set; } = "";
    public string Parentesco { get; set; } = "";
    public bool ContactoEmergencia { get; set; }
    public string Observacion { get; set; } = "";

    public string TipoPersona { get; set; } = "";
    public string Especialidad { get; set; } = "";
    public string Cargo { get; set; } = "";
    public string Area { get; set; } = "";

    public bool Activo { get; set; } = true;

    public string NombreCompleto => $"{Nombres} {ApellidoPaterno} {ApellidoMaterno}";
}

public class PagoAlumnoModel
{
    public int Id { get; set; }
    public int AlumnoId { get; set; }
    public int MatriculaId { get; set; }

    public string Concepto { get; set; } = "";

    public decimal MontoDescuento { get; set; }
    public decimal MontoFinal { get; set; }

    public string FechaEmision { get; set; } = "";
    public string FechaVencimiento { get; set; } = "";
    public string FechaPago { get; set; } = "";

    // Según SIME_PAGO.estado:
    // PENDIENTE, PAGADO, ANULADO
    public string Estado { get; set; } = "";

    public string Observacion { get; set; } = "";
}


public class MatriculaAlumnoModel
{
    public int Id { get; set; }
    public int AlumnoId { get; set; }

    public string Periodo { get; set; } = "";
    public string Nivel { get; set; } = "";
    public string Grado { get; set; } = "";

    public string FechaInicio { get; set; } = "";
    public string FechaFin { get; set; } = "";
    public string FechaMatricula { get; set; } = "";

    // Según SIME_MATRICULA_DETALLE.estado:
    // MATRICULADO, RETIRADO, RESERVADO
    public string Estado { get; set; } = "MATRICULADO";

    public bool Activo { get; set; } = true;

    public string Observacion { get; set; } = "";
}
