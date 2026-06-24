package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.gestionPagos.Pago;

import java.util.List;

public interface IPagoBL {
    List<Pago> listarPagosAlumno(int idALumno) throws Exception;

    Pago actualizarPago(int id,Pago pagoAct) throws Exception;

    void eliminarPago(int idPago) throws Exception;
}
