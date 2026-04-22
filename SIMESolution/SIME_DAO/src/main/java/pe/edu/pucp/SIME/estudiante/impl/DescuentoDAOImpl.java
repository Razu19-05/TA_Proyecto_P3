package pe.edu.pucp.SIME.estudiante.impl;

import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.estudiante.DAO.DescuentoDAO;
import pe.edu.pucp.SIME.estudiante.model.Descuento;
import pe.edu.pucp.SIME.estudiante.model.TipoDescuento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DescuentoDAOImpl implements DescuentoDAO {
    @Override
    public Descuento load(Integer descuentoID) {
        String sql = "select id_descuento, id_alumno, tipo_descuento, descripcion, monto from descuento where id_descuento = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, descuentoID);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Descuento desc = new Descuento();
                    desc.setIdDescuento(rs.getInt(1));
                    desc.setIdAlumno(rs.getInt(2)); // Obtenemos el id del alumno
                    desc.setTipoDescuento(TipoDescuento.valueOf(rs.getString(3)));
                    desc.setDescripcion(rs.getString(4));
                    desc.setDescuento(rs.getDouble(5)); // Mapeamos 'monto' a la variable 'descuento'
                    return desc;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Descuento save(Descuento descuento) {
        String sql = "insert descuento(id_alumno,tipo_descuento, descripcion, monto)  values (?,?,?,?)";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setInt(1,descuento.getIdAlumno());
            pstm.setString(2,descuento.getTipoDescuento().name());
            pstm.setString(3,descuento.getDescripcion());
            pstm.setDouble(4,descuento.getDescuento());

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        descuento.setIdDescuento(newId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return descuento;
    }

    @Override
    public Descuento update(Descuento  descuentoObj) {
        String sql = "update descuento set monto = ? WHERE id_descuento = ?";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setDouble(1, descuentoObj.getDescuento());
            pstm.setInt(2, descuentoObj.getIdDescuento()); // El WHERE

            pstm.executeUpdate();
            return descuentoObj;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Descuento  descuento) {
        String sql = "UPDATE descuento SET monto = ? WHERE id_descuento = ?";
        descuento.setDescuento(0.0);
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setDouble(1, descuento.getDescuento());
            pstm.setInt(2, descuento.getIdDescuento()); // El WHERE

            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el descuento", e);
        }
    }

    @Override
    public List<Descuento> listAll() {
        List<Descuento> lista = new ArrayList<>();
        String sql = "SELECT id_descuento, id_alumno, tipo_descuento, descripcion, monto " +
                "FROM descuento WHERE monto > 0";

        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Descuento desc = new Descuento();
                desc.setIdDescuento(rs.getInt(1));
                desc.setIdAlumno(rs.getInt(2));
                desc.setTipoDescuento(TipoDescuento.valueOf(rs.getString(3)));
                desc.setDescripcion(rs.getString(4));
                desc.setDescuento(rs.getDouble(5));

                lista.add(desc);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar descuentos válidos", e);
        }
        return lista;
    }
}
