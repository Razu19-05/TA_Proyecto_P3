package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IPagoBL;
import pe.edu.pucp.SIME.aula.DAO.gestionPagos.PagoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionPagos.PagoDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;

import java.util.ArrayList;
import java.util.List;

public class PagoBLImpl implements IPagoBL {
    private PagoDAO pagoDAO = new PagoDAOImpl();
    @Override
    public List<Pago> listarPagosAlumno(int idALumno) throws Exception {
        try{
            TransactionContext.getConnection();
            List <Pago> pagosAlumno = pagoDAO.listarPagosdeAlumno(idALumno);
            TransactionContext.commit();
            return pagosAlumno;
        }catch (Exception e){
            throw new Exception("Error al listar pagos del alumno: " + e.getMessage());
        }finally {
            TransactionContext.close();
        }
    }
}
