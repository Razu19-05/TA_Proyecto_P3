package pe.edu.pucp.SIME.aula.impl.gestionDescuento;

import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionDescuento.DescuentoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionDescuento.TipoDeDescuentoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaDetalleDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.AlumnoDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaDetalleDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAcademica.*;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionDescuento.Descuento;
import pe.edu.pucp.SIME.model.gestionDescuento.TipoDeDescuento;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;
import pe.edu.pucp.SIME.model.gestionMatricula.TipoMatricula;

import java.sql.*;

public class DescuentoDAOImpl implements DescuentoDAO {

    public TipoDeDescuento buscarTipoDescuento(int id) throws SQLException{
        TipoDeDescuentoDAO tipoDeDescuentoDAO = new TipoDeDescuentoDAOImpl();
        TipoDeDescuento tipoDeDescuento = tipoDeDescuentoDAO.load(id);
        return tipoDeDescuento;
    }

    public MatriculaDetalle buscarMatricula (int id) throws SQLException{
        MatriculaDetalleDAO matriculaDetalleDAO = new MatriculaDetalleDAOImpl();
        MatriculaDetalle matriculaDetalle = matriculaDetalleDAO.load(id);
        return matriculaDetalle;
    }

    @Override
    public Descuento load(Integer integer) throws SQLException {
        String sql = "select id_descuento, id_matricula_detalle, id_tipo_descuento, porcentaje, motivo, activo " +
                "from SIME_DESCUENTO where id_descuento = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,integer);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Descuento descuento = new Descuento();
                    descuento.setIdDescuento(rs.getInt("id_descuento"));
                    descuento.setPorcentaje(rs.getDouble("porcentaje"));
                    descuento.setMotivo(rs.getString("motivo"));
                    descuento.setActivo(rs.getBoolean("activo"));
                    MatriculaDetalle matricula = buscarMatricula (rs.getInt("id_matricula_detalle"));
                    TipoDeDescuento tipo = buscarTipoDescuento(rs.getInt("id_tipo_descuento"));
                    descuento.setTipoDeDescuento(tipo);
                    descuento.setMatriculaDetalle(matricula);
                    return descuento;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Descuento save(Descuento descuento) throws SQLException {
        String sql = """
                Insert into SIME_DESCUENTO 
                (id_matricula_detalle, id_tipo_descuento, porcentaje, motivo, activo)
                values (?,?,?,?,?)
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1,descuento.getMatriculaDetalle().getIdMatriculaDetalle());
            stmt.setInt(2,descuento.getTipoDeDescuento().getIdTipoDeDescuento());
            stmt.setDouble(3,descuento.getPorcentaje());
            stmt.setString(4,descuento.getMotivo());
            stmt.setBoolean(5,descuento.isActivo());

            int affectedRows = stmt.executeUpdate();

            if(affectedRows > 0){
                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        descuento.setIdDescuento(newId);
                    }
                }
            }
        }
        return descuento;
    }

    @Override
    public Descuento update(Descuento descuento) throws SQLException {
        return null;
    }

    @Override
    public void remove(Descuento descuento) throws SQLException {

    }
}
