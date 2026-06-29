namespace SimeSoft.Services.Matriculas;

public class ValidacionMatriculaAlumnoNuevoService
{
    public DatosAlumnoNuevoValidationResult ValidarDatosAlumnoNuevo(
        string dni,
        string nombres,
        string apellidoPaterno,
        string apellidoMaterno,
        DateTime? fechaNacimiento,
        string telefono,
        string correo,
        string direccion)
    {
        DatosAlumnoNuevoValidationResult resultado = new();

        string dniLimpio = dni.Trim();
        string telefonoLimpio = telefono.Trim();

        if (string.IsNullOrWhiteSpace(dniLimpio))
        {
            resultado.ErrorDni = "El DNI es obligatorio.";
        }
        else if (!EsDniValido(dniLimpio))
        {
            resultado.ErrorDni = "El DNI debe tener exactamente 8 dígitos numéricos.";
        }

        if (string.IsNullOrWhiteSpace(nombres))
        {
            resultado.ErrorNombres = "Los nombres son obligatorios.";
        }

        if (string.IsNullOrWhiteSpace(apellidoPaterno))
        {
            resultado.ErrorApellidoPaterno = "El apellido paterno es obligatorio.";
        }

        if (string.IsNullOrWhiteSpace(apellidoMaterno))
        {
            resultado.ErrorApellidoMaterno = "El apellido materno es obligatorio.";
        }

        if (fechaNacimiento is null)
        {
            resultado.ErrorFecha = "La fecha de nacimiento es obligatoria.";
        }

        if (string.IsNullOrWhiteSpace(telefonoLimpio))
        {
            resultado.ErrorTelefono = "El teléfono es obligatorio.";
        }
        else if (!EsTelefonoCelularValido(telefonoLimpio))
        {
            resultado.ErrorTelefono = "El teléfono debe tener 9 dígitos, empezar con 9 y contener solo números.";
        }

        if (string.IsNullOrWhiteSpace(correo))
        {
            resultado.ErrorCorreo = "El correo electrónico es obligatorio.";
        }

        if (string.IsNullOrWhiteSpace(direccion))
        {
            resultado.ErrorDireccion = "La dirección es obligatoria.";
        }

        resultado.EsValido =
            string.IsNullOrWhiteSpace(resultado.ErrorDni) &&
            string.IsNullOrWhiteSpace(resultado.ErrorNombres) &&
            string.IsNullOrWhiteSpace(resultado.ErrorApellidoPaterno) &&
            string.IsNullOrWhiteSpace(resultado.ErrorApellidoMaterno) &&
            string.IsNullOrWhiteSpace(resultado.ErrorFecha) &&
            string.IsNullOrWhiteSpace(resultado.ErrorTelefono) &&
            string.IsNullOrWhiteSpace(resultado.ErrorCorreo) &&
            string.IsNullOrWhiteSpace(resultado.ErrorDireccion);

        if (!resultado.EsValido)
        {
            resultado.MensajeGeneral = "Complete correctamente los datos personales antes de continuar.";
        }

        return resultado;
    }

    public MatriculaValidationResult ValidarApoderado(
        string dni,
        string nombres,
        string apellidoPaterno,
        string apellidoMaterno,
        string parentesco,
        string telefono,
        string correo,
        string direccion,
        string tipo,
        string especialidad,
        string cargo,
        string area)
    {
        if (string.IsNullOrWhiteSpace(dni) ||
            string.IsNullOrWhiteSpace(nombres) ||
            string.IsNullOrWhiteSpace(apellidoPaterno) ||
            string.IsNullOrWhiteSpace(apellidoMaterno) ||
            string.IsNullOrWhiteSpace(parentesco) ||
            string.IsNullOrWhiteSpace(telefono) ||
            string.IsNullOrWhiteSpace(correo) ||
            string.IsNullOrWhiteSpace(direccion))
        {
            return MatriculaValidationResult.Error("Complete todos los campos obligatorios del apoderado.");
        }

        if (!EsDniValido(dni.Trim()))
        {
            return MatriculaValidationResult.Error("El DNI del apoderado debe tener exactamente 8 dígitos numéricos.");
        }

        if (!EsTelefonoCelularValido(telefono.Trim()))
        {
            return MatriculaValidationResult.Error("El teléfono del apoderado debe tener 9 dígitos, empezar con 9 y contener solo números.");
        }

        if (tipo == "PROFESOR" && string.IsNullOrWhiteSpace(especialidad))
        {
            return MatriculaValidationResult.Error("Si el apoderado es profesor, debe ingresar la especialidad.");
        }

        if (tipo == "ADMINISTRADOR" && string.IsNullOrWhiteSpace(cargo))
        {
            return MatriculaValidationResult.Error("Si el apoderado es administrador, debe ingresar el cargo.");
        }

        if (tipo == "PERSONAL_SERVICIO" && string.IsNullOrWhiteSpace(area))
        {
            return MatriculaValidationResult.Error("Si el apoderado es personal de servicio, debe ingresar el área.");
        }

        return MatriculaValidationResult.Ok();
    }

    public MatriculaValidationResult ValidarPasoApoderados(
        int cantidadApoderados,
        bool tieneContactoEmergencia)
    {
        if (cantidadApoderados <= 0)
        {
            return MatriculaValidationResult.Error("Debe registrar al menos un apoderado.");
        }

        if (!tieneContactoEmergencia)
        {
            return MatriculaValidationResult.Error("Debe registrar al menos un apoderado y marcar un contacto de emergencia.");
        }

        return MatriculaValidationResult.Ok();
    }

    public MatriculaValidationResult ValidarDescuento(
        string tipoDescuento,
        string motivoDescuento,
        decimal porcentajeDescuento)
    {
        if (tipoDescuento == "NINGUNO")
        {
            return MatriculaValidationResult.Ok();
        }

        if (string.IsNullOrWhiteSpace(motivoDescuento))
        {
            return MatriculaValidationResult.Error("Debe seleccionar el motivo del descuento.");
        }

        if (porcentajeDescuento <= 0)
        {
            return MatriculaValidationResult.Error("No se pudo determinar el porcentaje del descuento seleccionado.");
        }

        return MatriculaValidationResult.Ok();
    }

    private bool EsDniValido(string dni)
    {
        return dni.Length == 8 && dni.All(char.IsDigit);
    }

    private bool EsTelefonoCelularValido(string telefono)
    {
        return telefono.Length == 9 &&
               telefono.All(char.IsDigit) &&
               telefono.StartsWith("9");
    }
}

public class DatosAlumnoNuevoValidationResult
{
    public bool EsValido { get; set; }
    public string MensajeGeneral { get; set; } = "";

    public string ErrorDni { get; set; } = "";
    public string ErrorNombres { get; set; } = "";
    public string ErrorApellidoPaterno { get; set; } = "";
    public string ErrorApellidoMaterno { get; set; } = "";
    public string ErrorFecha { get; set; } = "";
    public string ErrorTelefono { get; set; } = "";
    public string ErrorCorreo { get; set; } = "";
    public string ErrorDireccion { get; set; } = "";
}

public class MatriculaValidationResult
{
    public bool EsValido { get; set; }
    public string Mensaje { get; set; } = "";

    public static MatriculaValidationResult Ok()
    {
        return new MatriculaValidationResult
        {
            EsValido = true,
            Mensaje = ""
        };
    }

    public static MatriculaValidationResult Error(string mensaje)
    {
        return new MatriculaValidationResult
        {
            EsValido = false,
            Mensaje = mensaje
        };
    }
}