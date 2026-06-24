namespace SimeSoft.Components.State;

public class MatriculaState
{
    public List<AlumnoMatriculaModel> Alumnos { get; set; } = new();
    public List<ApoderadoMatriculaModel> Apoderados { get; set; } = new();
    public List<VacanteMatriculaModel> Vacantes { get; set; } = new();
    public List<ConceptoPagoMatriculaModel> ConceptosPago { get; set; } = new();
    public List<TipoDescuentoMatriculaModel> TiposDescuento { get; set; } = new();
    public List<PagoGeneradoMatriculaModel> PagosExistentes { get; set; } = new();
    public List<HistorialMatriculaModel> HistorialMatriculas { get; set; } = new();
    public int? AlumnoRegularEnProcesoId { get; set; }
    public bool VieneDePagoAlumnoRegular { get; set; }
    public bool LimpiarPagosAlVolver { get; set; }

    public PagoGeneradoMatriculaModel? DeudaSeleccionada { get; set; }

    public int? AlumnoPagoSeleccionadoId { get; set; }

    public VacanteMatriculaModel? VacanteSeleccionada { get; set; }

    public string TipoFlujo { get; set; } = "NUEVO";

    public string CriterioPagoActual { get; set; } = "";

    public MatriculaState()
    {
        CargarDemo();
    }

    public void SeleccionarVacante(VacanteMatriculaModel vacante, string tipoFlujo)
    {
        VacanteSeleccionada = vacante;
        TipoFlujo = tipoFlujo;
    }

    public List<VacanteMatriculaModel> FiltrarVacantes(string periodo, string nivel, string grado)
    {
        return Vacantes
            .Where(v =>
                (periodo == "Todos" || v.Periodo == periodo) &&
                (nivel == "Todos" || v.Nivel == nivel) &&
                (grado == "Todos" || v.Grado == grado) &&
                v.Activo)
            .OrderBy(v => v.Periodo)
            .ThenBy(v => v.Nivel)
            .ThenBy(v => v.Grado)
            .ThenBy(v => v.Aula)
            .ToList();
    }

    public AlumnoMatriculaModel? BuscarAlumno(string criterio)
    {
        if (string.IsNullOrWhiteSpace(criterio))
        {
            return null;
        }

        return Alumnos.FirstOrDefault(a =>
            a.Dni.Contains(criterio, StringComparison.OrdinalIgnoreCase) ||
            a.Nombres.Contains(criterio, StringComparison.OrdinalIgnoreCase) ||
            a.ApellidoPaterno.Contains(criterio, StringComparison.OrdinalIgnoreCase) ||
            a.ApellidoMaterno.Contains(criterio, StringComparison.OrdinalIgnoreCase));
    }

    public List<ApoderadoMatriculaModel> ObtenerApoderados(int alumnoId)
    {
        return Apoderados
            .Where(a => a.AlumnoId == alumnoId && a.Activo)
            .ToList();
    }

    public List<PagoGeneradoMatriculaModel> ObtenerPagosAlumno(int alumnoId)
    {
        return PagosExistentes
            .Where(p => p.AlumnoId == alumnoId)
            .ToList();
    }

    public List<PagoGeneradoMatriculaModel> ObtenerDeudasPendientes(int alumnoId)
    {
        return PagosExistentes
            .Where(p => p.AlumnoId == alumnoId && p.Estado == "PENDIENTE")
            .ToList();
    }

    public List<PagoGeneradoMatriculaModel> GenerarPagos(
        bool alumnoNuevo,
        decimal porcentajeDescuento,
        string tipoDescuento,
        string motivoDescuento)
    {
        var conceptos = ConceptosPago
            .Where(c =>
                c.Activo &&
                (
                    c.Nombre == "MATRICULA" ||
                    c.Nombre == "PENSION" ||
                    c.Nombre == "UTILES" ||
                    (alumnoNuevo && (c.Nombre == "INSCRIPCION" || c.Nombre == "EXAMEN_PSICOLOGICO"))
                ))
            .ToList();

        var pagos = new List<PagoGeneradoMatriculaModel>();

        foreach (var concepto in conceptos)
        {
            decimal descuento = 0;

            if (concepto.Nombre == "MATRICULA" && porcentajeDescuento > 0)
            {
                descuento = Math.Round(concepto.Monto * porcentajeDescuento / 100m, 2);
            }

            pagos.Add(new PagoGeneradoMatriculaModel
            {
                Concepto = concepto.Nombre,
                MontoOriginal = concepto.Monto,
                MontoDescuento = descuento,
                MontoFinal = concepto.Monto - descuento,
                FechaEmision = DateTime.Today,
                FechaVencimiento = concepto.Nombre == "PENSION"
                    ? new DateTime(2026, 3, 30)
                    : new DateTime(2026, 1, 20),
                FechaPago = null,
                Estado = "PENDIENTE",
                Observacion = descuento > 0
                    ? $"Descuento {tipoDescuento} aplicado. Motivo: {motivoDescuento}"
                    : "Pago generado por matrícula"
            });
        }

        return pagos;
    }
    public void MarcarDeudasComoPagadas(int alumnoId)
    {
        var deudas = PagosExistentes
            .Where(p => p.AlumnoId == alumnoId && p.Estado == "PENDIENTE")
            .ToList();

        foreach (var deuda in deudas)
        {
            deuda.Estado = "PAGADO";
            deuda.FechaPago = DateTime.Today;
            deuda.Observacion = "Pago regularizado desde el módulo Pagos";
        }
    }

