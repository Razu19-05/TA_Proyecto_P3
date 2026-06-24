namespace SimeSoft.Components.State;

public class AulaState
{
    public List<AulaModel> Aulas { get; set; } = new();

    public List<ProfesorAulaModel> Profesores { get; set; } = new();

    public List<ProfesorDisponibleModel> ProfesoresDisponibles { get; set; } = new();

    public AulaModel? AulaSeleccionada { get; set; }

    public AulaState()
    {
        CargarDemo();
    }

    public void SeleccionarAula(AulaModel aula)
    {
        AulaSeleccionada = aula;
    }

    // Método antiguo para que tus pantallas no fallen.
    // Ahora "texto" solo busca por código de aula o tipo de aula, NO por profesor.
    public IEnumerable<AulaModel> Filtrar(string periodo, string nivel, string grado, string estado, string texto)
    {
        return FiltrarAulas(periodo, nivel, grado, estado, texto);
    }


    // Método recomendado.
    public List<AulaModel> FiltrarAulas(string periodo, string nivel, string grado, string estado, string codigoAula)
    {
        return Aulas
            .Where(a =>
                (periodo == "Todos" || a.Periodo == periodo) &&
                (nivel == "Todos" || a.EsCompartida || a.Nivel == nivel) &&
                (grado == "Todos" || a.EsCompartida || a.Grado == grado) &&
                (estado == "Todos" || a.Estado == estado) &&
                (string.IsNullOrWhiteSpace(codigoAula) ||
                 a.Codigo.Contains(codigoAula, StringComparison.OrdinalIgnoreCase) ||
                 a.TipoAula.Contains(codigoAula, StringComparison.OrdinalIgnoreCase)))
            .OrderBy(a => a.Periodo)
            .ThenBy(a => a.EsCompartida)
            .ThenBy(a => a.Nivel)
            .ThenBy(a => a.Grado)
            .ThenBy(a => a.Codigo)
            .ToList();
    }

    public IEnumerable<AulaModel> AulasUsadasPorGrado(string periodo, string nivel, string grado)
    {
        return Aulas.Where(a =>
            a.Periodo == periodo &&
            a.Estado == "Activa" &&
            (a.EsCompartida || (a.Nivel == nivel && a.Grado == grado)));
    }

    // Método antiguo por periodo + código.
    public IEnumerable<ProfesorAulaModel> ProfesoresPorAula(string periodo, string codigoAula)
    {
        return Profesores
            .Where(p =>
                p.Periodo == periodo &&
                p.CodigoAula == codigoAula &&
                p.Estado == "Activo")
            .OrderByDescending(p => p.EsTutor)
            .ThenBy(p => p.NombreCompleto);
    }

    // Método recomendado por Id de aula.
    public List<ProfesorAulaModel> ObtenerProfesoresPorAula(int aulaId)
    {
        return Profesores
            .Where(p => p.AulaId == aulaId && p.Estado == "Activo")
            .OrderByDescending(p => p.EsTutor)
            .ThenBy(p => p.NombreCompleto)
            .ToList();
    }

    public IEnumerable<ProfesorAulaModel> ProfesoresPorGrado(string periodo, string nivel, string grado)
    {
        var aulas = AulasUsadasPorGrado(periodo, nivel, grado)
            .Select(a => a.Id)
            .ToHashSet();

        return Profesores
            .Where(p =>
                p.Periodo == periodo &&
                aulas.Contains(p.AulaId) &&
                p.Estado == "Activo")
            .OrderByDescending(p => p.EsTutor)
            .ThenBy(p => p.NombreCompleto);
    }

    public List<ProfesorDisponibleModel> BuscarProfesoresDisponibles(string criterio)
    {
        if (string.IsNullOrWhiteSpace(criterio))
        {
            return new List<ProfesorDisponibleModel>();
        }

        return ProfesoresDisponibles
            .Where(p =>
                p.Dni.Contains(criterio, StringComparison.OrdinalIgnoreCase) ||
                p.NombreCompleto.Contains(criterio, StringComparison.OrdinalIgnoreCase) ||
                p.Especialidad.Contains(criterio, StringComparison.OrdinalIgnoreCase))
            .OrderBy(p => p.NombreCompleto)
            .ToList();
    }

    // Método antiguo.
    public bool ExisteCodigo(string codigo, int idIgnorar = 0)
    {
        return ExisteCodigoAula(codigo, idIgnorar);
    }

    // Método recomendado.
    public bool ExisteCodigoAula(string codigo, int idIgnorar = 0)
    {
        return Aulas.Any(a =>
            a.Id != idIgnorar &&
            a.Codigo.Equals(codigo, StringComparison.OrdinalIgnoreCase));
    }

