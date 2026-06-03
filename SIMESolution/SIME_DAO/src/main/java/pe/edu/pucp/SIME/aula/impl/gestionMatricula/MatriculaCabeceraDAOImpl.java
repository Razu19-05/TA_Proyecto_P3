package pe.edu.pucp.SIME.aula.impl.gestionMatricula;

import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatriculaCabeceraDAOImpl implements MatriculaCabeceraDAO {
    @Override
    public MatriculaCabecera load(Integer id) throws SQLException {
        String sql = "select id_matricula_cabecera, id_periodo_academico, id_grado_seccion, id_aula, " +
                "fecha_inicio_matricula, fecha_fin_matricula, total_vacantes, vacantes_ocupadas, activo " +
                "from SIME_MATRICULA_CABECERA where id_matricula_cabecera = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    MatriculaCabecera matriculaCabecera = new MatriculaCabecera();
                    PeriodoAcademico periodo = new PeriodoAcademico();
                    periodo.setIdPeriodoAcademico(rs.getInt("id_periodo_academico"));
                    GradoSeccion grado = new GradoSeccion();
                    grado.setIdGradoSeccion(rs.getInt("id_grado_seccion"));
                    Aula aula = new Aula();
                    aula.setIdAula(rs.getInt("id_aula"));
                    matriculaCabecera.setPeriodoAcademico(periodo);
                    matriculaCabecera.setGradoSeccion(grado);
                    matriculaCabecera.setAula(aula);
                    matriculaCabecera.setIdMatriculaCabecera(rs.getInt("id_matricula_cabecera"));
                    matriculaCabecera.setFechaInicioMatricula(rs.getDate("fecha_inicio_matricula"));
                    matriculaCabecera.setFechaFinMatricula(rs.getDate("fecha_fin_matricula"));
                    matriculaCabecera.setTotalVacantes(rs.getInt("total_vacantes"));
                    matriculaCabecera.setVacantesOcupadas(rs.getInt("vacantes_ocupadas"));
                    matriculaCabecera.setActivo(rs.getBoolean("activo"));
                    return matriculaCabecera;
                }
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public MatriculaCabecera save(MatriculaCabecera matriculaCabecera) throws SQLException {
        return null;
    }

    @Override
    public MatriculaCabecera update(MatriculaCabecera matriculaCabecera) throws SQLException {
        return null;
    }

    @Override
    public void remove(MatriculaCabecera matriculaCabecera) throws SQLException {

    }
}