    public bool RegistrarMatricula(VacanteMatriculaModel vacante)
    {
        if (vacante.VacantesDisponibles <= 0)
        {
            return false;
        }

        vacante.VacantesOcupadas++;

        return true;
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
                ApellidoPaterno = "Martinez",
                ApellidoMaterno = "Perez",
                Direccion = "Lima",
                Telefono = "988111111",
                Correo = "luis@gmail.com",
                FechaNacimiento = new DateTime(2015, 5, 10),
                AlumnoNuevo = true,
                Activo = true
            },
            new()
            {
                Id = 2,
                Dni = "80000002",
                Nombres = "Lucia",
                ApellidoPaterno = "Martinez",
                ApellidoMaterno = "Perez",
                Direccion = "Lima",
                Telefono = "988222222",
                Correo = "lucia@gmail.com",
                FechaNacimiento = new DateTime(2016, 8, 15),
                AlumnoNuevo = false,
                Activo = true
            },
            new()
            {
                Id = 3,
                Dni = "80000003",
                Nombres = "Pedro",
                ApellidoPaterno = "Garcia",
                ApellidoMaterno = "Lopez",
                Direccion = "Lima",
                Telefono = "988333333",
                Correo = "pedro@gmail.com",
                FechaNacimiento = new DateTime(2014, 1, 20),
                AlumnoNuevo = false,
                Activo = true
            },

