package pe.edu.pucp.SIME.aula.impl.gestionDescuento;

import pe.edu.pucp.SIME.aula.DAO.gestionDescuento.TipoDeDescuentoDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionDescuento.TipoDeDescuento;

import java.sql.*;

public class TipoDeDescuentoDAOImpl implements TipoDeDescuentoDAO {
    @Override
    public TipoDeDescuento load(Integer id_tipoDesc) throws SQLException {
        String sql = "select id_tipo_descuento, nombre, descripcion, activo from SIME_TIPO_DESCUENTO where id_tipo_descuento = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id_tipoDesc);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    TipoDeDescuento tipo = new TipoDeDescuento();
                    tipo.setIdTipoDeDescuento(rs.getInt("id_tipo_descuento"));
                    tipo.setNombre(rs.getString("nombre"));
                    tipo.setDescripcion(rs.getString("descripcion"));
                    tipo.setActivo(rs.getBoolean("activo"));
                    return tipo;
                }
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public TipoDeDescuento save(TipoDeDescuento tipoDeDescuento) throws SQLException {
        String sql = """
                INSERT INTO SIME_TIPO_DESCUENTO (nombre, descripcion, activo) values (?,?,?)
                """;
        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, tipoDeDescuento.getNombre());
            pstm.setString(2, tipoDeDescuento.getDescripcion());
            pstm.setBoolean(3, tipoDeDescuento.isActivo());

            int affectedRows = pstm.executeUpdate();

            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        tipoDeDescuento.setIdTipoDeDescuento(newId);
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tipoDeDescuento;
    }

    @Override
    public TipoDeDescuento update(TipoDeDescuento tipoDeDescuento) throws SQLException {
        String sql = """
                UPDATE nombre = ?, descripcion = ? FROM SIME_TIPO_DESCUENTO WHERE id_tipo_descuento = ?
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setString(1,tipoDeDescuento.getNombre());
            pstm.setString(2,tipoDeDescuento.getDescripcion());
            pstm.setInt(3,tipoDeDescuento.getIdTipoDeDescuento());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el tipod descuento con ID: " + tipoDeDescuento.getIdTipoDeDescuento());
            }
        }
        return tipoDeDescuento;
    }

    @Override
    public void remove(TipoDeDescuento tipoDeDescuento) throws SQLException {
        String sql = """
                UPDATE activo = 0 FROM SIME_TIPO_DESCUENTO WHERE id_tipo_descuento = ?
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,tipoDeDescuento.getIdTipoDeDescuento());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el tipod descuento con ID: " + tipoDeDescuento.getIdTipoDeDescuento());
            }
        }
    }
}