    public void GuardarAula(AulaModel aula)
    {
        aula.Codigo = aula.Codigo.Trim();

        if (EsLaboratorio(aula.TipoAula))
        {
            aula.EsCompartida = true;
            aula.Nivel = "Todos";
            aula.Grado = "Todos";
        }
        else
        {
            aula.EsCompartida = false;
        }

        aula.Capacidad = Math.Clamp(aula.Capacidad, 1, 20);

        if (aula.VacantesOcupadas > aula.Capacidad)
        {
            aula.VacantesOcupadas = aula.Capacidad;
        }

        var actual = Aulas.FirstOrDefault(a => a.Id == aula.Id);

        if (actual is null)
        {
            aula.Id = Aulas.Count == 0 ? 1 : Aulas.Max(a => a.Id) + 1;
            Aulas.Add(aula);
            return;
        }

        actual.Periodo = aula.Periodo;
        actual.Nivel = aula.Nivel;
        actual.Grado = aula.Grado;
        actual.Codigo = aula.Codigo;
        actual.TipoAula = aula.TipoAula;
        actual.Capacidad = aula.Capacidad;
        actual.VacantesOcupadas = Math.Min(actual.VacantesOcupadas, aula.Capacidad);
        actual.Estado = aula.Estado;
        actual.EsCompartida = aula.EsCompartida;
    }

    public bool CambiarCapacidad(int aulaId, int nuevaCapacidad)
    {
        var aula = Aulas.FirstOrDefault(a => a.Id == aulaId);

        if (aula is null)
        {
            return false;
        }

        if (nuevaCapacidad <= 0 || nuevaCapacidad > 20)
        {
            return false;
        }

        if (nuevaCapacidad < aula.VacantesOcupadas)
        {
            return false;
        }

        aula.Capacidad = nuevaCapacidad;

        return true;
    }


    public void EliminarAula(int id)
    {
        var aula = Aulas.FirstOrDefault(a => a.Id == id);

        if (aula is null)
        {
            return;
        }

        Aulas.Remove(aula);
        Profesores.RemoveAll(p => p.AulaId == aula.Id);
    }

    // Método antiguo por periodo + código.
    public void AsignarProfesor(string periodo, string codigoAula, ProfesorAulaModel profesor)
    {
        var aula = Aulas.FirstOrDefault(a =>
            a.Periodo == periodo &&
            a.Codigo == codigoAula);

        if (aula is null)
        {
            return;
        }

        profesor.AulaId = aula.Id;
        profesor.Periodo = periodo;
        profesor.CodigoAula = codigoAula;

        AsignarProfesorInterno(profesor);
    }

    // Método recomendado por Id de aula.
    public void AsignarProfesor(int aulaId, ProfesorDisponibleModel profesor, bool esTutor, string observacion)
    {
        var aula = Aulas.FirstOrDefault(a => a.Id == aulaId);

        if (aula is null)
        {
            return;
        }

        var nuevo = new ProfesorAulaModel
        {
            AulaId = aula.Id,
            Periodo = aula.Periodo,
            CodigoAula = aula.Codigo,
            Dni = profesor.Dni,
            NombreCompleto = profesor.NombreCompleto,
            Especialidad = profesor.Especialidad,
            Cargo = profesor.Cargo,
            Telefono = profesor.Telefono,
            Correo = profesor.Correo,
            EsTutor = esTutor,
            Observacion = string.IsNullOrWhiteSpace(observacion) ? "—" : observacion,
            Estado = "Activo"
        };

        AsignarProfesorInterno(nuevo);
    }

    private void AsignarProfesorInterno(ProfesorAulaModel profesor)
    {
        if (Profesores.Any(p =>
            p.AulaId == profesor.AulaId &&
            p.Dni == profesor.Dni &&
            p.Estado == "Activo"))
        {
            return;
        }

        profesor.Id = Profesores.Count == 0 ? 1 : Profesores.Max(p => p.Id) + 1;
        profesor.Estado = "Activo";

        if (profesor.EsTutor)
        {
            foreach (var p in Profesores.Where(p => p.AulaId == profesor.AulaId))
            {
                p.EsTutor = false;
            }
        }

        Profesores.Add(profesor);

        ActualizarEncargadoAula(profesor.AulaId);
    }

