package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionPagos.ConceptoPagoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionPagos.ConceptoPagoDAOImpl;
import pe.edu.pucp.SIME.model.gestionPagos.ConceptoPago;

import java.sql.SQLException;

public class ConceptoPagoTest {
    public static void main(String [] args) throws SQLException{
        ConceptoPagoDAO conceptoDAO = new ConceptoPagoDAOImpl();
        ConceptoPago conceptoPago = conceptoDAO.load(1);
        System.out.println(conceptoPago.getIdConceptoPago());
        System.out.println(conceptoPago.getNombre());
        System.out.println(conceptoPago.getMonto());
    }
}
