package pe.edu.pucp.SIME.matricula.impl;

import pe.edu.pucp.SIME.aula.model.GradoSeccion;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.estudiante.model.Alumno;
import pe.edu.pucp.SIME.matricula.DAO.MatriculaDAO;
import pe.edu.pucp.SIME.matricula.model.Matricula;
import pe.edu.pucp.SIME.matricula.model.Periodo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAOImpl implements MatriculaDAO {
    @Override
    public Matricula load(Integer matriculaId) {
        String sql = "SELECT id_matricula, fecha, estado, monto , id_alumno ,id_periodo ,id_grado_seccion  from matricula where id_matricula = ?";
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

                    Alumno alu = new Alumno();
                    Periodo per = new Periodo();
                    GradoSeccion gs = new GradoSeccion();
                    alu.setIdAlumno(rs.getInt(5));
                    per.setIdPeriodo(rs.getInt(6));
                    gs.setIdGrado(rs.getInt(7));
                    matricula.setAlumno(alu);
                    matricula.setPeriodo(per);
                    matricula.setGrado(gs);
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
        matricula.setEstado("ACTIVO");
        String sql = "INSERT matricula(fecha,estado,monto, id_alumno, id_periodo, id_grado_seccion ) values (?,?,?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            //se pasa la  fecha un obj sql.Date -> fecha
            pstm.setDate(1,new java.sql.Date(matricula.getFecha().getTime()));
            pstm.setString(2, matricula.getEstado());
            pstm.setDouble(3,matricula.getMonto());
            pstm.setInt(4,matricula.getAlumno().getIdAlumno());
            pstm.setInt(5,matricula.getPeriodo().getIdPeriodo());
            pstm.setInt(6,matricula.getGrado().getIdGrado());

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        matricula.setIdMatricula(newId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matricula;
    }

    @Override
    public Matricula update(Matricula matricula) {
        matricula.setEstado("ACTIVO");
        String sql = "UPDATE matricula SET monto = ? ,estado = ? WHERE id_matricula = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setDouble(1, matricula.getMonto());
            pstm.setString(2,matricula.getEstado());
            pstm.setInt(3,matricula.getIdMatricula());
            int resultado = pstm.executeUpdate();
            if(resultado == 0){
                System.out.println("No se encontró la matricula con ID: " + matricula.getIdMatricula());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matricula;
    }

    @Override
    public void remove(Matricula matricula) {
        matricula.setEstado("RETIRADO");
        String sql = "UPDATE matricula SET estado = ? WHERE id_matricula = ?";
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

    @Override
    public List<Matricula> listAll(){
        List<Matricula> matriculas = null;
        String sql = "SELECT id_matricula, fecha, estado, monto , id_alumno ,id_periodo ,id_grado_seccion from matricula where estado = 'ACTIVO'";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                if(matriculas == null) matriculas = new ArrayList<>();
                Matricula matricula = new Matricula();
                matricula.setIdMatricula(rs.getInt(1));
                matricula.setFecha(rs.getDate(2));
                matricula.setEstado(rs.getString(3));
                matricula.setMonto(rs.getDouble(4));

                Alumno alu = new Alumno();
                Periodo per = new Periodo();
                GradoSeccion gs = new GradoSeccion();
                alu.setIdAlumno(rs.getInt(5));
                per.setIdPeriodo(rs.getInt(6));
                gs.setIdGrado(rs.getInt(7));
                matricula.setAlumno(alu);
                matricula.setPeriodo(per);
                matricula.setGrado(gs);
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matriculas;
    }
}