            new()
            {
                Id = 4,
                Dni = "80000004",
                Nombres = "Mateo",
                ApellidoPaterno = "Quispe",
                ApellidoMaterno = "Ramos",
                Direccion = "Sunampe",
                Telefono = "988444444",
                Correo = "mateo@gmail.com",
                FechaNacimiento = new DateTime(2015, 9, 12),
                AlumnoNuevo = false,
                Activo = true
            }
        };

        Apoderados = new()
        {
            new()
            {
                Id = 1,
                AlumnoId = 1,
                Nombres = "Juan",
                ApellidoPaterno = "Perez",
                ApellidoMaterno = "Lopez",
                Dni = "70000001",
                Telefono = "999111111",
                Correo = "juan@colegio.edu",
                Direccion = "Lima",
                Tipo = "PROFESOR",
                Especialidad = "Matematica",
                Cargo = "Docente",
                Area = "Académica",
                Parentesco = "PADRE",
                ContactoEmergencia = true,
                Observaciones = "Apoderado principal",
                Activo = true
            },
            new()
            {
                Id = 2,
                AlumnoId = 2,
                Nombres = "Maria",
                ApellidoPaterno = "Gomez",
                ApellidoMaterno = "Rojas",
                Dni = "70000002",
                Telefono = "999222222",
                Correo = "maria@colegio.edu",
                Direccion = "Lima",
                Tipo = "PROFESOR",
                Especialidad = "Comunicacion",
                Cargo = "Docente",
                Area = "Académica",
                Parentesco = "MADRE",
                ContactoEmergencia = true,
                Observaciones = "Apoderada principal",
                Activo = true
            },
            new()
            {
                Id = 3,
                AlumnoId = 3,
                Nombres = "Rosa",
                ApellidoPaterno = "Lopez",
                ApellidoMaterno = "Ramos",
                Dni = "70000003",
                Telefono = "999333333",
                Correo = "rosa@gmail.com",
                Direccion = "Lima",
                Tipo = "EXTERNO",
                Especialidad = "",
                Cargo = "",
                Area = "",
                Parentesco = "MADRE",
                ContactoEmergencia = true,
                Observaciones = "Apoderada principal",
                Activo = true
            },
            new()
            {
                Id = 4,
                AlumnoId = 4,
                Nombres = "Carlos",
                ApellidoPaterno = "Quispe",
                ApellidoMaterno = "Torres",
                Dni = "70000004",
                Telefono = "999444444",
                Correo = "carlos@gmail.com",
                Direccion = "Sunampe",
                Tipo = "EXTERNO",
                Especialidad = "",
                Cargo = "",
                Area = "",
                Parentesco = "PADRE",
                ContactoEmergencia = true,
                Observaciones = "Apoderado principal",
                Activo = true
            }
        };

        Vacantes = new()
        {
            new()
            {
                Id = 1,
                Periodo = "2026",
                Nivel = "Primaria",
                Grado = "5°",
                Aula = "A101",
                TotalVacantes = 30,
                VacantesOcupadas = 2,
                FechaInicio = new DateTime(2026, 1, 5),
                FechaFin = new DateTime(2026, 3, 15),
                Activo = true
            },
            new()
            {
                Id = 2,
                Periodo = "2026",
                Nivel = "Primaria",
                Grado = "6°",
                Aula = "A102",
                TotalVacantes = 30,
                VacantesOcupadas = 25,
                FechaInicio = new DateTime(2026, 1, 5),
                FechaFin = new DateTime(2026, 3, 15),
                Activo = true
            },
            new()
            {
                Id = 3,
                Periodo = "2026",
                Nivel = "Secundaria",
                Grado = "1°",
                Aula = "S201",
                TotalVacantes = 30,
                VacantesOcupadas = 30,
                FechaInicio = new DateTime(2026, 1, 5),
                FechaFin = new DateTime(2026, 3, 15),
                Activo = true
            },
            new()
            {
                Id = 4,
                Periodo = "2026",
                Nivel = "Inicial",
                Grado = "5 AÑOS",
                Aula = "I105",
                TotalVacantes = 25,
                VacantesOcupadas = 10,
                FechaInicio = new DateTime(2026, 1, 5),
                FechaFin = new DateTime(2026, 3, 15),
                Activo = true
            }
        };

        ConceptosPago = new()
        {
            new() { Nombre = "MATRICULA", Monto = 350.00m, Activo = true },
            new() { Nombre = "PENSION", Monto = 450.00m, Activo = true },
            new() { Nombre = "INSCRIPCION", Monto = 100.00m, Activo = true },
            new() { Nombre = "EXAMEN_PSICOLOGICO", Monto = 80.00m, Activo = true },
            new() { Nombre = "UTILES", Monto = 150.00m, Activo = true },
            new() { Nombre = "BUZO", Monto = 120.00m, Activo = true }
        };

        TiposDescuento = new()
        {
            new()
            {
                Nombre = "NINGUNO",
                Descripcion = "Sin descuento",
                Activo = true
            },
            new()
            {
                Nombre = "HERMANOS",
                Descripcion = "Descuento por hermanos matriculados",
                Activo = true
            },
            new()
            {
                Nombre = "BECA_CONCURSO",
                Descripcion = "Descuento por concurso escolar",
                Activo = true
            }
        };

        PagosExistentes = new()
        {
            new()
            {
                AlumnoId = 1,
                Concepto = "MATRICULA",
                MontoOriginal = 350.00m,
                MontoDescuento = 175.00m,
                MontoFinal = 175.00m,
                FechaEmision = new DateTime(2026, 1, 10),
                FechaVencimiento = new DateTime(2026, 1, 20),
                FechaPago = new DateTime(2026, 1, 15),
                Estado = "PAGADO",
                Observacion = "Pago confirmado"
            },
            new()
            {
                AlumnoId = 2,
                Concepto = "MATRICULA",
                MontoOriginal = 350.00m,
                MontoDescuento = 43.75m,
                MontoFinal = 306.25m,
                FechaEmision = new DateTime(2026, 1, 12),
                FechaVencimiento = new DateTime(2026, 1, 22),
                FechaPago = null,
                Estado = "PENDIENTE",
                Observacion = "Pendiente de pago"
            },
            new()
            {
                AlumnoId = 4,
                Concepto = "PENSION",
                MontoOriginal = 450.00m,
                MontoDescuento = 0.00m,
                MontoFinal = 450.00m,
                FechaEmision = new DateTime(2026, 3, 1),
                FechaVencimiento = new DateTime(2026, 3, 30),
                FechaPago = null,
                Estado = "PENDIENTE",
                Observacion = "Pendiente de pago"
            }
        };
    }


    public void SeleccionarDeuda(int alumnoId, PagoGeneradoMatriculaModel deuda)
    {
        AlumnoPagoSeleccionadoId = alumnoId;
        DeudaSeleccionada = deuda;
    }

    public AlumnoMatriculaModel? ObtenerAlumnoPorId(int alumnoId)
    {
        return Alumnos.FirstOrDefault(a => a.Id == alumnoId);
    }

    public void PagarDeudaSeleccionada()
    {
        if (DeudaSeleccionada is null)
        {
            return;
        }

        DeudaSeleccionada.Estado = "PAGADO";
        DeudaSeleccionada.FechaPago = DateTime.Today;
        DeudaSeleccionada.Observacion = "Pago regularizado";
    }

    public void AnularDeudaSeleccionada()
    {
        if (DeudaSeleccionada is null)
        {
            return;
        }

        DeudaSeleccionada.Estado = "ANULADO";
        DeudaSeleccionada.Observacion = "Deuda anulada";
    }

    public void RegistrarHistorialMatricula(AlumnoMatriculaModel alumno, VacanteMatriculaModel vacante)
    {
        var existe = HistorialMatriculas.Any(h =>
            h.AlumnoId == alumno.Id &&
            h.Periodo == vacante.Periodo &&
            h.Nivel == NormalizarNivelHistorial(vacante.Nivel) &&
            h.Grado == vacante.Grado &&
            h.Activo);

        if (existe)
        {
            return;
        }

        HistorialMatriculas.Add(new HistorialMatriculaModel
        {
            Id = HistorialMatriculas.Any()
                ? HistorialMatriculas.Max(h => h.Id) + 1
                : 1,

            AlumnoId = alumno.Id,
            Dni = alumno.Dni,
            NombresCompletos = alumno.NombreCompleto,
            Periodo = vacante.Periodo,
            Nivel = NormalizarNivelHistorial(vacante.Nivel),
            Grado = vacante.Grado,
            Aula = vacante.Aula,
            FechaMatricula = DateTime.Today,
            Estado = "MATRICULADO",
            Activo = true
        });
    }

    public void EliminarHistorialMatricula(int historialId)
    {
        var registro = HistorialMatriculas.FirstOrDefault(h => h.Id == historialId);

        if (registro is not null)
        {
            registro.Activo = false;
        }
    }

    private string NormalizarNivelHistorial(string nivel)
    {
        return nivel.ToUpper() switch
        {
            "INICIAL" => "Inicial",
            "PRIMARIA" => "Primaria",
            "SECUNDARIA" => "Secundaria",
            _ => nivel
        };
    }
}

