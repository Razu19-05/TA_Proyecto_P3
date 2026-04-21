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

        // --- PROBAR SAVE ---
        System.out.println("--- Probando Guardado ---");
        Matricula nuevaMat = new Matricula();
        nuevaMat.setFecha(new Date());
        nuevaMat.setMonto(1500.00);

        // Importante: Estos IDs deben existir en tu DB
        Alumno alu = new Alumno();
        alu.setIdAlumno(1);
        nuevaMat.setAlumno(alu);

        Periodo per = new Periodo();
        per.setIdPeriodo(1);
        nuevaMat.setPeriodo(per);

        matriculaDAO.save(nuevaMat);
        System.out.println("Matrícula guardada con ID: " + nuevaMat.getIdMatricula());

        // --- PROBAR LOAD ---
        System.out.println("\n--- Probando Carga ---");
        Matricula cargada = matriculaDAO.load(nuevaMat.getIdMatricula());
        if (cargada != null) {
            System.out.println("ID Cargado: " + cargada.getIdMatricula());
            System.out.println("Monto: " + cargada.getMonto());
            System.out.println("Estado: " + cargada.getEstado());
            System.out.println("ID Alumno asociado: " + cargada.getAlumno().getIdAlumno());
        }


    }
}