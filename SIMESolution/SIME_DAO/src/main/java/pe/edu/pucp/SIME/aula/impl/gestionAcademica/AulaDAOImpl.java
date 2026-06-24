package pe.edu.pucp.SIME.aula.impl.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AulaDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;
import pe.edu.pucp.SIME.model.gestionAcademica.TipoAula;

import java.sql.*;

public class AulaDAOImpl implements AulaDAO {
    @Override
    public Aula load(Integer id) throws SQLException {
        String sql = "select id_aula, codigo, tipo_aula, capacidad, activo from SIME_AULA where id_aula = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Aula aula = new Aula();
                    aula.setIdAula(rs.getInt("id_aula"));
                    aula.setCodigo(rs.getString("codigo"));
                    String tipo = rs.getString("tipo_aula");
                    aula.setTipo(TipoAula.valueOf(tipo));
                    aula.setCapacidad(rs.getInt("capacidad"));
                    aula.setActivo(rs.getBoolean("activo"));
                    return aula;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Aula save(Aula aula) throws SQLException {
        String sql = """
                insert into SIME_AULA
                (codigo, tipo_aula, capacidad, activo )
                values (?,?,?,?)
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstm.setString(1,aula.getCodigo());
            pstm.setString(2, aula.getTipo().name());
            pstm.setInt(3,aula.getCapacidad());
            pstm.setBoolean(4,aula.isActivo());
            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        aula.setIdAula(newId);
                    }
                }
            }
        }
        return aula;
    }

    @Override
    public Aula update(Aula aula) throws SQLException {
        String sql = """
                UPDATE SIME_AULA 
                SET codigo = ?, tipo_aula = ?, capacidad = ?, activo = ? 
                WHERE id_aula = ?
                """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, aula.getCodigo());
            pstm.setString(2, aula.getTipo().name());
            pstm.setInt(3, aula.getCapacidad());
            pstm.setBoolean(4, aula.isActivo());
            pstm.setInt(5, aula.getIdAula());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No se encontró el concepto de aula con ID: " + aula.getIdAula());
            }
        }
        return aula;
    }

    @Override
    public void remove(Aula aula) throws SQLException {
        String sql = """
                UPDATE SIME_AULA 
                SET activo = 0
                WHERE id_aula = ?
                """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, aula.getIdAula());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No se encontró el concepto de aula con ID: " + aula.getIdAula());
            }
        }
    }
}
