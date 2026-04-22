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
        //Prueba de Save
//        Alumno newAlumno = new Alumno();
//        newAlumno.setDNI("09823746");
//        newAlumno.setNombres("Gabriel");
//        newAlumno.setApellidoPaterno("Yauri");
//        newAlumno.setApellidoMaterno("Mendoza");
//        alumnoDAO.save(newAlumno);

//        Prueba de load
//        Alumno alumnoElegido = new Alumno();
//        alumnoElegido = alumnoDAO.load(1);
//        System.out.println(alumnoElegido.getNombres());

//        //Prueba de update
//        Alumno alumnoModificado = new Alumno();
//        alumnoModificado.setTelefono("995951026");
//        alumnoModificado.setIdAlumno(1);
//        alumnoDAO.update(alumnoModificado);
//
//        //Prueba de remove
//        newAlumno.setIdAlumno(1);
//        alumnoDAO.remove(newAlumno);
//
//        // Prueba de listAll
//        List<Alumno> alumnos = alumnoDAO.listAll();
//        for(Alumno alu : alumnos){
//            String nombre = alu.getNombres();
//            System.out.println(nombre);
//        }
    }
}
