package pe.edu.pucp.SIME.aula.impl.gestionAcademica;

import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.AulaDAO;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.AulaDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;
import pe.edu.pucp.SIME.model.gestionAcademica.TipoAula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaDAOImpl implements AulaDAO {


    @Override
    public Aula load(Integer id) throws SQLException {
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

    @Override
    public Aula save(Aula aula) throws SQLException {
        String sql = """
                insert into SIME_AULA
                (codigo, tipo_aula, capacidad, activo )
                values (?,?,?,?)
                """;
        Connection connection = TransactionContext.getConnection();
        try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstm.setString(1,aula.getCodigo());
            pstm.setString(2, aula.getTipo().name());
            pstm.setInt(3,aula.getCapacidad());
            pstm.setBoolean(4,aula.isActivo());
            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        aula.setIdAula(newId);
                    }
                }
            }
        }
        return aula;
    }

    @Override
    public Aula update(Aula aula) throws SQLException {
        String sql = """
                UPDATE SIME_AULA 
                SET codigo = ?, tipo_aula = ?, capacidad = ?, activo = ? 
                WHERE id_aula = ?
                """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, aula.getCodigo());
            pstm.setString(2, aula.getTipo().name());
            pstm.setInt(3, aula.getCapacidad());
            pstm.setBoolean(4, aula.isActivo());
            pstm.setInt(5, aula.getIdAula());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No se encontró el concepto de aula con ID: " + aula.getIdAula());
            }
        }
        return aula;
    }

    @Override
    public void remove(Aula aula) throws SQLException {
        String sql = """
                UPDATE SIME_AULA 
                SET activo = 0
                WHERE id_aula = ?
                """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, aula.getIdAula());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No se encontró el concepto de aula con ID: " + aula.getIdAula());
            }
        }
    }

    @Override
    public List<AulaDTO> listarAulas(String periodo, String nivel, String grado, String estado, String codigo) throws SQLException {
        List<AulaDTO> lista = new ArrayList<>();

        String sql = """
        SELECT 
            a.id_aula,
            mc.id_matricula_cabecera,

            COALESCE(CAST(pa.anio_escolar AS CHAR), 'Todos') AS periodo,

            CASE 
                WHEN gs.nivel = 'INICIAL' THEN 'Inicial'
                WHEN gs.nivel = 'PRIMARIA' THEN 'Primaria'
                WHEN gs.nivel = 'SECUNDARIA' THEN 'Secundaria'
                ELSE 'Todos'
            END AS nivel,

            COALESCE(gs.grado, 'Todos') AS grado,

            a.codigo,
            a.tipo_aula,
            a.capacidad,

            COALESCE(mc.vacantes_ocupadas, 0) AS vacantes_ocupadas,
            (a.capacidad - COALESCE(mc.vacantes_ocupadas, 0)) AS vacantes_disponibles,

            CASE 
                WHEN a.activo = 1 THEN 'Activa'
                ELSE 'Inactiva'
            END AS estado,

            CASE 
                WHEN a.tipo_aula IN ('COMPUTO', 'CIENCIAS') THEN 1
                ELSE 0
            END AS es_compartida,

            COALESCE(
                MAX(
                    CASE 
                        WHEN ad.es_tutor = 1 THEN 
                            CONCAT(p.nombres, ' ', p.apellido_paterno, ' ', p.apellido_materno)
                    END
                ),
                'Sin asignar'
            ) AS profesor_encargado

        FROM SIME_AULA a

        LEFT JOIN SIME_MATRICULA_CABECERA mc
            ON mc.id_aula = a.id_aula
            AND mc.activo = 1

        LEFT JOIN SIME_PERIODO_ACADEMICO pa
            ON pa.id_periodo_academico = mc.id_periodo_academico

        LEFT JOIN SIME_GRADO_SECCION gs
            ON gs.id_grado_seccion = mc.id_grado_seccion

        LEFT JOIN SIME_ASIGNACION_DOCENTE ad
            ON ad.id_matricula_cabecera = mc.id_matricula_cabecera
            AND ad.activo = 1

        LEFT JOIN SIME_PERSONA p
            ON p.id_persona = ad.id_persona
            AND p.activo = 1

        WHERE
            (
                ? IS NULL
                OR CAST(pa.anio_escolar AS CHAR) = ?
                OR a.tipo_aula IN ('COMPUTO', 'CIENCIAS')
            )
            AND (
                ? IS NULL
                OR gs.nivel = UPPER(?)
                OR a.tipo_aula IN ('COMPUTO', 'CIENCIAS')
            )
            AND (
                ? IS NULL
                OR gs.grado = ?
                OR a.tipo_aula IN ('COMPUTO', 'CIENCIAS')
            )
            AND (
                ? IS NULL
                OR CASE WHEN a.activo = 1 THEN 'Activa' ELSE 'Inactiva' END = ?
            )
            AND (
                ? IS NULL
                OR UPPER(a.codigo) LIKE UPPER(?)
            )

        GROUP BY
            a.id_aula,
            mc.id_matricula_cabecera,
            pa.anio_escolar,
            gs.nivel,
            gs.grado,
            a.codigo,
            a.tipo_aula,
            a.capacidad,
            mc.vacantes_ocupadas,
            a.activo

        ORDER BY
            pa.anio_escolar DESC,
            gs.nivel,
            gs.grado,
            a.codigo
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {

            String pPeriodo = normalizarFiltro(periodo);
            String pNivel = normalizarFiltro(nivel);
            String pGrado = normalizarFiltro(grado);
            String pEstado = normalizarFiltro(estado);
            String pCodigo = normalizarFiltro(codigo);

            pstm.setString(1, pPeriodo);
            pstm.setString(2, pPeriodo);

            pstm.setString(3, pNivel);
            pstm.setString(4, pNivel);

            pstm.setString(5, pGrado);
            pstm.setString(6, pGrado);

            pstm.setString(7, pEstado);
            pstm.setString(8, pEstado);

            pstm.setString(9, pCodigo);
            pstm.setString(10, pCodigo == null ? null : "%" + pCodigo + "%");

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearAulaDTO(rs));
                }
            }
        }

        return lista;
    }

    private String normalizarFiltro(String valor) {
        if (valor == null || valor.isBlank() || valor.equalsIgnoreCase("Todos")) {
            return null;
        }
        return valor;
    }

    private AulaDTO mapearAulaDTO(ResultSet rs) throws SQLException {
        AulaDTO dto = new AulaDTO();

        dto.setIdAula(rs.getInt("id_aula"));

        int idMatriculaCabecera = rs.getInt("id_matricula_cabecera");
        dto.setIdMatriculaCabecera(rs.wasNull() ? null : idMatriculaCabecera);

        dto.setPeriodo(rs.getString("periodo"));
        dto.setNivel(rs.getString("nivel"));
        dto.setGrado(rs.getString("grado"));

        dto.setCodigo(rs.getString("codigo"));
        dto.setTipoAula(formatearTipoAula(rs.getString("tipo_aula")));

        dto.setCapacidad(rs.getInt("capacidad"));
        dto.setVacantesOcupadas(rs.getInt("vacantes_ocupadas"));
        dto.setVacantesDisponibles(rs.getInt("vacantes_disponibles"));

        dto.setEstado(rs.getString("estado"));
        dto.setEsCompartida(rs.getInt("es_compartida") == 1);
        dto.setProfesorEncargado(rs.getString("profesor_encargado"));

        return dto;
    }

    private String formatearTipoAula(String tipoAula) {
        if (tipoAula == null) {
            return "";
        }

        return switch (tipoAula) {
            case "AULA" -> "Aula regular";
            case "COMPUTO" -> "Laboratorio de Cómputo";
            case "CIENCIAS" -> "Laboratorio de Ciencias";
            default -> tipoAula;
        };
    }
    @Override
    public AulaDTO obtenerDetalleAula(int idAula) throws SQLException {
        String sql = """
        SELECT 
            a.id_aula,
            mc.id_matricula_cabecera,

            COALESCE(CAST(pa.anio_escolar AS CHAR), 'Todos') AS periodo,

            CASE 
                WHEN gs.nivel = 'INICIAL' THEN 'Inicial'
                WHEN gs.nivel = 'PRIMARIA' THEN 'Primaria'
                WHEN gs.nivel = 'SECUNDARIA' THEN 'Secundaria'
                ELSE 'Todos'
            END AS nivel,

            COALESCE(gs.grado, 'Todos') AS grado,

            a.codigo,
            a.tipo_aula,
            a.capacidad,

            COALESCE(mc.vacantes_ocupadas, 0) AS vacantes_ocupadas,
            (a.capacidad - COALESCE(mc.vacantes_ocupadas, 0)) AS vacantes_disponibles,

            CASE 
                WHEN a.activo = 1 THEN 'Activa'
                ELSE 'Inactiva'
            END AS estado,

            CASE 
                WHEN a.tipo_aula IN ('COMPUTO', 'CIENCIAS') THEN 1
                ELSE 0
            END AS es_compartida,

            COALESCE(
                MAX(
                    CASE 
                        WHEN ad.es_tutor = 1 THEN 
                            CONCAT(p.nombres, ' ', p.apellido_paterno, ' ', p.apellido_materno)
                    END
                ),
                'Sin asignar'
            ) AS profesor_encargado

        FROM SIME_AULA a

        LEFT JOIN SIME_MATRICULA_CABECERA mc
            ON mc.id_aula = a.id_aula
            AND mc.activo = 1

        LEFT JOIN SIME_PERIODO_ACADEMICO pa
            ON pa.id_periodo_academico = mc.id_periodo_academico

        LEFT JOIN SIME_GRADO_SECCION gs
            ON gs.id_grado_seccion = mc.id_grado_seccion

        LEFT JOIN SIME_ASIGNACION_DOCENTE ad
            ON ad.id_matricula_cabecera = mc.id_matricula_cabecera
            AND ad.activo = 1

        LEFT JOIN SIME_PERSONA p
            ON p.id_persona = ad.id_persona
            AND p.activo = 1

        WHERE a.id_aula = ?

        GROUP BY
            a.id_aula,
            mc.id_matricula_cabecera,
            pa.anio_escolar,
            gs.nivel,
            gs.grado,
            a.codigo,
            a.tipo_aula,
            a.capacidad,
            mc.vacantes_ocupadas,
            a.activo

        ORDER BY pa.anio_escolar DESC
        LIMIT 1
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, idAula);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return mapearAulaDTO(rs);
                }
            }
        }

        return null;
    }

    @Override
    public boolean existeCodigoAula(String codigo, int idIgnorar) throws SQLException {
        String sql = """
        SELECT COUNT(*) AS total
        FROM SIME_AULA
        WHERE UPPER(codigo) = UPPER(?)
          AND id_aula <> ?
    """;

        Connection connection = TransactionContext.getConnection();

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, codigo);
            pstm.setInt(2, idIgnorar);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }

        return false;
    }

    @Override
    public boolean actualizarCapacidad(int idAula, int capacidad) throws SQLException {
        String sqlValidar = """
        SELECT COALESCE(MAX(vacantes_ocupadas), 0) AS ocupadas
        FROM SIME_MATRICULA_CABECERA
        WHERE id_aula = ?
          AND activo = 1
    """;

        String sqlAula = """
        UPDATE SIME_AULA
        SET capacidad = ?
        WHERE id_aula = ?
    """;

        String sqlMatriculaCabecera = """
        UPDATE SIME_MATRICULA_CABECERA
        SET total_vacantes = ?
        WHERE id_aula = ?
          AND activo = 1
    """;

        Connection connection = TransactionContext.getConnection();

        int ocupadas = 0;

        try (PreparedStatement pstmValidar = connection.prepareStatement(sqlValidar)) {
            pstmValidar.setInt(1, idAula);

            try (ResultSet rs = pstmValidar.executeQuery()) {
                if (rs.next()) {
                    ocupadas = rs.getInt("ocupadas");
                }
            }
        }

        if (capacidad < ocupadas) {
            throw new SQLException(
                    "La capacidad no puede ser menor a las vacantes ocupadas. Ocupadas: " + ocupadas
            );
        }

        int filasAula;

        try (PreparedStatement pstmAula = connection.prepareStatement(sqlAula)) {
            pstmAula.setInt(1, capacidad);
            pstmAula.setInt(2, idAula);

            filasAula = pstmAula.executeUpdate();
        }

        try (PreparedStatement pstmMatricula = connection.prepareStatement(sqlMatriculaCabecera)) {
            pstmMatricula.setInt(1, capacidad);
            pstmMatricula.setInt(2, idAula);

            pstmMatricula.executeUpdate();
        }

        return filasAula > 0;
    }
}
