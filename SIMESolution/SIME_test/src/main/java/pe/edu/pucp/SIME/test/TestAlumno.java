package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.estudiante.DAO.AlumnoDAO;
import pe.edu.pucp.SIME.estudiante.impl.AlumnoDAOImpl;
import pe.edu.pucp.SIME.estudiante.model.Alumno;

import java.util.ArrayList;
import java.util.List;

public class TestAlumno {
    public static void main(String[] args) {
        AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
        List<Alumno> alumnos = alumnoDAO.listAll();
//        Alumno alumno = alumnoDAO.load(3);
//        System.out.println(alumno.getNombres());
//
        Alumno newAlumno = new Alumno();
        newAlumno.setNombres("Diego");
        newAlumno.setApellidoPaterno("AAAA");
        newAlumno.setApellidoMaterno("BBBB");
        newAlumno.setDireccion("C");
        newAlumno.setTelefono("999999");
        newAlumno.setDNI("72161621");
        newAlumno.setCorreo("a20221351@pucp.edu.pe");
        alumnoDAO.save(newAlumno);
        for(Alumno alu : alumnos){
            int id = alu.getIdAlumno();
            System.out.println(id);
        }
    }
}
