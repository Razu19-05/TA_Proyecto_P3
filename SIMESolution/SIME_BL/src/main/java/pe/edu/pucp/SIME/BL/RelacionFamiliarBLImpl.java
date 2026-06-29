package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IRelacionFamiliarBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.RelacionFamiliarDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class RelacionFamiliarBLImpl implements IRelacionFamiliarBL {

    private RelacionFamiliarDAO rf_dao = new RelacionFamiliarDAOImpl();
    private PersonaDAO personaDAO = new PersonaDAOImpl();

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


}
