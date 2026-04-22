package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.model.GradoSeccion;
import pe.edu.pucp.SIME.estudiante.model.Alumno;
import pe.edu.pucp.SIME.matricula.DAO.MatriculaDAO;
import pe.edu.pucp.SIME.matricula.impl.MatriculaDAOImpl;
import pe.edu.pucp.SIME.matricula.model.Matricula;
import pe.edu.pucp.SIME.matricula.model.Periodo;
import java.util.Date;
import java.util.List;

public class TestMatricula {
    public static void main(String[] args) {
        MatriculaDAO matriculaDAO = new MatriculaDAOImpl();
        //Prueba Save
        Matricula newMatricula = new Matricula();
//
//        newMatricula.setFecha(java.sql.Date.valueOf("2026-04-23"));
//        newMatricula.setEstado("ACTIVO");
//        newMatricula.setMonto(330);
//
//        Alumno alu = new Alumno();
//        alu.setIdAlumno(6);
//        newMatricula.setAlumno(alu);
//
//        Periodo periodo =new Periodo();
//        periodo.setIdPeriodo(1);
//        newMatricula.setPeriodo(periodo);
//
//        GradoSeccion gs = new GradoSeccion();
//        gs.setIdGrado(1);
//        newMatricula.setGrado(gs);
//
//        matriculaDAO.save(newMatricula);
        //Prueba load
//        Matricula matricula = new Matricula();
//        matricula = matriculaDAO.load(4);
//        System.out.println(matricula.getMonto() + " - " +matricula.getEstado());
        //Prueba Remove
//        newMatricula.setIdMatricula(4);
//        matriculaDAO.remove(newMatricula);
//        System.out.println(newMatricula.getEstado());
        //Prueba update
//        newMatricula.setMonto(400);
//        matriculaDAO.update(newMatricula);
//
//        System.out.println(newMatricula.getMonto() + " - " +newMatricula.getEstado());
       //Prueba LISTALL
        List<Matricula> matriculas = matriculaDAO.listAll();
        for(Matricula m : matriculas){
            newMatricula = m;
            System.out.println(newMatricula.getMonto() + " - " +newMatricula.getEstado());

        }
    }
}