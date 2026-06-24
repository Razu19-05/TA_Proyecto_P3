package pe.edu.pucp.SIME.aula.impl.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.GradoSeccionDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;
import pe.edu.pucp.SIME.model.gestionAcademica.TipoSeccion;

import java.sql.*;

public class GradoSeccionDAOImpl implements GradoSeccionDAO {
    @Override
    public GradoSeccion load(Integer id) throws SQLException {
        String sql = "select id_grado_seccion, nivel, grado, vacantes_maximas, activo " +
                "from SIME_GRADO_SECCION where id_grado_seccion = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    GradoSeccion grado = new GradoSeccion();
                    grado.setIdGradoSeccion(rs.getInt("id_grado_seccion"));
                    String nivel = rs.getString("nivel");
                    grado.setTipo(TipoSeccion.valueOf(nivel));
                    grado.setGrado(rs.getString("grado"));
                    grado.setVacantesMaximas(rs.getInt("vacantes_maximas"));
                    grado.setActivo(rs.getBoolean("activo"));
                    return grado;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public GradoSeccion buscarPorNivelYGrado(String nivel, String gradoStr) throws SQLException {
        String sql = "select id_grado_seccion, nivel, grado, vacantes_maximas, activo " +
                "from SIME_GRADO_SECCION where nivel = ? and grado = ? and activo = 1";
        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nivel);
            stmt.setString(2, gradoStr);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    GradoSeccion grado = new GradoSeccion();
                    grado.setIdGradoSeccion(rs.getInt("id_grado_seccion"));
                    String nivelDb = rs.getString("nivel");
                    grado.setTipo(TipoSeccion.valueOf(nivelDb));
                    grado.setGrado(rs.getString("grado"));
                    grado.setVacantesMaximas(rs.getInt("vacantes_maximas"));
                    grado.setActivo(rs.getBoolean("activo"));
                    return grado;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public GradoSeccion save(GradoSeccion gradoSeccion) throws SQLException {
        String sql = """
                INSERT INTO SIME_GRADO_SECCION 
                (nivel, grado, vacantes_maximas, activo) 
                VALUES (?, ?, ?, ?)
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, gradoSeccion.getTipo().name());

            pstm.setString(2, gradoSeccion.getGrado());
            pstm.setInt(3, gradoSeccion.getVacantesMaximas());
            pstm.setBoolean(4, gradoSeccion.isActivo());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        gradoSeccion.setIdGradoSeccion(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return gradoSeccion;
    }

    @Override
    public GradoSeccion update(GradoSeccion gradoSeccion) throws SQLException {
        String sql = """
                UPDATE SIME_GRADO_SECCION 
                SET vacantes_maximas = ?
                WHERE id_grado_seccion = ?
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1,gradoSeccion.getVacantesMaximas());
            pstm.setInt(2,gradoSeccion.getIdGradoSeccion());

            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el gradoSeccion con ID: " + gradoSeccion.getIdGradoSeccion());
            }
        }
        return gradoSeccion;
    }

    @Override
    public void remove(GradoSeccion gradoSeccion) throws SQLException {
        String sql = """
                UPDATE SIME_GRADO_SECCION 
                SET activo = 0
                WHERE id_grado_seccion = ?
                """;

        Connection conn = TransactionContext.getConnection();

        try (PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1,gradoSeccion.getIdGradoSeccion());

            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el gradoSeccion con ID: " + gradoSeccion.getIdGradoSeccion());
            }
        }
    }
}
