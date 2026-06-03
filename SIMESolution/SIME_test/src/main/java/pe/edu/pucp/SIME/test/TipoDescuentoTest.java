package pe.edu.pucp.SIME.test;

import pe.edu.pucp.SIME.aula.DAO.gestionDescuento.TipoDeDescuentoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionDescuento.TipoDeDescuentoDAOImpl;
import pe.edu.pucp.SIME.model.gestionDescuento.TipoDeDescuento;

import java.sql.SQLException;

public class TipoDescuentoTest {
    public static void main(String [] args) throws SQLException{
        TipoDeDescuentoDAO tipoDescDAO = new TipoDeDescuentoDAOImpl();
        TipoDeDescuento tipoDesc = tipoDescDAO.load(1);
        System.out.println(tipoDesc.getIdTipoDeDescuento());
        System.out.println(tipoDesc.getNombre());
        System.out.println(tipoDesc.getDescripcion());
    }
}
