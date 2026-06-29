package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IRelacionFamiliarBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.RelacionFamiliarDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.ApoderadoNuevoDTO;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class RelacionFamiliarBLImpl implements IRelacionFamiliarBL {

    private RelacionFamiliarDAO rf_dao = new RelacionFamiliarDAOImpl();
    private PersonaDAO personaDAO = new PersonaDAOImpl();
    private AlumnoBLImpl alumnoBL = new AlumnoBLImpl();

    @Override
    public List<RelacionFamiliar> listarApoderados(Integer idAlumno) throws Exception {
        try {
            TransactionContext.getConnection();
            List<RelacionFamiliar> ap = rf_dao.listarApoderadosActivos(idAlumno);
            List<RelacionFamiliar> apoderados = new ArrayList<>();
            for (RelacionFamiliar rf : ap) {
                int id  = rf.getIdRelacionFamiliar();
                rf = rf_dao.load(id);
                apoderados.add(rf);
            }
            TransactionContext.commit();
            return apoderados;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al listar apoderados: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public boolean existePersonaPorDni(String dni) throws Exception {
        try {
            TransactionContext.getConnection();

            Persona persona = personaDAO.buscarPorDni(dni);

            TransactionContext.commit();

            return persona != null;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al verificar apoderado por DNI: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public int agregarApoderado(String dniAlumno, ApoderadoNuevoDTO apoderado) throws Exception {
        // Se resuelve el alumno por DNI fuera de la transacción de escritura
        // (buscarAlumnoPorDNI gestiona su propia conexión).
        Alumno alumno = alumnoBL.buscarAlumnoPorDNI(dniAlumno);
        if (alumno == null) {
            throw new Exception("No se encontró el alumno con DNI " + dniAlumno);
        }

        try {
            TransactionContext.getConnection();

            int existentes = rf_dao.contarApoderadosActivos(alumno.getIdAlumno());
            if (existentes >= 3) {
                throw new Exception("El alumno ya tiene el máximo de 3 apoderados registrados.");
            }

            int idRelacion = rf_dao.insertarApoderadoAlumno(alumno.getIdAlumno(), apoderado);

            TransactionContext.commit();
            return idRelacion;
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al agregar apoderado: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public void editarApoderado(int idRelacionFamiliar, ApoderadoNuevoDTO apoderado) throws Exception {
        try {
            TransactionContext.getConnection();
            rf_dao.actualizarApoderado(idRelacionFamiliar, apoderado);
            TransactionContext.commit();
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al editar apoderado: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public void eliminarApoderado(int idRelacionFamiliar) throws Exception {
        try {
            TransactionContext.getConnection();

            RelacionFamiliar relacion = rf_dao.load(idRelacionFamiliar);
            if (relacion == null) {
                throw new Exception("No existe el apoderado a eliminar (id=" + idRelacionFamiliar + ").");
            }

            rf_dao.remove(relacion);

            TransactionContext.commit();
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al eliminar apoderado: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

}