public class HistorialMatriculaModel
{
    public int Id { get; set; }
    public int AlumnoId { get; set; }
    public string Dni { get; set; } = "";
    public string NombresCompletos { get; set; } = "";
    public string Periodo { get; set; } = "";
    public string Nivel { get; set; } = "";
    public string Grado { get; set; } = "";
    public string Aula { get; set; } = "";
    public DateTime FechaMatricula { get; set; }
    public string Estado { get; set; } = "MATRICULADO";
    public bool Activo { get; set; } = true;
}

public class AlumnoMatriculaModel
{
    public int Id { get; set; }

    public string Dni { get; set; } = "";
    public string Nombres { get; set; } = "";
    public string ApellidoPaterno { get; set; } = "";
    public string ApellidoMaterno { get; set; } = "";

    public string Direccion { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";

    public DateTime FechaNacimiento { get; set; }

    public bool AlumnoNuevo { get; set; }

    public bool Activo { get; set; } = true;

    public string NombreCompleto => $"{Nombres} {ApellidoPaterno} {ApellidoMaterno}";
}

public class ApoderadoMatriculaModel
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

    public string Tipo { get; set; } = "EXTERNO";
    public string Especialidad { get; set; } = "";
    public string Cargo { get; set; } = "";
    public string Area { get; set; } = "";

    public string Parentesco { get; set; } = "PADRE";

    public bool ContactoEmergencia { get; set; }

    public string Observaciones { get; set; } = "";

    public bool Activo { get; set; } = true;

    public string NombreCompleto => $"{Nombres} {ApellidoPaterno} {ApellidoMaterno}";
}

public class VacanteMatriculaModel
{
    public int Id { get; set; }

    public string Periodo { get; set; } = "";
    public string Nivel { get; set; } = "";
    public string Grado { get; set; } = "";
    public string Aula { get; set; } = "";

    public int TotalVacantes { get; set; }

    public int VacantesOcupadas { get; set; }

    public int VacantesDisponibles => TotalVacantes - VacantesOcupadas;

    public DateTime FechaInicio { get; set; }

    public DateTime FechaFin { get; set; }

    public bool Activo { get; set; } = true;
}

public class ConceptoPagoMatriculaModel
{
    public string Nombre { get; set; } = "";

    public decimal Monto { get; set; }

    public bool Activo { get; set; } = true;
}

public class TipoDescuentoMatriculaModel
{
    public string Nombre { get; set; } = "";

    public string Descripcion { get; set; } = "";

    public bool Activo { get; set; } = true;
}

public class PagoGeneradoMatriculaModel
{
    public int AlumnoId { get; set; }

    public string Concepto { get; set; } = "";

    public decimal MontoOriginal { get; set; }

    public decimal MontoDescuento { get; set; }

    public decimal MontoFinal { get; set; }

    public DateTime FechaEmision { get; set; }

    public DateTime FechaVencimiento { get; set; }

    public DateTime? FechaPago { get; set; }

    public string Estado { get; set; } = "PENDIENTE";

    public string Observacion { get; set; } = "";
}