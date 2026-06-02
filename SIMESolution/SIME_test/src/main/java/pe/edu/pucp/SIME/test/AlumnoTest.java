package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.AlumnoDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

import java.sql.SQLException;

public class AlumnoTest {
    public static void main(String [] args) throws SQLException{
        AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
        Alumno alumno = alumnoDAO.load(1);
        System.out.println(alumno.getNombres());
        TransactionContext.close();
    }
}
