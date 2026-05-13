package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.PeriodoAcademicoDAO;
import pe.edu.pucp.SIME.aula.impl.PeriodoAcademicoDAOImp;
import pe.edu.pucp.SIME.model.PeriodoAcademico;

import java.util.Date;

public class PeriodoAcademicoTest {
    public static void main(String[] args) {
        PeriodoAcademicoDAO periodoDAO = new PeriodoAcademicoDAOImp();
        //Prueba de load
        PeriodoAcademico periodoElegido = new PeriodoAcademico();
        periodoElegido = periodoDAO.load(1);
        System.out.println(periodoElegido.getAnioEscolar());
        //Prueba de save
        PeriodoAcademico periodoGuarda = new PeriodoAcademico();
//        periodoGuarda.setAnioEscolar(2027);
//        periodoGuarda.setActivo(1);
//        periodoGuarda.setFechaInicio(java.sql.Date.valueOf(java.time.LocalDate.of(2027, 3, 27)));
//        periodoGuarda.setFechaFin(java.sql.Date.valueOf(java.time.LocalDate.of(2027, 12, 15)));
//        periodoGuarda.setActivo(1);
//        periodoDAO.save(periodoGuarda);
        //Prueba de update
        periodoGuarda.setIdPeriodoAcademico(6);
//        periodoGuarda.setFechaFin(java.sql.Date.valueOf(java.time.LocalDate.of(2027, 12, 25)));
//        periodoDAO.update(periodoGuarda);
//        periodoDAO.remove(periodoGuarda);


    }
}
