package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IAlumnoBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.AlumnoDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

public class AlumnoBLImpl implements IAlumnoBL {
    AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
    @Override
    public Alumno buscarAlumnoPorDNI(String DNI) throws Exception{
        return alumnoDAO.buscarPorDni(DNI);
    }
    @Override
    public Alumno actualizar(Alumno alumno) throws Exception{
        try{
            TransactionContext.getConnection();
            Alumno alumnoact = alumnoDAO.update(alumno);
            TransactionContext.commit();
            return alumnoact;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al actualizar alumno");
        } finally{
            TransactionContext.close();
        }

    }

    @Override
    public Alumno insertar(Alumno alumno) throws Exception {
        try{
            TransactionContext.getConnection();
            Alumno a = alumnoDAO.save(alumno);
            TransactionContext.commit();
            return a;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al insertar alumno");
        } finally{
            TransactionContext.close();
        }
    }
}
