package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.gestionPagos.Pago;

import java.util.List;

public interface IPagoBL {
    List<Pago> listarPagosAlumno(int idALumno) throws Exception;
}
