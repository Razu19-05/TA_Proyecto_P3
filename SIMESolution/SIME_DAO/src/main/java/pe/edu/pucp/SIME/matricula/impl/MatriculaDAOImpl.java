package pe.edu.pucp.SIME.matricula.impl;

import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.matricula.DAO.MatriculaDAO;
import pe.edu.pucp.SIME.matricula.model.Matricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatriculaDAOImpl implements MatriculaDAO {
    @Override
    public Matricula load(Integer matriculaId) {
        String sql = "SELECT id_matricula, fecha, estado, monto from matricula where id_matriucla = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,matriculaId);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    Matricula matricula = new Matricula();
                    matricula.setIdMatricula(rs.getInt(1));
                    matricula.setFecha(rs.getDate(2));
                    matricula.setEstado(rs.getString(3));
                    matricula.setMonto(rs.getDouble(4));
                    return matricula;
                }
            }
            return null;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Matricula save(Matricula matricula) {
        return null;
    }

    @Override
    public Matricula update(Matricula matricula) {
        return null;
    }

    @Override
    public void remove(Matricula matricula) {
        matricula.setEstado("retirado");
        String sql = "UPDATE apoderado SET estado = ? WHERE id_matricula = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setString(1,matricula.getEstado());
            pstm.setInt(2,matricula.getIdMatricula());
            int res = pstm.executeUpdate();
            if (res == 0){
                System.out.println("No se encontró la matricula con ID: " + matricula.getIdMatricula());
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
