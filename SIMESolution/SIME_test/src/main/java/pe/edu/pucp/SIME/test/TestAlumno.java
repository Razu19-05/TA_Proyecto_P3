package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.model.GradoSeccion;
import pe.edu.pucp.SIME.estudiante.DAO.AlumnoDAO;
import pe.edu.pucp.SIME.estudiante.impl.AlumnoDAOImpl;
import pe.edu.pucp.SIME.estudiante.model.Alumno;

import java.util.ArrayList;
import java.util.List;

public class TestAlumno {
    public static void main(String[] args) {
        AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
        List<Alumno> alumnos = alumnoDAO.listAll();
        Alumno alumno = alumnoDAO.load(2);
//        System.out.println(alumno.getNombres());
//
        Alumno newAlumno = new Alumno();
        newAlumno.setDNI("77420924");
        newAlumno.setNombres("Gabriel");
        newAlumno.setApellidoPaterno("AAAA");
        newAlumno.setApellidoMaterno("BBBB");
        GradoSeccion newGrado = new GradoSeccion();
        newGrado.setIdGrado(1);
        newAlumno.setGradoSeccion(newGrado);
        alumnoDAO.save(newAlumno);

//        System.out.println(newAlumno.getIdAlumno());
        for(Alumno alu : alumnos){
            int id = alu.getIdAlumno();
            System.out.println(id);
        }
    }
}
