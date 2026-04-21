package pe.edu.pucp.SIME.test;


import pe.edu.pucp.SIME.matricula.DAO.PeriodoDAO;
import pe.edu.pucp.SIME.matricula.impl.PeriodoDAOImpl;
import pe.edu.pucp.SIME.matricula.model.Periodo;

import java.util.List;

public class TestPeriodo {
    public static void main(String[] args) {
        PeriodoDAO periodoDAO = new PeriodoDAOImpl();
        Periodo newPeriodo = new Periodo();
        newPeriodo.setAnioEscolar(2025);
        newPeriodo.setFechaInicio(java.sql.Date.valueOf("2025-03-08"));
        newPeriodo.setFechaFin(java.sql.Date.valueOf("2025-12-15"));
        periodoDAO.save(newPeriodo);

    }
}