    public void EditarAsignacion(int id, bool esTutor, string observacion)
    {
        var profesor = Profesores.FirstOrDefault(p => p.Id == id);

        if (profesor is null)
        {
            return;
        }

        if (esTutor)
        {
            foreach (var p in Profesores.Where(p => p.AulaId == profesor.AulaId))
            {
                p.EsTutor = false;
            }
        }

        profesor.EsTutor = esTutor;
        profesor.Observacion = string.IsNullOrWhiteSpace(observacion) ? "—" : observacion;

        ActualizarEncargadoAula(profesor.AulaId);
    }

    public void QuitarAsignacion(int id)
    {
        var profesor = Profesores.FirstOrDefault(p => p.Id == id);

        if (profesor is null)
        {
            return;
        }

        int aulaId = profesor.AulaId;

        Profesores.Remove(profesor);

        ActualizarEncargadoAula(aulaId);
    }

    private void ActualizarEncargadoAula(int aulaId)
    {
        var aula = Aulas.FirstOrDefault(a => a.Id == aulaId);

        if (aula is null)
        {
            return;
        }

        var tutor = Profesores
            .Where(p => p.AulaId == aulaId && p.Estado == "Activo")
            .OrderByDescending(p => p.EsTutor)
            .ThenBy(p => p.NombreCompleto)
            .FirstOrDefault();

        aula.ProfesorEncargado = tutor?.NombreCompleto ?? "Sin asignar";
    }

    private bool EsLaboratorio(string tipoAula)
    {
        return tipoAula == "Laboratorio de Cómputo" ||
               tipoAula == "Laboratorio de Ciencias";
    }

    private void CargarDemo()
    {
        Aulas = new()
        {
            new()
            {
                Id = 1,
                Periodo = "2026",
                Nivel = "Primaria",
                Grado = "1ro",
                Codigo = "P-101",
                TipoAula = "Aula regular",
                Capacidad = 15,
                VacantesOcupadas = 12,
                ProfesorEncargado = "Ana Torres",
                Estado = "Activa",
                EsCompartida = false
            },
            new()
            {
                Id = 2,
                Periodo = "2026",
                Nivel = "Primaria",
                Grado = "2do",
                Codigo = "P-102",
                TipoAula = "Aula regular",
                Capacidad = 15,
                VacantesOcupadas = 13,
                ProfesorEncargado = "José Ramírez",
                Estado = "Activa",
                EsCompartida = false
            },
            new()
            {
                Id = 3,
                Periodo = "2026",
                Nivel = "Primaria",
                Grado = "5to",
                Codigo = "P-204",
                TipoAula = "Aula regular",
                Capacidad = 15,
                VacantesOcupadas = 12,
                ProfesorEncargado = "María Elena Rojas Torres",
                Estado = "Activa",
                EsCompartida = false
            },
            new()
            {
                Id = 4,
                Periodo = "2026",
                Nivel = "Primaria",
                Grado = "5to",
                Codigo = "P-205",
                TipoAula = "Aula regular",
                Capacidad = 15,
                VacantesOcupadas = 14,
                ProfesorEncargado = "Carlos Ramírez",
                Estado = "Activa",
                EsCompartida = false
            },
            new()
            {
                Id = 5,
                Periodo = "2026",
                Nivel = "Secundaria",
                Grado = "2do",
                Codigo = "S-201",
                TipoAula = "Aula regular",
                Capacidad = 15,
                VacantesOcupadas = 0,
                ProfesorEncargado = "Sin asignar",
                Estado = "Inactiva",
                EsCompartida = false
            },
            new()
            {
                Id = 6,
                Periodo = "2026",
                Nivel = "Todos",
                Grado = "Todos",
                Codigo = "LAB-COM-01",
                TipoAula = "Laboratorio de Cómputo",
                Capacidad = 20,
                VacantesOcupadas = 0,
                ProfesorEncargado = "César Augusto Salas Quispe",
                Estado = "Activa",
                EsCompartida = true
            },
            new()
            {
                Id = 7,
                Periodo = "2026",
                Nivel = "Todos",
                Grado = "Todos",
                Codigo = "LAB-CIEN-01",
                TipoAula = "Laboratorio de Ciencias",
                Capacidad = 20,
                VacantesOcupadas = 0,
                ProfesorEncargado = "María Isabel Quispe",
                Estado = "Activa",
                EsCompartida = true
            }
        };

        Profesores = new()
        {
            new()
            {
                Id = 1,
                AulaId = 3,
                Periodo = "2026",
                CodigoAula = "P-204",
                Dni = "40678812",
                NombreCompleto = "María Elena Rojas Torres",
                Especialidad = "Matemática",
                Cargo = "Docente",
                Telefono = "987 654 321",
                Correo = "mrojas@sime.edu.pe",
                EsTutor = true,
                Observacion = "Tutora del aula",
                Estado = "Activo"
            },
            new()
            {
                Id = 2,
                AulaId = 3,
                Periodo = "2026",
                CodigoAula = "P-204",
                Dni = "43215678",
                NombreCompleto = "Carlos Alberto Ramírez Solís",
                Especialidad = "Computación e Informática",
                Cargo = "Docente",
                Telefono = "976 543 210",
                Correo = "cramirez@sime.edu.pe",
                EsTutor = false,
                Observacion = "Enseña computación",
                Estado = "Activo"
            },
            new()
            {
                Id = 3,
                AulaId = 3,
                Periodo = "2026",
                CodigoAula = "P-204",
                Dni = "70321987",
                NombreCompleto = "Lucía Fernanda Vargas Díaz",
                Especialidad = "Comunicación",
                Cargo = "Docente",
                Telefono = "998 765 432",
                Correo = "lvargas@sime.edu.pe",
                EsTutor = false,
                Observacion = "Enseña comunicación",
                Estado = "Activo"
            },
            new()
            {
                Id = 4,
                AulaId = 6,
                Periodo = "2026",
                CodigoAula = "LAB-COM-01",
                Dni = "43321987",
                NombreCompleto = "César Augusto Salas Quispe",
                Especialidad = "Computación e Informática",
                Cargo = "Docente",
                Telefono = "912 345 678",
                Correo = "csalas@sime.edu.pe",
                EsTutor = false,
                Observacion = "Encargado de laboratorio de cómputo",
                Estado = "Activo"
            }
        };

        ProfesoresDisponibles = new()
        {
            new()
            {
                Dni = "41567890",
                NombreCompleto = "José Luis Ramírez Vega",
                Especialidad = "Comunicación",
                Cargo = "Docente",
                Telefono = "987 111 222",
                Correo = "jramirez@sime.edu.pe"
            },
            new()
            {
                Dni = "43321987",
                NombreCompleto = "César Augusto Salas Quispe",
                Especialidad = "Computación e Informática",
                Cargo = "Docente",
                Telefono = "987 333 444",
                Correo = "csalas@sime.edu.pe"
            },
            new()
            {
                Dni = "40876543",
                NombreCompleto = "Rosa Elena Martínez Cruz",
                Especialidad = "Matemática",
                Cargo = "Docente",
                Telefono = "987 555 666",
                Correo = "rmartinez@sime.edu.pe"
            },
            new()
            {
                Dni = "42211234",
                NombreCompleto = "Miguel Ángel Paredes Ríos",
                Especialidad = "Ciencia y Tecnología",
                Cargo = "Docente",
                Telefono = "987 777 888",
                Correo = "mparedes@sime.edu.pe"
            },
            new()
            {
                Dni = "42990123",
                NombreCompleto = "Claudia Patricia Silva Fernández",
                Especialidad = "Comunicación",
                Cargo = "Docente",
                Telefono = "987 999 000",
                Correo = "csilva@sime.edu.pe"
            }
        };
    }
}

