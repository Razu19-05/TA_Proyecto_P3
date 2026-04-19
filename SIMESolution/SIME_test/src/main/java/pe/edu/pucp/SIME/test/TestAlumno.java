package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.estudiante.DAO.AlumnoDAO;
import pe.edu.pucp.SIME.estudiante.impl.AlumnoDAOImpl;
import pe.edu.pucp.SIME.estudiante.model.Alumno;

public class TestAlumno {
    public static void main(String[] args) {
        AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
        Alumno alumno = alumnoDAO.load(3);
        System.out.println(alumno.getNombres());

        Alumno newAlumno = new Alumno();
        newAlumno.setNombres("Gabriel");
        newAlumno.setApellidoPaterno("Huarote");
        newAlumno.setApellidoMaterno("Serrano");
        newAlumno.setDireccion("Chorrillos");
        newAlumno.setTelefono("995951026");
        newAlumno.setCorreo("a20231846@pucp.edu.pe");
        alumnoDAO.save(newAlumno);
    }
}
