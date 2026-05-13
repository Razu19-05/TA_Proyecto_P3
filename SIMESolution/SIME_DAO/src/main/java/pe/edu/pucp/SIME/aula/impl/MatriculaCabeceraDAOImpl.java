package pe.edu.pucp.SIME.aula.impl;

import pe.edu.pucp.SIME.aula.DAO.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.model.*;

import java.sql.*;
import java.util.List;

public class MatriculaCabeceraDAOImpl implements MatriculaCabeceraDAO {
    @Override
    public MatriculaCabecera load(Integer idMatriculaCabecera) {
        String sql = "select id_matricula_cabecera, id_periodo_academico, id_grado_seccion, id_aula, id_persona, " +
                "fecha_inicio_matricula, fecha_fin_matricula, total_vacantes, vacantes_ocupadas, vacantes_libres, activo " +
                "from SIME_MATRICULA_CABECERA where id_matricula_cabecera = ?";

        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,idMatriculaCabecera);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    MatriculaCabecera cabecera = new MatriculaCabecera();
                    cabecera.setIdMatriculaCabecera(rs.getInt(1));
                    PeriodoAcademico periodo = new PeriodoAcademico();
                    periodo.setIdPeriodoAcademico(rs.getInt(2));
                    cabecera.setPeriodo(periodo);
                    GradoSeccion grado = new GradoSeccion();
                    grado.setIdGradoSeccion(rs.getInt(3));
                    cabecera.setGradoSeccion(grado);
                    Aula aula = new Aula();
                    aula.setIdAula(rs.getInt(4));
                    cabecera.setAula(aula);
                    Persona persona = New Persona();
                    persona.setIdPersona(rs.getInt(5));
                    cabecera.setTrabajador(persona);
                    cabecera.setFechaInicioMatricula(rs.getDate(6));
                    cabecera.setFechaFinMatricula(rs.getDate(7));
                    cabecera.setTotalVacantes(rs.getInt(8));
                    cabecera.setVacantesOcupadas(rs.getInt(9));
                    cabecera.setVacantesLibres(rs.getInt(10));
                    cabecera.setActivo(rs.getInt(11));
                }
            }
            return null;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }    }

    @Override
    public MatriculaCabecera save(MatriculaCabecera matriculaCabecera) {
        String sql = "insert into SIME_MATRICULA_CABECERA (id_periodo_academico, id_grado_seccion, id_aula, id_persona," +
                "fecha_inicio_matricula, fecha_fin_matricula, total_vacantes, vacantes_ocupadas, vacantes_libres, activo ) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //Statement.RETURN_GENERATED_KEYS permite recuperar el id que la db genero
            PeriodoAcademico periodo = new PeriodoAcademico();
            GradoSeccion grado = new GradoSeccion();
            Aula aula = new Aula();
            Persona trabajador = new Persona();
            pstm.setInt(1,periodo.getIdPeriodoAcademico());
            pstm.setInt(2,grado.getIdGradoSeccion());
            pstm.setInt(3,aula.getIdAula());
            pstm.setInt(4,trabajador.getIdPersona());
            pstm.setDate(5,new java.sql.Date(matriculaCabecera.getFechaInicioMatricula().getTime()));
            pstm.setDate(6,new java.sql.Date(matriculaCabecera.getFechaFinMatricula().getTime()));
            pstm.setInt(7,matriculaCabecera.getTotalVacantes());
            pstm.setInt(8,matriculaCabecera.getVacantesOcupadas());
            pstm.setInt(9,matriculaCabecera.getVacantesLibres());
            pstm.setInt(10,matriculaCabecera.getActivo());
            matriculaCabecera.setPeriodo(periodo);
            matriculaCabecera.setGradoSeccion(grado);
            matriculaCabecera.setAula(aula);
            matriculaCabecera.setTrabajador(trabajador);

            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        matriculaCabecera.setIdMatriculaCabecera(newId);
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return matriculaCabecera;
    }

    @Override
    public MatriculaCabecera update(MatriculaCabecera matriculaCabecera) {
        String sql ="UPDATE SIME_MATRICULA_CABECERA SET vacantes_ocupadas = ? WHERE id_matricula_cabecera = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1, matriculaCabecera.getVacantesOcupadas());
            pstm.setInt(2,matriculaCabecera.getIdMatriculaCabecera());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el apoderado con ID: " + matriculaCabecera.getIdMatriculaCabecera());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matriculaCabecera;
    }

    @Override
    public void remove(MatriculaCabecera matriculaCabecera) {
        String sql = "UPDATE SIME_MATRICULA_CABECERA SET activo = 0 WHERE id_periodo_academico = ?";
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1,matriculaCabecera.getIdMatriculaCabecera());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                // Opcional: Manejar el caso donde el ID no existe
                System.out.println("No se encontró el alumno con ID: " + matriculaCabecera.getIdMatriculaCabecera());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MatriculaCabecera> listAll() {
        return List.of();
    }
}