public class AulaModel
{
    public int Id { get; set; }

    public string Periodo { get; set; } = "";
    public string Nivel { get; set; } = "";
    public string Grado { get; set; } = "";

    public string Codigo { get; set; } = "";
    public string TipoAula { get; set; } = "";

    public int Capacidad { get; set; } = 15;
    public int VacantesOcupadas { get; set; }

    public int VacantesDisponibles => Capacidad - VacantesOcupadas;

    // Compatibilidad con pantallas anteriores que todavía usen VacantesTotales.
    public int VacantesTotales
    {
        get => Capacidad;
        set => Capacidad = value;
    }

    public string ProfesorEncargado { get; set; } = "Sin asignar";

    public string Estado { get; set; } = "Activa";

    public bool EsCompartida { get; set; }
}

public class ProfesorAulaModel
{
    public int Id { get; set; }

    public int AulaId { get; set; }

    public string Periodo { get; set; } = "";
    public string CodigoAula { get; set; } = "";

    public string Dni { get; set; } = "";
    public string NombreCompleto { get; set; } = "";
    public string Especialidad { get; set; } = "";
    public string Cargo { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";

    public bool EsTutor { get; set; }

    public string Observacion { get; set; } = "";

    public string Estado { get; set; } = "Activo";
}

public class ProfesorDisponibleModel
{
    public string Dni { get; set; } = "";
    public string NombreCompleto { get; set; } = "";
    public string Especialidad { get; set; } = "";
    public string Cargo { get; set; } = "";
    public string Telefono { get; set; } = "";
    public string Correo { get; set; } = "";
}