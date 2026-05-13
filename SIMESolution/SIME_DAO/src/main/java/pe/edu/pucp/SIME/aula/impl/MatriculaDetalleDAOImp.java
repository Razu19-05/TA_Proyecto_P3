package pe.edu.pucp.SIME.aula.impl;

import pe.edu.pucp.SIME.aula.DAO.MatriculaDetalleDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.model.MatriculaCabecera;
import pe.edu.pucp.SIME.model.MatriculaDetalle;
import pe.edu.pucp.SIME.model.TipoEstado;

import java.sql.*;
import java.util.List;

public class MatriculaDetalleDAOImp implements MatriculaDetalleDAO {
    @Override
    public MatriculaDetalle load(Integer idMatriculaDetalle) {
        String sql = "select id_matricula_detalle, id_matricula_cabecera, id_alumno, fecha_matricula, estado, activo from SIME_MATRICULA_DETALLE where id_matricula_detalle = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,idMatriculaDetalle);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    MatriculaDetalle detalle = new MatriculaDetalle();
                    detalle.setIdMatriculaDetalle(rs.getInt(1));
                    MatriculaCabecera cabecera = new MatriculaCabecera();
                    cabecera.setIdMatriculaCabecera(rs.getInt(2));
                    detalle.setCabecera(cabecera);
                    Alumno alumno = new Alumno();
                    String tipoStr = rs.getString(5);
                    alumno.setIdAlumno(rs.getInt(3));
                    detalle.setAlumno(alumno);
                    detalle.setFechaMatricula(rs.getDate(4));
                    if(tipoStr != null){
                        detalle.setTipoEstado(TipoEstado.valueOf(tipoStr));
                    }
                    detalle.setActivo(rs.getInt(6));
                    return detalle;
                }
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public MatriculaDetalle save(MatriculaDetalle matriculaDetalle) {
        String sql = "insert into SIME_MATRICULA_DETALLE (id_matricula_cabecera,id_alumno,fecha_matricula,estado,activo) VALUES (?,?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //Statement.RETURN_GENERATED_KEYS permite recuperar el id que la db genero
            MatriculaCabecera cabecera = new MatriculaCabecera();
            Alumno alumno = new Alumno();
            pstm.setInt(1,cabecera.getIdMatriculaCabecera());
            matriculaDetalle.setCabecera(cabecera);
            pstm.setInt(2,alumno.getIdAlumno);
            matriculaDetalle.setAlumno(alumno);
            pstm.setDate(3,new java.sql.Date(matriculaDetalle.getFechaMatricula().getTime()));
            pstm.setString(4,matriculaDetalle.getTipoEstado()!= null ? matriculaDetalle.getTipoEstado().name() : null);
            pstm.setInt(5,matriculaDetalle.getActivo());

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        matriculaDetalle.setIdMatriculaDetalle(newId);
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return matriculaDetalle;
    }

    @Override
    public MatriculaDetalle update(MatriculaDetalle matriculaDetalle) {
        String sql ="UPDATE SIME_MATRICULA_DETALLE SET estado = ? WHERE id_matricula_detalle = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setString(1,matriculaDetalle.getTipoEstado()!= null ? matriculaDetalle.getTipoEstado().name() : null);
            pstm.setInt(2,matriculaDetalle.getIdMatriculaDetalle());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el apoderado con ID: " + matriculaDetalle.getIdMatriculaDetalle());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matriculaDetalle;
    }

    @Override
    public void remove(MatriculaDetalle matriculaDetalle) {
        String sql = "UPDATE SIME_MATRICULA_DETALLE SET activo = 0 WHERE id_matricula_detalle = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1,matriculaDetalle.getIdMatriculaDetalle());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el alumno con ID: " + matriculaDetalle.getIdMatriculaDetalle());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MatriculaDetalle> listAll() {
        return List.of();
    }
}
