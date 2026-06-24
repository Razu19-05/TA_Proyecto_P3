package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionDescuento.DescuentoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDescuento.DescuentoDAOImpl;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionDescuento.Descuento;
import pe.edu.pucp.SIME.model.gestionDescuento.TipoDeDescuento;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;

import java.sql.SQLException;

public class DescuentoTest {
    public static void main(String [] args) throws SQLException {
        DescuentoDAO descuentoDAO = new DescuentoDAOImpl();
        Descuento descuento = descuentoDAO.load(1);
        TipoDeDescuento tipo = descuento.getTipoDeDescuento();
        MatriculaDetalle matriculaDetalle = descuento.getMatriculaDetalle();
        Alumno alumno = matriculaDetalle.getAlumno();
        MatriculaCabecera matriculaCabecera = matriculaDetalle.getMatriculaCabecera();
        PeriodoAcademico periodo = matriculaCabecera.getPeriodoAcademico();
        GradoSeccion grado = matriculaCabecera.getGradoSeccion();
        Aula aula = matriculaCabecera.getAula();
        System.out.println(descuento.getMotivo());
        System.out.println(tipo.getDescripcion());
        System.out.println(matriculaDetalle.getEstado());
        System.out.println(alumno.getNombres());
        System.out.println(matriculaCabecera.getTotalVacantes());
        System.out.println(periodo.getAnioEscolar());
        System.out.println(grado.getTipo());
        System.out.println(aula.getTipo());



    }
}
