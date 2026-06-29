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

    @Override
    public Pago actualizarPago(int id,Pago pagoAct) throws Exception {
        try{
            TransactionContext.getConnection();
            pagoAct.setIdPago(id);
            pagoAct = pagoDAO.update(pagoAct);
            TransactionContext.commit();
            return pagoAct;
        }catch (Exception e){
            throw new Exception("Error al actualizar pago: " + e.getMessage());
        }finally {
            TransactionContext.close();
        }
    }

    @Override
    public void eliminarPago(int idPago) throws Exception {
        try{
            TransactionContext.getConnection();
            Pago pago = new Pago();
            pago.setIdPago(idPago);
            pagoDAO.remove(pago);
            TransactionContext.commit();
        }catch (Exception e){
            throw new Exception("Error al eliminar pago: " + e.getMessage());
        }finally {
            TransactionContext.close();
        }
    }

    @Override
    public List<Pago> listarPagosPendientesAlumno(int idALumno) throws Exception {
        try{
            TransactionContext.getConnection();
            List <Pago> pagosAlumno = pagoDAO.listarPagosPendientesdeAlumno(idALumno);
            TransactionContext.commit();
            return pagosAlumno;
        }catch (Exception e){
            throw new Exception("Error al listar pagos del alumno: " + e.getMessage());
        }finally {
            TransactionContext.close();
        }
    }

    @Override
    public Pago pagarPago(int idPago, String observacion) throws Exception {
        try {
            TransactionContext.getConnection();

            if (idPago <= 0) {
                throw new Exception("El ID del pago no es válido.");
            }

            if (observacion == null || observacion.isBlank()) {
                observacion = "Pago confirmado";
            }

            Pago pago = pagoDAO.marcarComoPagado(idPago, observacion.trim());

            TransactionContext.commit();

            return pago;

        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al pagar deuda: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public Pago anularPago(int idPago, String observacion) throws Exception {
        try {
            TransactionContext.getConnection();

            if (idPago <= 0) {
                throw new Exception("El ID del pago no es válido.");
            }

            if (observacion == null || observacion.isBlank()) {
                throw new Exception("Debe ingresar una observación para anular el pago.");
            }

            Pago pago = pagoDAO.marcarComoAnulado(idPago, observacion.trim());

            TransactionContext.commit();

            return pago;

        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al anular deuda: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }
}
