package pe.edu.pucp.SIME.aula.impl.gestionPagos;

import pe.edu.pucp.SIME.aula.DAO.gestionPagos.PagoDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;
import pe.edu.pucp.SIME.model.gestionPagos.ConceptoPago;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;
import pe.edu.pucp.SIME.model.gestionPagos.TipoEstado;

import java.sql.*;

public class PagoDAOImpl implements PagoDAO {

    @Override
    public Pago load(Integer pagoID) throws SQLException {
        String sql = """
                SELECT
                id_pago,
                id_matricula_detalle,
                id_concepto,
                monto_descuento,
                monto_final,
                fecha_emision,
                fecha_vencimiento,
                fecha_pago,
                estado,
                observacion,activo FROM SIME_PAGO WHERE id_pago = ?
                """;

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, pagoID);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Pago pago = new Pago();
                    pago.setIdPago(rs.getInt("id_pago"));
                    pago.setMontoDescuento(rs.getDouble("monto_descuento"));
                    pago.setMontoFinal(rs.getDouble("monto_final"));

                    // Fechas
                    pago.setFechaEmision(rs.getDate("fecha_emision"));
                    pago.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                    pago.setFechaPago(rs.getDate("fecha_pago")); // Retorna null si en la BD está vacío

                    // Enum TipoEstado
                    String estadoStr = rs.getString("estado");
                    if (estadoStr != null) {
                        pago.setEstado(TipoEstado.valueOf(estadoStr));
                    }

                    pago.setObservacion(rs.getString("observacion"));
                    pago.setActivo(rs.getBoolean("activo"));

                    // Cascarón de MatriculaDetalle
                    MatriculaDetalle detalle = new MatriculaDetalle();
                    detalle.setIdMatriculaDetalle(rs.getInt("id_matricula_detalle"));
                    pago.setMatriculaDetalle(detalle);

                    // Cascarón de ConceptoPago
                    ConceptoPago concepto = new ConceptoPago();
                    concepto.setIdConceptoPago(rs.getInt("id_concepto"));
                    pago.setConceptoPago(concepto);

                    return pago;
                }
                return null;
            }
        }
    }

    @Override
    public Pago save(Pago pago) throws SQLException {
        String sql = """
                INSERT INTO SIME_PAGO 
                (id_matricula_detalle, id_concepto, monto_descuento, monto_final, 
                 fecha_emision, fecha_vencimiento, fecha_pago, estado, observacion, activo) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, pago.getMatriculaDetalle().getIdMatriculaDetalle());
            pstm.setInt(2, pago.getConceptoPago().getIdConceptoPago());

            pstm.setDouble(3, pago.getMontoDescuento());
            pstm.setDouble(4, pago.getMontoFinal());

            pstm.setDate(5, new java.sql.Date(pago.getFechaEmision().getTime()));
            pstm.setDate(6, new java.sql.Date(pago.getFechaVencimiento().getTime()));

            if (pago.getFechaPago() != null) {
                pstm.setDate(7, new java.sql.Date(pago.getFechaPago().getTime()));
            } else {
                pstm.setNull(7, Types.DATE);
            }
            pstm.setString(8, pago.getEstado().name());
            pstm.setString(9, pago.getObservacion());
            pstm.setBoolean(10, pago.isActivo());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pago.setIdPago(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return pago;
    }

    @Override
    public Pago update(Pago pago) throws SQLException {
        String sql = """
                UPDATE SIME_PAGO 
                SET id_matricula_detalle = ?, id_concepto = ?, monto_descuento = ?, monto_final = ?, 
                    fecha_emision = ?, fecha_vencimiento = ?, fecha_pago = ?, estado = ?, observacion = ?, activo = ? 
                WHERE id_pago = ?
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, pago.getMatriculaDetalle().getIdMatriculaDetalle());
            pstm.setInt(2, pago.getConceptoPago().getIdConceptoPago());
            pstm.setDouble(3, pago.getMontoDescuento());
            pstm.setDouble(4, pago.getMontoFinal());

            pstm.setDate(5, new java.sql.Date(pago.getFechaEmision().getTime()));
            pstm.setDate(6, new java.sql.Date(pago.getFechaVencimiento().getTime()));

            if (pago.getFechaPago() != null) {
                pstm.setDate(7, new java.sql.Date(pago.getFechaPago().getTime()));
            } else {
                pstm.setNull(7, Types.DATE);
            }

            pstm.setString(8, pago.getEstado().name());
            pstm.setString(9, pago.getObservacion());
            pstm.setBoolean(10, pago.isActivo());

            // Parámetro del WHERE
            pstm.setInt(11, pago.getIdPago());

            pstm.executeUpdate();
        }
        return pago;
    }

    @Override
    public void remove(Pago pago) throws SQLException {
        String sql = "UPDATE SIME_PAGO SET activo = 0 WHERE id_pago = ?";

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, pago.getIdPago());
            pstm.executeUpdate();
        }
    }
}
