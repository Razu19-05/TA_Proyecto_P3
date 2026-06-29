package pe.edu.pucp.SIME.aula.impl.gestionAlumnos;

import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.AlumnoNuevoDTO;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO {
    @Override
    public Alumno load(Integer alumnoID) throws SQLException {
        String sql = "select id_alumno, dni, nombres, apellido_paterno, apellido_materno, direccion, telefono, correo, " +
                "fecha_nacimiento, alumno_nuevo, activo from SIME_ALUMNO where id_alumno = ? ";
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,alumnoID);
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
                    alumno.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
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
    @Override
    public Alumno save(Alumno alumno) throws SQLException {
        String sql = """
                INSERT INTO SIME_ALUMNO (dni,nombres, apellido_paterno, apellido_materno, direccion, telefono, correo,
                fecha_nacimiento,alumno_nuevo,activo) values (?,?,?,?,?,?,?,?,?,?)
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstm.setString(1, alumno.getDni());
            pstm.setString(2, alumno.getNombres());
            pstm.setString(3, alumno.getApellidoPaterno());
            pstm.setString(4, alumno.getApellidoMaterno());
            pstm.setString(5,alumno.getDireccion());
            pstm.setString(6,alumno.getTelefono());
            pstm.setString(7,alumno.getCorreo());
            pstm.setDate(8, java.sql.Date.valueOf(alumno.getFechaNacimiento()));
            pstm.setInt(9,1);
            pstm.setInt(10,1);
            int affectedRows = pstm.executeUpdate();
            if(affectedRows > 0){
                try(ResultSet generatedKeys = pstm.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        alumno.setIdAlumno(newId);
                    }
                }
            }
        }
        return alumno;
    }

    @Override
    public Alumno update(Alumno alumno) throws SQLException {
        alumno.setAlumnoNuevo(false);
        String sql = """
                UPDATE SIME_ALUMNO 
                SET dni = ?,
                    nombres=?,
                    apellido_paterno=?,
                    apellido_materno=?,
                    direccion=?,
                    telefono=?,
                    correo=?,
                    fecha_nacimiento=?,
                    alumno_nuevo=?,
                    activo=? WHERE id_alumno=?
                """;
        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setString(1, alumno.getDni());
            pstm.setString(2, alumno.getNombres());
            pstm.setString(3, alumno.getApellidoPaterno());
            pstm.setString(4, alumno.getApellidoMaterno());
            pstm.setString(5, alumno.getDireccion());
            pstm.setString(6, alumno.getTelefono());
            pstm.setString(7, alumno.getCorreo());
            pstm.setObject(8, alumno.getFechaNacimiento());
            pstm.setBoolean(9, alumno.isAlumnoNuevo());
            pstm.setBoolean(10, alumno.isActivo());
            pstm.setInt(11, alumno.getIdAlumno());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el alumno con ID: " + alumno.getIdAlumno());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alumno;
    }

    @Override
    public void remove(Alumno alumno) throws SQLException {
        alumno.setActivo(false);
        String sql = "UPDATE SIME_ALUMNO SET activo = 0 WHERE id_alumno = ?";
        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,alumno.getIdAlumno());
            int resultado = pstm.executeUpdate();
            if (resultado == 0) {
                System.out.println("No se encontró el alumno con ID: " + alumno.getIdAlumno());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Alumno> listarAlumnos() throws SQLException{
        List<Alumno> alumnos = new ArrayList<>();
        String sql = """
                select id_alumno, dni,nombres, apellido_paterno, apellido_materno, direccion, telefono, correo ,
                fecha_nacimiento,alumno_nuevo from SIME_ALUMNO where activo = 1;
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()){

            while(rs.next()){
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt(1));
                alumno.setDni(rs.getString(2));
                alumno.setNombres(rs.getString(3));
                alumno.setApellidoPaterno(rs.getString(4));
                alumno.setApellidoMaterno(rs.getString(5));
                alumno.setDireccion(rs.getString(6));
                alumno.setTelefono(rs.getString(7));
                alumno.setCorreo(rs.getString(8));
                alumno.setFechaNacimiento(rs.getDate(9).toLocalDate());
                alumno.setAlumnoNuevo(rs.getBoolean(10));
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alumnos;
    }

    @Override
    public Alumno buscarPorDni(String dni) throws SQLException {
        String sql = "select id_alumno, dni, nombres, apellido_paterno, apellido_materno, direccion, telefono, correo, " +
                "fecha_nacimiento, alumno_nuevo, activo from SIME_ALUMNO where dni = ? ";

        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,dni);
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
                    alumno.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
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

    @Override
    public boolean existeAlumnoPorDni(String dni) throws SQLException {

        String sql = """
        SELECT COUNT(*) AS total
        FROM SIME_ALUMNO
        WHERE dni = ?
          AND activo = 1
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, dni);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }

        return false;
    }
    @Override
    public int insertarAlumnoNuevo(AlumnoNuevoDTO alumno) throws SQLException {

        String sql = """
        INSERT INTO SIME_ALUMNO
        (
            dni,
            nombres,
            apellido_paterno,
            apellido_materno,
            fecha_nacimiento,
            direccion,
            telefono,
            correo,
            alumno_nuevo,
            activo
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1, 1)
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, alumno.getDni());
            pstm.setString(2, alumno.getNombres());
            pstm.setString(3, alumno.getApellidoPaterno());
            pstm.setString(4, alumno.getApellidoMaterno());

            if (alumno.getFechaNacimiento() == null || alumno.getFechaNacimiento().isBlank()) {
                pstm.setNull(5, Types.DATE);
            } else {
                pstm.setDate(5, Date.valueOf(alumno.getFechaNacimiento()));
            }

            pstm.setString(6, alumno.getDireccion());
            pstm.setString(7, alumno.getTelefono());
            pstm.setString(8, alumno.getCorreo());

            pstm.executeUpdate();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    @Override
    public Alumno buscarPorCriterio(String criterio) throws SQLException {
        String sql = """
        SELECT 
            id_alumno,
            dni,
            nombres,
            apellido_paterno,
            apellido_materno,
            direccion,
            telefono,
            correo,
            fecha_nacimiento,
            alumno_nuevo,
            activo
        FROM SIME_ALUMNO
        WHERE activo = 1
          AND (
                dni LIKE ?
                OR LOWER(nombres) LIKE LOWER(?)
                OR LOWER(apellido_paterno) LIKE LOWER(?)
                OR LOWER(apellido_materno) LIKE LOWER(?)
                OR LOWER(CONCAT(nombres, ' ', apellido_paterno, ' ', apellido_materno)) LIKE LOWER(?)
          )
        LIMIT 1
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String patron = "%" + criterio.trim() + "%";

            stmt.setString(1, patron);
            stmt.setString(2, patron);
            stmt.setString(3, patron);
            stmt.setString(4, patron);
            stmt.setString(5, patron);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Alumno alumno = new Alumno();

                    alumno.setIdAlumno(rs.getInt("id_alumno"));
                    alumno.setDni(rs.getString("dni"));
                    alumno.setNombres(rs.getString("nombres"));
                    alumno.setApellidoPaterno(rs.getString("apellido_paterno"));
                    alumno.setApellidoMaterno(rs.getString("apellido_materno"));
                    alumno.setDireccion(rs.getString("direccion"));
                    alumno.setTelefono(rs.getString("telefono"));
                    alumno.setCorreo(rs.getString("correo"));

                    Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                    if (fechaNacimiento != null) {
                        alumno.setFechaNacimiento(fechaNacimiento.toLocalDate());
                    }

                    alumno.setAlumnoNuevo(rs.getBoolean("alumno_nuevo"));
                    alumno.setActivo(rs.getBoolean("activo"));

                    return alumno;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
