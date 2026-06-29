package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IAulaBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AulaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.AulaDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.AulaDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;

import java.util.List;

public class AulaBL implements IAulaBL {

    private AulaDAO aulaDAO = new AulaDAOImpl();

    @Override
    public List<AulaDTO> listarAulas(String periodo, String nivel, String grado, String estado, String codigo) throws Exception {
        try {
            TransactionContext.getConnection();
            List<AulaDTO> lista = aulaDAO.listarAulas(periodo, nivel, grado, estado, codigo);
            TransactionContext.commit();
            return lista;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al listar aulas: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public AulaDTO obtenerDetalleAula(int idAula) throws Exception {
        try {
            TransactionContext.getConnection();
            AulaDTO aula = aulaDAO.obtenerDetalleAula(idAula);
            TransactionContext.commit();
            return aula;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al obtener detalle del aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Aula guardarAula(Aula aula) throws Exception {
        try {
            TransactionContext.getConnection();

            if (aulaDAO.existeCodigoAula(aula.getCodigo(), 0)) {
                throw new Exception("Ya existe un aula con ese código.");
            }

            Aula registrada = aulaDAO.save(aula);
            TransactionContext.commit();
            return registrada;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al guardar aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Aula actualizarAula(Aula aula) throws Exception {
        try {
            TransactionContext.getConnection();

            if (aulaDAO.existeCodigoAula(aula.getCodigo(), aula.getIdAula())) {
                throw new Exception("Ya existe un aula con ese código.");
            }

            Aula actualizada = aulaDAO.update(aula);
            TransactionContext.commit();
            return actualizada;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al actualizar aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public void eliminarAula(int idAula) throws Exception {
        try {
            TransactionContext.getConnection();

            Aula aula = new Aula();
            aula.setIdAula(idAula);

            aulaDAO.remove(aula);
            TransactionContext.commit();
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al eliminar aula: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public boolean actualizarCapacidad(int idAula, int capacidad) throws Exception {
        try {
            TransactionContext.getConnection();
            boolean ok = aulaDAO.actualizarCapacidad(idAula, capacidad);
            TransactionContext.commit();
            return ok;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al actualizar capacidad: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }
}