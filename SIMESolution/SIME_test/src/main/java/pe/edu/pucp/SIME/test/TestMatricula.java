package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.estudiante.model.Alumno;
import pe.edu.pucp.SIME.matricula.DAO.MatriculaDAO;
import pe.edu.pucp.SIME.matricula.impl.MatriculaDAOImpl;
import pe.edu.pucp.SIME.matricula.model.Matricula;
import pe.edu.pucp.SIME.matricula.model.Periodo;
import java.util.Date;

public class TestMatricula {
    public static void main(String[] args) {
        MatriculaDAO matriculaDAO = new MatriculaDAOImpl();

        Matricula newMatricula = new Matricula();
        newMatricula.setFecha(java.sql.Date.valueOf("2026-04-23"));
        newMatricula.setEstado("ACTIVO");
        newMatricula.setMonto(330);
        Alumno alu = new Alumno();
        alu.setIdAlumno(8);
        newMatricula.setAlumno(alu);
        Periodo periodo =new Periodo();
        periodo.setIdPeriodo(1);
        newMatricula.setPeriodo(periodo);
        matriculaDAO.save(newMatricula);
    }
}