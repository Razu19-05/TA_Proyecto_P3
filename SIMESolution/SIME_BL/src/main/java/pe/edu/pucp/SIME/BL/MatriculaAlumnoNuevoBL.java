package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IMatriculaAlumnoNuevoBL;
import pe.edu.pucp.SIME.BL.servicios.ApoderadoMatriculaAlumnoNuevoBL;
import pe.edu.pucp.SIME.BL.servicios.PagoMatriculaAlumnoNuevoBL;
import pe.edu.pucp.SIME.BL.validaciones.ValidacionMatriculaAlumnoNuevoBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaDetalleDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.AlumnoDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaCabeceraDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaDetalleDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoRequestDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoResponseDTO;
import pe.edu.pucp.SIME.model.DTO.PagoMatriculaDTO;
import pe.edu.pucp.SIME.model.DTO.VacanteMatriculaDTO;

import java.util.List;

public class MatriculaAlumnoNuevoBL implements IMatriculaAlumnoNuevoBL {

    private final AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
    private final MatriculaCabeceraDAO matriculaCabeceraDAO = new MatriculaCabeceraDAOImpl();
    private final MatriculaDetalleDAO matriculaDetalleDAO = new MatriculaDetalleDAOImpl();

    private final ValidacionMatriculaAlumnoNuevoBL validacion =
            new ValidacionMatriculaAlumnoNuevoBL();

    private final PagoMatriculaAlumnoNuevoBL pagoMatriculaAlumnoNuevoBL =
            new PagoMatriculaAlumnoNuevoBL();

    private final ApoderadoMatriculaAlumnoNuevoBL apoderadoMatriculaAlumnoNuevoBL =
            new ApoderadoMatriculaAlumnoNuevoBL();

    @Override
    public MatriculaAlumnoNuevoResponseDTO matricularAlumnoNuevo(
            MatriculaAlumnoNuevoRequestDTO request
    ) throws Exception {

        try {
            TransactionContext.getConnection();

            validacion.validarRequest(request);

            if (alumnoDAO.existeAlumnoPorDni(request.getAlumno().getDni())) {
                throw new Exception("Ya existe un alumno registrado con ese DNI.");
            }

            if (!matriculaCabeceraDAO.existeVacanteDisponible(request.getIdMatriculaCabecera())) {
                throw new Exception("No existen vacantes disponibles para el aula seleccionada.");
            }

            int idAlumno = alumnoDAO.insertarAlumnoNuevo(request.getAlumno());

            if (idAlumno <= 0) {
                throw new Exception("No se pudo registrar el alumno.");
            }

            apoderadoMatriculaAlumnoNuevoBL.registrarApoderadosAlumnoNuevo(
                    idAlumno,
                    request.getApoderados()
            );

            if (matriculaDetalleDAO.existeMatriculaActiva(idAlumno, request.getIdMatriculaCabecera())) {
                throw new Exception("El alumno ya tiene matrícula activa en esta cabecera.");
            }

            int idMatriculaDetalle = matriculaDetalleDAO.insertarMatriculaAlumnoNuevo(
                    idAlumno,
                    request.getIdMatriculaCabecera()
            );

            if (idMatriculaDetalle <= 0) {
                throw new Exception("No se pudo registrar la matrícula.");
            }

            boolean vacanteActualizada =
                    matriculaCabeceraDAO.incrementarVacanteOcupada(request.getIdMatriculaCabecera());

            if (!vacanteActualizada) {
                throw new Exception("No se pudo actualizar la vacante. Es posible que ya no haya disponibilidad.");
            }

            List<PagoMatriculaDTO> pagos =
                    pagoMatriculaAlumnoNuevoBL.registrarPagosAlumnoNuevo(
                            idAlumno,
                            idMatriculaDetalle,
                            request
                    );

            MatriculaAlumnoNuevoResponseDTO response = new MatriculaAlumnoNuevoResponseDTO();
            response.setIdAlumno(idAlumno);
            response.setIdMatriculaDetalle(idMatriculaDetalle);
            response.setMensaje("Matrícula de alumno nuevo registrada correctamente.");
            response.setPagos(pagos);

            TransactionContext.commit();
            return response;

        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al matricular alumno nuevo: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public List<VacanteMatriculaDTO> listarVacantes(
            String periodo,
            String nivel,
            String grado
    ) throws Exception {
        try {
            TransactionContext.getConnection();

            validacion.validarFiltroVacantes(periodo, nivel, grado);

            List<VacanteMatriculaDTO> vacantes =
                    matriculaCabeceraDAO.listarVacantes(periodo, nivel, grado);

            TransactionContext.commit();

            return vacantes;

        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al listar vacantes: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }
}