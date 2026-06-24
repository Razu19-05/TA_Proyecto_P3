package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaCabeceraDAOImpl;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;

import java.sql.SQLException;

public class MatriculaCabezeraTest
{
    public static void main(String [] args) throws SQLException{
        MatriculaCabeceraDAO matriculaCabeceraDAO = new MatriculaCabeceraDAOImpl();
        MatriculaCabecera matriculaCabecera = matriculaCabeceraDAO.load(1);

        System.out.println(matriculaCabecera.getIdMatriculaCabecera());
        System.out.println(matriculaCabecera.getTotalVacantes());
    }
}
