package pe.edu.pucp.SIME.aula.impl.gestionPagos;

import pe.edu.pucp.SIME.aula.DAO.gestionPagos.ConceptoPagoDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionPagos.ConceptoPago;

import java.sql.*;

public class ConceptoPagoDAOImpl implements ConceptoPagoDAO {
    @Override
    public ConceptoPago load(Integer idConcepto) throws SQLException {
        String sql = """
                SELECT id_concepto,nombre,monto,activo FROM SIME_CONCEPTO_PAGO WHERE id_concepto = ?
                """;

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, idConcepto);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    ConceptoPago concepto = new ConceptoPago();
                    concepto.setIdConceptoPago(rs.getInt("id_concepto"));
                    concepto.setNombre(rs.getString("nombre"));
                    concepto.setMonto(rs.getDouble("monto"));
                    concepto.setActivo(rs.getBoolean("activo"));
                    return concepto;
                }
                return null;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConceptoPago save(ConceptoPago concepto) throws SQLException {
        String sql = "INSERT INTO SIME_CONCEPTO_PAGO (nombre, monto, activo) VALUES (?, ?, ?)";

        Connection conn = TransactionContext.getConnection();
        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, concepto.getNombre());
            pstm.setDouble(2, concepto.getMonto());
            pstm.setBoolean(3, concepto.isActivo());

            int affectedRows = pstm.executeUpdate();

            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        concepto.setIdConceptoPago(newId);
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return concepto;
    }

    @Override
    public ConceptoPago update(ConceptoPago concepto) throws SQLException {
        String sql = "UPDATE SIME_CONCEPTO_PAGO SET nombre = ?, monto = ?, activo = ? WHERE id_concepto = ?";

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, concepto.getNombre());
            pstm.setDouble(2, concepto.getMonto());
            pstm.setBoolean(3, concepto.isActivo());
            pstm.setInt(4, concepto.getIdConceptoPago());

            pstm.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return concepto;
    }

    @Override
    public void remove(ConceptoPago conceptoPago) throws SQLException {
        String sql = "UPDATE SIME_CONCEPTO_PAGO SET activo = 0 WHERE id_concepto = ?";

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, conceptoPago.getIdConceptoPago());
            pstm.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
