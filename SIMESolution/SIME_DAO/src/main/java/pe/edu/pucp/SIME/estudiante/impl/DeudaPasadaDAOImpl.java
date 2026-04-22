package pe.edu.pucp.SIME.estudiante.impl;

import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.estudiante.DAO.DeudaPasadaDAO;
import pe.edu.pucp.SIME.estudiante.model.DeudaPasada;
import pe.edu.pucp.SIME.estudiante.model.TipoDeuda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeudaPasadaDAOImpl implements DeudaPasadaDAO {


    @Override
    public DeudaPasada load(Integer idDeudaPasada) {
        String sql = "SELECT id_deuda_pasada, id_alumno, tipo_deuda, monto, estado FROM deuda_pasada WHERE id_deuda_pasada = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, idDeudaPasada);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    DeudaPasada deuda = new DeudaPasada();

                    deuda.setIdDeudaPasada(rs.getInt(1));
                    deuda.setIdAlumno(rs.getInt(2)); // NUEVO
                    deuda.setTipoDeuda(TipoDeuda.valueOf(rs.getString(3).toUpperCase()));
                    deuda.setMonto(rs.getDouble(4));
                    deuda.setEstado(rs.getString(5));

                    return deuda;
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public DeudaPasada save(DeudaPasada deuda) {
        String sql = "INSERT INTO deuda_pasada (tipo_deuda, monto, estado) VALUES (?, ?, ?)";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, deuda.getTipoDeuda().name()); // enum → String
            pstm.setDouble(2, deuda.getMonto());
            pstm.setString(3, deuda.getEstado());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        deuda.setIdDeudaPasada(newId);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return deuda;
    }
    @Override
    public DeudaPasada update(DeudaPasada deuda) {
        String sql = "INSERT INTO deuda_pasada (id_alumno, tipo_deuda, monto, estado) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setInt(1, deuda.getIdAlumno());
            pstm.setString(2, deuda.getTipoDeuda().name());
            pstm.setDouble(3, deuda.getMonto());
            pstm.setString(4, deuda.getEstado());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        deuda.setIdDeudaPasada(newId);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return deuda;
    }
    @Override
    public void remove(DeudaPasada deuda) {
        String sql = "UPDATE deuda_pasada SET estado = ? WHERE id_deuda_pasada = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            // Puedes marcarla como "PAGADO" o "INACTIVO"
            pstm.setString(1, "PAGADO");
            pstm.setInt(2, deuda.getIdDeudaPasada());

            int resultado = pstm.executeUpdate();

            if (resultado == 0) {
                System.out.println("No se encontró la deuda con ID: " + deuda.getIdDeudaPasada());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DeudaPasada> listAll() {
        List<DeudaPasada> deudas = new ArrayList<>();

        String sql = "SELECT id_deuda_pasada, tipo_deuda, monto, estado FROM deuda_pasada";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                DeudaPasada deuda = new DeudaPasada();

                deuda.setIdDeudaPasada(rs.getInt(1));
                deuda.setTipoDeuda(TipoDeuda.valueOf(rs.getString(2).toUpperCase()));
                deuda.setMonto(rs.getDouble(3));
                deuda.setEstado(rs.getString(4));

                deudas.add(deuda);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return deudas;
    }
}
