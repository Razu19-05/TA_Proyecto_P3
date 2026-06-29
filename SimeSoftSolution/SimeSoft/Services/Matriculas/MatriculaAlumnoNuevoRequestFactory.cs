using SimeSoft.Models.Matriculas;

namespace SimeSoft.Services.Matriculas;

public class MatriculaAlumnoNuevoRequestFactory
{
    public MatriculaAlumnoNuevoRequestDto CrearRequestAlumnoNuevo(
        string dni,
        string nombres,
        string apellidoPaterno,
        string apellidoMaterno,
        DateTime? fechaNacimiento,
        string direccion,
        string telefono,
        string correo,
        List<ApoderadoNuevoModel> apoderados,
        int idMatriculaCabecera,
        string tipoDescuento,
        string motivoDescuento,
        decimal porcentajeDescuento)
    {
        return new MatriculaAlumnoNuevoRequestDto
        {
            Alumno = CrearAlumnoRequest(
                dni,
                nombres,
                apellidoPaterno,
                apellidoMaterno,
                fechaNacimiento,
                direccion,
                telefono,
                correo
            ),

            Apoderados = CrearApoderadosRequest(apoderados),

            IdMatriculaCabecera = idMatriculaCabecera,
            TipoDescuento = tipoDescuento,
            MotivoDescuento = tipoDescuento == "NINGUNO" ? "" : motivoDescuento,
            PorcentajeDescuento = (double)porcentajeDescuento
        };
    }

    private AlumnoNuevoRequestDto CrearAlumnoRequest(
        string dni,
        string nombres,
        string apellidoPaterno,
        string apellidoMaterno,
        DateTime? fechaNacimiento,
        string direccion,
        string telefono,
        string correo)
    {
        return new AlumnoNuevoRequestDto
        {
            Dni = dni.Trim(),
            Nombres = nombres.Trim(),
            ApellidoPaterno = apellidoPaterno.Trim(),
            ApellidoMaterno = apellidoMaterno.Trim(),
            FechaNacimiento = fechaNacimiento?.ToString("yyyy-MM-dd") ?? "",
            Direccion = direccion.Trim(),
            Telefono = telefono.Trim(),
            Correo = correo.Trim()
        };
    }

    private List<ApoderadoNuevoRequestDto> CrearApoderadosRequest(
        List<ApoderadoNuevoModel> apoderados)
    {
        return apoderados.Select(a => new ApoderadoNuevoRequestDto
        {
            Dni = a.Dni.Trim(),
            Nombres = a.Nombres.Trim(),
            ApellidoPaterno = a.ApellidoPaterno.Trim(),
            ApellidoMaterno = a.ApellidoMaterno.Trim(),
            Telefono = a.Telefono.Trim(),
            Correo = a.Correo.Trim(),
            Direccion = a.Direccion.Trim(),
            Tipo = a.Tipo,
            Especialidad = a.Especialidad.Trim(),
            Cargo = a.Cargo.Trim(),
            Area = a.Area.Trim(),
            Parentesco = a.Parentesco,
            ContactoEmergencia = a.ContactoEmergencia,
            Observacion = a.Observaciones.Trim()
        }).ToList();
    }
}