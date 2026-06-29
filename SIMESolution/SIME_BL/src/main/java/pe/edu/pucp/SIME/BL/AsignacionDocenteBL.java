


package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IAsignacionDocenteBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AsignacionDocenteDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.AsignacionDocenteDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.AsignacionDocenteRequestDTO;
import pe.edu.pucp.SIME.model.DTO.ProfesorAulaDTO;

import java.util.List;

public class AsignacionDocenteBL implements IAsignacionDocenteBL {

    private final AsignacionDocenteDAO asignacionDocenteDAO = new AsignacionDocenteDAOImpl();

    @Override
    public List<ProfesorAulaDTO> listarPorAula(int idAula) throws Exception {
        try {
            TransactionContext.getConnection();

            List<ProfesorAulaDTO> lista = asignacionDocenteDAO.listarPorAula(idAula);

            TransactionContext.commit();
            return lista;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al listar profesores del aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public List<ProfesorAulaDTO> listarPorMatriculaCabecera(int idMatriculaCabecera) throws Exception {
        try {
            TransactionContext.getConnection();

            List<ProfesorAulaDTO> lista =
                    asignacionDocenteDAO.listarPorMatriculaCabecera(idMatriculaCabecera);

            TransactionContext.commit();
            return lista;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al listar profesores por matrícula cabecera: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public List<ProfesorAulaDTO> listarProfesoresDisponibles(String criterio) throws Exception {
        try {
            TransactionContext.getConnection();

            List<ProfesorAulaDTO> lista =
                    asignacionDocenteDAO.listarProfesoresDisponibles(criterio);

            TransactionContext.commit();
            return lista;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al listar profesores disponibles: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public int asignarDocente(AsignacionDocenteRequestDTO request) throws Exception {
        try {
            TransactionContext.getConnection();

            completarMatriculaCabeceraSiEsNecesario(request);
            validarRequest(request, 0);

            if (asignacionDocenteDAO.existeAsignacionActiva(
                    request.getIdPersona(),
                    request.getIdMatriculaCabecera(),
                    0)) {
                throw new Exception("El profesor ya está asignado a esta aula.");
            }

            if (request.isEsTutor() &&
                    asignacionDocenteDAO.existeTutorActivo(request.getIdMatriculaCabecera(), 0)) {
                throw new Exception("Esta aula ya tiene un tutor asignado.");
            }

            int idGenerado = asignacionDocenteDAO.asignarDocente(request);

            TransactionContext.commit();
            return idGenerado;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al asignar docente: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public boolean actualizarAsignacion(int idAsignacionDocente, AsignacionDocenteRequestDTO request) throws Exception {
        try {
            TransactionContext.getConnection();

            completarMatriculaCabeceraSiEsNecesario(request);
            validarRequest(request, idAsignacionDocente);

            if (asignacionDocenteDAO.existeAsignacionActiva(
                    request.getIdPersona(),
                    request.getIdMatriculaCabecera(),
                    idAsignacionDocente)) {
                throw new Exception("El profesor ya está asignado a esta aula.");
            }

            if (request.isEsTutor() &&
                    asignacionDocenteDAO.existeTutorActivo(
                            request.getIdMatriculaCabecera(),
                            idAsignacionDocente)) {
                throw new Exception("Esta aula ya tiene otro tutor asignado.");
            }

            boolean ok = asignacionDocenteDAO.actualizarAsignacion(idAsignacionDocente, request);

            TransactionContext.commit();
            return ok;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al actualizar asignación docente: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public boolean eliminarAsignacion(int idAsignacionDocente) throws Exception {
        try {
            TransactionContext.getConnection();

            boolean ok = asignacionDocenteDAO.eliminarAsignacion(idAsignacionDocente);

            TransactionContext.commit();
            return ok;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al eliminar asignación docente: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    private void completarMatriculaCabeceraSiEsNecesario(AsignacionDocenteRequestDTO request) throws Exception {
        if (request.getIdMatriculaCabecera() != null && request.getIdMatriculaCabecera() > 0) {
            return;
        }

        if (request.getIdAula() == null || request.getIdAula() <= 0) {
            throw new Exception("Debe enviar el idAula o el idMatriculaCabecera.");
        }

        int idMatriculaCabecera =
                asignacionDocenteDAO.obtenerMatriculaCabeceraActivaPorAula(request.getIdAula());

        if (idMatriculaCabecera <= 0) {
            throw new Exception("No existe una matrícula cabecera activa para esta aula.");
        }

        request.setIdMatriculaCabecera(idMatriculaCabecera);
    }

    private void validarRequest(AsignacionDocenteRequestDTO request, int idIgnorar) throws Exception {
        if (request.getIdPersona() <= 0) {
            throw new Exception("Debe seleccionar un profesor.");
        }

        if (request.getIdMatriculaCabecera() == null || request.getIdMatriculaCabecera() <= 0) {
            throw new Exception("Debe indicar la matrícula cabecera.");
        }

        if (request.getObservacion() != null && request.getObservacion().length() > 100) {
            throw new Exception("La observación no debe superar los 100 caracteres.");
        }
    }
}
