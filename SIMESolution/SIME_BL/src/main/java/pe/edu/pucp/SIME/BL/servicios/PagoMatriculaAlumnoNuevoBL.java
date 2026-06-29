package pe.edu.pucp.SIME.BL.servicios;

import pe.edu.pucp.SIME.aula.DAO.gestionPagos.ConceptoPagoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionPagos.PagoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionPagos.ConceptoPagoDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionPagos.PagoDAOImpl;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoNuevoRequestDTO;
import pe.edu.pucp.SIME.model.DTO.PagoMatriculaDTO;

import java.util.List;

public class PagoMatriculaAlumnoNuevoBL {

    private final ConceptoPagoDAO conceptoPagoDAO = new ConceptoPagoDAOImpl();
    private final PagoDAO pagoDAO = new PagoDAOImpl();

    public List<PagoMatriculaDTO> registrarPagosAlumnoNuevo(
            int idAlumno,
            int idMatriculaDetalle,
            MatriculaAlumnoNuevoRequestDTO request
    ) throws Exception {

        List<PagoMatriculaDTO> pagos = conceptoPagoDAO.listarConceptosAlumnoNuevo();

        prepararPagosBase(pagos);

        aplicarDescuento(request, pagos);

        for (PagoMatriculaDTO pago : pagos) {
            pagoDAO.insertarPagoMatricula(idAlumno, idMatriculaDetalle, pago);
        }

        return pagos;
    }

    private void prepararPagosBase(List<PagoMatriculaDTO> pagos) {
        for (PagoMatriculaDTO pago : pagos) {
            pago.setMontoDescuento(0);
            pago.setMontoFinal(pago.getMontoOriginal());

            if (pago.getEstado() == null || pago.getEstado().isBlank()) {
                pago.setEstado("PENDIENTE");
            }
        }
    }

    private void aplicarDescuento(
            MatriculaAlumnoNuevoRequestDTO request,
            List<PagoMatriculaDTO> pagos
    ) {
        if (request.getPorcentajeDescuento() <= 0) {
            return;
        }

        for (PagoMatriculaDTO pago : pagos) {
            if (esConceptoMatricula(pago.getConcepto())) {
                double descuento = calcularDescuento(
                        pago.getMontoOriginal(),
                        request.getPorcentajeDescuento()
                );

                double montoFinal = pago.getMontoOriginal() - descuento;

                pago.setMontoDescuento(descuento);
                pago.setMontoFinal(montoFinal);
            }
        }
    }

    private double calcularDescuento(double montoOriginal, double porcentajeDescuento) {
        return montoOriginal * (porcentajeDescuento / 100.0);
    }

    private boolean esConceptoMatricula(String concepto) {
        String conceptoNormalizado = normalizarConcepto(concepto);

        return "MATRICULA".equals(conceptoNormalizado);
    }

    private String normalizarConcepto(String concepto) {
        if (concepto == null) {
            return "";
        }

        return concepto
                .trim()
                .toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace(" ", "_");
    }
}