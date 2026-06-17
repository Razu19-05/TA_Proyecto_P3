package pe.edu.pucp.SIME.aula.impl.gestionDePersonal;

import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionDePersonal.TipoPersona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOImpl implements PersonaDAO {

    /*'id_persona', '
    'nombres', '
    'apellido_paterno', '
    'apellido_materno',
    'dni',
    'telefono',
    'correo', '
    'direccion',
    'tipo', 'enum(\'EXTERNO\',\'PROFESOR\',\'ADMINISTRADOR\',\'PERSONAL_SERVICIO\')', 'NO', '', NULL, ''
    'especialidad',
    'cargo',
    'area',
    'activo', 'tinyint(1)', 'YES', '', '1', ''
    */
    private Persona mapearPersona(ResultSet rs) throws SQLException {
        String tipoBD = rs.getString("tipo");
        Persona persona = new Persona();
        persona.setTipo(TipoPersona.valueOf(tipoBD));

        if ("PROFESOR".equals(tipoBD)) {
            persona.setEspecialidad(rs.getString("especialidad"));
        } else if ("ADMINISTRADOR".equals(tipoBD)) {
            persona.setCargo(rs.getString("cargo"));
        } else if ("PERSONAL_SERVICIO".equals(tipoBD)) {
            persona.setArea(rs.getString("area"));
        }
        if (persona != null) {
            persona.setIdPersona(rs.getInt("id_persona"));
            persona.setNombres(rs.getString("nombres"));
            persona.setApellidoPaterno(rs.getString("apellido_paterno"));
            persona.setApellidoMaterno(rs.getString("apellido_materno"));
            persona.setDni(rs.getString("dni"));
            persona.setTelefono(rs.getString("telefono"));
            persona.setCorreo(rs.getString("correo"));
            persona.setDireccion(rs.getString("direccion"));
            persona.setActivo(rs.getBoolean("activo"));
        }

        return persona;
    }

    private void setearDatosEspecificos(PreparedStatement pstmt, Persona persona) throws SQLException {

        if (persona.getTipo().equals("PROFESOR")) {
            pstmt.setString(8, persona.getTipo().name());
            pstmt.setString(9, persona.getEspecialidad());
            pstmt.setNull(10, Types.VARCHAR);
            pstmt.setNull(11, Types.VARCHAR);

        } else if (persona.getTipo().equals("ADMINISTRADOR")) {
            pstmt.setString(8, persona.getTipo().name());
            pstmt.setNull(9, Types.VARCHAR);
            pstmt.setString(10, persona.getCargo());
            pstmt.setNull(11, Types.VARCHAR);
        } else if (persona.getTipo().equals("PERSONAL_SERVICIO")) {
            pstmt.setString(8, persona.getTipo().name());
            pstmt.setNull(9, Types.VARCHAR);
            pstmt.setNull(10, Types.VARCHAR);
            pstmt.setString(11,persona.getArea());
        } else {
            pstmt.setString(8, persona.getTipo().name());
            pstmt.setNull(9, Types.VARCHAR);
            pstmt.setNull(10, Types.VARCHAR);
            pstmt.setNull(11, Types.VARCHAR);
        }
    }

    @Override
    public Persona load(Integer personaId) throws SQLException {
        String sql = """
        SELECT id_persona, 
        nombres, 
        apellido_paterno, 
        apellido_materno, 
        dni, 
        telefono, 
        correo ,
        direccion
        ,tipo ,
        especialidad, 
        cargo, 
        area, 
        activo
        from SIME_PERSONA where id_persona = ?
        """;
        Connection connection = TransactionContext.getConnection();
        try(
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,personaId);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    Persona persona = null;
                    persona = mapearPersona(rs);

                    return persona;
                }
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Persona save(Persona persona) throws SQLException {
        String sql = """
            INSERT INTO SIME_PERSONA
            (nombres, 
            apellido_paterno, 
            apellido_materno, 
            dni, 
            telefono, 
            correo ,
            direccion
            ,tipo ,
            especialidad, 
            cargo, 
            area, 
            activo) values (?,?,?,?,?,?,?,?,?,?,?,?)
                """;

        Connection connection = TransactionContext.getConnection();
        //solo cierra el PreparedStatement
        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Statement.RETURN_GENERATED_KEYS permite recuperar el id que la bd generó
            pstm.setString(1, persona.getNombres());
            pstm.setString(2, persona.getApellidoPaterno());
            pstm.setString(3, persona.getApellidoMaterno());
            pstm.setString(4, persona.getDni());
            pstm.setString(5, persona.getTelefono());
            pstm.setString(6, persona.getCorreo());
            pstm.setString(7, persona.getDireccion());
            setearDatosEspecificos(pstm, persona);
            pstm.setInt(12, 1); // set activo al insertar
            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        persona.setIdPersona(newId);
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persona;
    }

    @Override
    public Persona update(Persona persona) throws SQLException {
        String sql ="UPDATE SIME_PERSONA SET telefono = ?, correo = ? WHERE id_persona = ?";
        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1,persona.getTelefono());
            pstm.setString(2,persona.getCorreo());
            pstm.setInt(3,persona.getIdPersona());
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No se encontró la persona con ID: " + persona.getIdPersona());
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persona;
    }

    @Override
    public void remove(Persona persona) throws SQLException {
        String sql = "UPDATE SIME_PERSONA SET activo = 0 WHERE id_persona = ?";
        Connection connection = TransactionContext.getConnection();
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1,persona.getIdPersona());
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No se encontró la persona con ID: " + persona.getIdPersona());
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Persona buscarPorDni(String dni) throws SQLException{
        String sql = """
        SELECT id_persona, 
        nombres, 
        apellido_paterno, 
        apellido_materno, 
        dni, 
        telefono, 
        correo ,
        direccion
        ,tipo ,
        especialidad, 
        cargo, 
        area, 
        activo
        from SIME_PERSONA where dni = ?
        """;
        Connection connection = TransactionContext.getConnection();
        try(
                PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setString(1,dni);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    Persona persona = null;
                    persona = mapearPersona(rs);
                    return persona;
                }
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public ResumenPersonalDTO obtenerEstadisticas() throws SQLException{
        ResumenPersonalDTO resumen = new ResumenPersonalDTO();
        String sql = "SELECT tipo, COUNT(*) as total FROM SIME_PERSONA WHERE activo = 1 GROUP BY tipo";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int total = rs.getInt("total");

                // Asigna los valores según cómo estén escritos en la BD
                if ("PROFESOR".equals(tipo)) {
                    resumen.setCantidadProfesores(total);
                } else if ("ADMINISTRADOR".equals(tipo)) {
                    resumen.setCantidadAdministrativos(total);
                } else if ("PERSONAL_SERVICIO".equals(tipo)) {
                    resumen.setCantidadServicio(total);
                }
            }
        }
        return resumen;
    }

    @Override
    public List<Persona> listarEmpleados() throws SQLException {
        String sql = """
        SELECT id_persona, 
        nombres, 
        apellido_paterno, 
        apellido_materno, 
        dni, 
        telefono, 
        correo ,
        direccion
        ,tipo ,
        especialidad, 
        cargo, 
        area,
        activo
        from SIME_PERSONA where activo = 1;
        """;
        Connection connection = TransactionContext.getConnection();
        List<Persona> empleados = new ArrayList<>();
        try(
                PreparedStatement pstm = connection.prepareStatement(sql)){

            try(ResultSet rs = pstm.executeQuery()){
                while(rs.next()){
                    Persona persona = null;
                    persona = mapearPersona(rs);
                    boolean isEmpleado = persona.getTipo() == TipoPersona.EXTERNO;
                    if(!isEmpleado){
                        empleados.add(persona);
                    }

                }
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return empleados;
    }
}
