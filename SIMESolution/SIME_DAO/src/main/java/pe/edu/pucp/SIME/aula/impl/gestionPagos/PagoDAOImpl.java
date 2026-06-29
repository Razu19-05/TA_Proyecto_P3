package pe.edu.pucp.SIME.aula.impl.gestionPagos;

import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaDetalleDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionPagos.ConceptoPagoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionPagos.PagoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaDetalleDAOImpl;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.PagoMatriculaDTO;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;
import pe.edu.pucp.SIME.model.gestionPagos.ConceptoPago;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;
import pe.edu.pucp.SIME.model.gestionPagos.TipoEstado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAOImpl implements PagoDAO {

    public MatriculaDetalle buscarMatricula (int id) throws SQLException{
        MatriculaDetalleDAO matriculaDetalleDAO = new MatriculaDetalleDAOImpl();
        MatriculaDetalle matriculaDetalle = matriculaDetalleDAO.load(id);
        return matriculaDetalle;
    }
    public ConceptoPago busarConcepto (int id) throws SQLException{
        ConceptoPagoDAO conceptoPagoDAO = new ConceptoPagoDAOImpl();
        ConceptoPago conceptoPago = conceptoPagoDAO.load(id);
        return conceptoPago;
    }

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
        Connection connection = TransactionContext.getConnection();
        try (
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

                    MatriculaDetalle detalle = buscarMatricula(rs.getInt("id_matricula_detalle"));
                    pago.setMatriculaDetalle(detalle);

                    ConceptoPago concepto = busarConcepto(rs.getInt("id_concepto"));
                    pago.setConceptoPago(concepto);

                    return pago;
                }

            }
        }
        return null;
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

            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        pago.setIdPago(newId);
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

            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el pago con ID: " + pago.getIdPago());
            }
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

    @Override
    public List<Pago> listarPagosdeAlumno(int idAlumno) throws SQLException {
        String sql = """

                SELECT
                    p.id_pago,
                    p.id_matricula_detalle,
                    p.id_concepto,
                    p.monto_descuento,
                    p.monto_final,
                    p.fecha_emision,
                    p.fecha_vencimiento,
                    p.fecha_pago,
                    p.estado,
                    p.observacion,
                    p.activo
                FROM SIME_PAGO p
                INNER JOIN SIME_MATRICULA_DETALLE md ON p.id_matricula_detalle = md.id_matricula_detalle
                WHERE md.id_alumno = ? AND p.activo = 1
                ORDER BY p.fecha_emision DESC
                """;
        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, idAlumno);
            List<Pago> pagos = new ArrayList<>();
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
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

                    MatriculaDetalle detalle = buscarMatricula(rs.getInt("id_matricula_detalle"));
                    pago.setMatriculaDetalle(detalle);

                    ConceptoPago concepto = busarConcepto(rs.getInt("id_concepto"));
                    pago.setConceptoPago(concepto);

                    pagos.add(pago);

                }
            }
            return pagos;

        }
    }

    @Override
    public int insertarPagoMatricula(
            int idAlumno,
            int idMatriculaDetalle,
            PagoMatriculaDTO pago
    ) throws SQLException {

        String sql = """
        INSERT INTO SIME_PAGO
        (
            id_matricula_detalle,
            id_concepto,
            monto_descuento,
            monto_final,
            fecha_emision,
            fecha_vencimiento,
            fecha_pago,
            estado,
            observacion,
            activo
        )
        SELECT
            ?,
            cp.id_concepto,
            ?,
            ?,
            CURDATE(),
            DATE_ADD(CURDATE(), INTERVAL 30 DAY),
            NULL,
            'PENDIENTE',
            ?,
            1
        FROM SIME_CONCEPTO_PAGO cp
        WHERE cp.activo = 1
          AND UPPER(cp.nombre) = UPPER(?)
        LIMIT 1
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setInt(1, idMatriculaDetalle);
            pstm.setDouble(2, pago.getMontoDescuento());
            pstm.setDouble(3, pago.getMontoFinal());
            pstm.setString(4, "Pago generado por matrícula de alumno nuevo");
            pstm.setString(5, pago.getConcepto());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró el concepto de pago: " + pago.getConcepto());
            }

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }
}
