package pe.edu.pucp.SIME.aula.impl.gestionDescuento;

import pe.edu.pucp.SIME.aula.DAO.gestionDescuento.DescuentoDAO;
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
        String sql = "select id_tipo_descuento, nombre, descripcion, activo from SIME_TIPO_DESCUENTO where id_tipo_descuento = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    TipoDeDescuento tipo = new TipoDeDescuento();
                    tipo.setIdTipoDeDescuento(rs.getInt("id_tipo_descuento"));
                    tipo.setNombre(rs.getString("nombre"));
                    tipo.setDescripcion(rs.getString("descripcion"));
                    tipo.setActivo(rs.getBoolean("activo"));
                    return tipo;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public Alumno buscarAlumno (int id) throws SQLException{
        String sql = "select id_alumno, dni, nombres, apellido_paterno, apellido_materno, direccion, telefono, correo, " +
                "fecha_nacimiento, alumno_nuevo, activo from SIME_ALUMNO where id_alumno = ? ";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(rs.getInt("id_alumno"));
                    alumno.setDni(rs.getString("dni"));
                    alumno.setNombres(rs.getString("nombres"));
                    alumno.setApellidoPaterno(rs.getString("apellido_paterno"));
                    alumno.setApellidoMaterno(rs.getString("apellido_materno"));
                    alumno.setDireccion(rs.getString("direccion"));
                    alumno.setTelefono(rs.getString("telefono"));
                    alumno.setCorreo(rs.getString("correo"));
                    alumno.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    alumno.setAlumnoNuevo(rs.getBoolean("alumno_nuevo"));
                    alumno.setActivo(rs.getBoolean("activo"));
                    return alumno;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public PeriodoAcademico buscarPeriodo (int id) throws SQLException{
        String sql = "select id_periodo_academico, anio_escolar, fecha_inicio, fecha_fin, activo " +
                "from SIME_PERIODO_ACADEMICO where id_periodo_academico = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    PeriodoAcademico periodo = new PeriodoAcademico();
                    periodo.setIdPeriodoAcademico(rs.getInt("id_periodo_academico"));
                    periodo.setAnioEscolar(rs.getInt("anio_escolar"));
                    periodo.setFechaInicio(rs.getDate("fecha_inicio"));
                    periodo.setFechaFin(rs.getDate("fecha_fin"));
                    periodo.setActivo(rs.getBoolean("activo"));
                    return periodo;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public GradoSeccion buscarGrado (int id) throws SQLException{
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

    public Aula buscarAula (int id) throws SQLException{
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

    public MatriculaCabecera buscarMatriculaCabecera (int id) throws SQLException{
        String sql = "select id_matricula_cabecera, id_periodo_academico, id_grado_seccion, id_aula, " +
                "fecha_inicio_matricula, fecha_fin_matricula, total_vacantes, vacantes_ocupadas, activo " +
                "from SIME_MATRICULA_CABECERA where id_matricula_cabecera = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    MatriculaCabecera matriculaCabecera = new MatriculaCabecera();
                    PeriodoAcademico periodo = buscarPeriodo(rs.getInt("id_periodo_academico"));
                    GradoSeccion grado = buscarGrado(rs.getInt("id_grado_seccion"));
                    Aula aula = buscarAula(rs.getInt("id_aula"));
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
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public MatriculaDetalle buscarMatricula (int id) throws SQLException{
        String sql = "select id_matricula_detalle, id_matricula_cabecera, id_alumno, fecha_matricula, estado, activo " +
                "from SIME_MATRICULA_DETALLE where id_matricula_detalle = ?";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    MatriculaDetalle matricula = new MatriculaDetalle();
                    matricula.setIdMatriculaDetalle(rs.getInt("id_matricula_detalle"));
                    MatriculaCabecera matriculaCabecera = buscarMatriculaCabecera(rs.getInt("id_matricula_cabecera"));
                    Alumno alumno = buscarAlumno(rs.getInt("id_alumno"));
                    matricula.setMatriculaCabecera(matriculaCabecera);
                    matricula.setAlumno(alumno);
                    matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                    String estado = rs.getString("estado");
                    matricula.setEstado(TipoMatricula.valueOf(estado));
                    matricula.setActivo(rs.getBoolean("activo"));
                    return matricula;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
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
