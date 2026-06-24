package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IAsignacionDocenteBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AsignacionDocenteDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.AsignacionDocenteDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.ProfesorDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.AsignacionDocente;

import java.util.List;


public class AsignacionDocenteBL implements IAsignacionDocenteBL {
    private AsignacionDocenteDAO asignacionDAO = new AsignacionDocenteDAOImpl();

    public List<ProfesorDTO> obtenerProfesoresPorAula(String nivel, String grado, int anio) throws Exception {
        try {
            TransactionContext.getConnection(); // Abre conexión
            List<ProfesorDTO> profesores = asignacionDAO.listarProfesoresPorAula(nivel, grado, anio);
            TransactionContext.commit();
            return profesores;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al cargar profesores: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }


    @Override
    public AsignacionDocente actualizarAsignacionDocente(AsignacionDocente asignacionDocente) throws Exception{
        try {
            TransactionContext.getConnection(); // Abre conexión
            AsignacionDocente asig = asignacionDAO.update(asignacionDocente);
            TransactionContext.commit();
            return asig;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al actualizar docente: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public void eliminarAsignacionDocente(AsignacionDocente asignacionDocente) throws Exception{
        try {
            TransactionContext.getConnection(); // Abre conexión
            asignacionDAO.remove(asignacionDocente);
            TransactionContext.commit();

        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al eliminar docente: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }
}
