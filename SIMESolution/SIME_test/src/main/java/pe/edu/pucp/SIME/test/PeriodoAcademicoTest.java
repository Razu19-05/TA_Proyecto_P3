package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.PeriodoAcademicoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.PeriodoAcademicoDAOImpl;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;

import java.sql.SQLException;

public class PeriodoAcademicoTest {
    public static void main(String [] args) throws SQLException{
        PeriodoAcademicoDAO periodoDAO = new PeriodoAcademicoDAOImpl();
        PeriodoAcademico periodo = periodoDAO.load(1);
        System.out.println(periodo.getIdPeriodoAcademico());
        System.out.println(periodo.getAnioEscolar());
        System.out.println(periodo.getFechaInicio());
        System.out.println(periodo.getFechaInicio());
    }
}
