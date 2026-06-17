package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IRelacionFamiliarBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.RelacionFamiliarDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.util.ArrayList;
import java.util.List;

public class RelacionFamiliarBLImpl implements IRelacionFamiliarBL {
    private RelacionFamiliarDAO rf_dao = new RelacionFamiliarDAOImpl();
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
}
