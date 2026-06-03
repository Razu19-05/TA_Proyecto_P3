package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionPagos.ConceptoPagoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionPagos.PagoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionPagos.PagoDAOImpl;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;

import java.sql.SQLException;

public class PagoTest {
    public static void main(String [] args) throws SQLException{
        PagoDAO pagoDao = new PagoDAOImpl();

        Pago pago = pagoDao.load(1);

        System.out.println(pago.getIdPago());
        System.out.println(pago.getMontoFinal());


    }
}
