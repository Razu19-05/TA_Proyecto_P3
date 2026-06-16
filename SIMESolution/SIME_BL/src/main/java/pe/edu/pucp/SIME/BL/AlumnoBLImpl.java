package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IAlumnoBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.AlumnoDAOImpl;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

public class AlumnoBLImpl implements IAlumnoBL {
    AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
    @Override
    public Alumno buscarAlumnoPorDNI(String DNI) throws Exception{
        return alumnoDAO.buscarPorDni(DNI);
    }
    @Override
    public Alumno actualizar(Alumno alumno) throws Exception{
        return alumnoDAO.update(alumno);
    }
}
