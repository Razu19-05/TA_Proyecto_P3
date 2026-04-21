package pe.edu.pucp.SIME.apoderado.impl;

import pe.edu.pucp.SIME.apoderado.DAO.ApoderadoDAO;
import pe.edu.pucp.SIME.apoderado.DAO.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.apoderado.model.Apoderado;
import pe.edu.pucp.SIME.apoderado.model.RelacionFamiliar;
import pe.edu.pucp.SIME.configuracion.DBManager;
import pe.edu.pucp.SIME.estudiante.model.Alumno;
import pe.edu.pucp.SIME.matricula.model.Matricula;
import pe.edu.pucp.SIME.matricula.model.Periodo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelacionFamiliarDAOImpl implements RelacionFamiliarDAO {
    @Override
    public RelacionFamiliar load(Integer integer) {
        return null;
    }
    //pk compuesta
    public RelacionFamiliar load(int apoderadoID,int alumnoID ) {
        String sql = "SELECT tipo_relacion, contacto_emergencia, observaciones FROM relacion_familiar WHERE id_apoderado = ? AND id_alumno = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setInt(1,apoderadoID);
            pstm.setInt(2,alumnoID);
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next()){
                    RelacionFamiliar rf = new RelacionFamiliar();
                    rf.setTipoRelacion(rs.getString(1));
                    rf.setContactoEmergencia(rs.getString(2));
                    rf.setObservaciones(rs.getString(3));

                    Alumno alu = new Alumno();
                    Apoderado apo = new Apoderado();
                    apo.setIdApoderado(rs.getInt(5));
                    alu.setIdAlumno(rs.getInt(6));

                    rf.setAlumno(alu);
                    rf.setApoderado(apo);

                    return rf;
                }
            }
            return null;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public RelacionFamiliar save(RelacionFamiliar relacionFamiliar) {
        String sql = "INSERT relacion_familiar(tipo_relacion, contacto_emergencia, observaciones, id_apoderado,id_alumno) values (?,?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1,relacionFamiliar.getTipoRelacion());
            pstm.setString(2, relacionFamiliar.getContactoEmergencia());
            pstm.setString(4,relacionFamiliar.getObservaciones());
            pstm.setInt(4,relacionFamiliar.getApoderado().getIdApoderado());
            pstm.setInt(5,relacionFamiliar.getAlumno().getIdAlumno());
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return relacionFamiliar;
    }

    @Override
    public RelacionFamiliar update(RelacionFamiliar rel) {
        String sql = "UPDATE relacion_familiar SET tipo_relacion = ?, contacto_emergencia = ?, observaciones = ? " +
                "WHERE id_apoderado = ? AND id_alumno = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstm = con.prepareStatement(sql)) {

            // Datos a actualizar
            pstm.setString(1, rel.getTipoRelacion());
            pstm.setString(2, rel.getContactoEmergencia());
            pstm.setString(3, rel.getObservaciones());

            // Identificadores (Llave Primaria Compuesta)
            pstm.setInt(4, rel.getApoderado().getIdApoderado());
            pstm.setInt(5, rel.getAlumno().getIdAlumno());

            int rowsAffected = pstm.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No se encontró la relación para actualizar.");
                return null;
            }
            return rel;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(RelacionFamiliar relacionFamiliar) {
        //TODO
    }

    @Override
    public List<RelacionFamiliar> listAll() {
        List<RelacionFamiliar> lista = new ArrayList<>();
        String sql = "select rf.tipo_relacion, rf.contacto_emergencia, rf.observaciones," +
                "a.id_alumno, a.nombres as nomb_alumn, a.apellido_paterno as apell_alum" +
                "apo.id_apoderado, apo.nombres as nomb_apode, apo.apellido_paterno as apell_apo"+
                "from relacion_familiar rf, alumno a,apoderado ap"+
                "where rd.id_alumno = a.id_alumno AND rf.id_apoderado = ap.id_apoderado";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()){

            while(rs.next()){
                RelacionFamiliar rel = new RelacionFamiliar();
                rel.setTipoRelacion((rs.getString("tipo_relacion")));
                rel.setContactoEmergencia(rs.getString("contacto_emergencia"));
                rel.setObservaciones(rs.getString("observaciones"));

                Alumno alu = new Alumno();
                alu.setIdAlumno(rs.getInt("id_alumno"));
                alu.setNombres(rs.getString("nomb_alumn"));
                alu.setApellidoPaterno(rs.getString("apell_alum"));

                Apoderado apo = new Apoderado();
                apo.setIdApoderado(rs.getInt("id_apoderado"));
                apo.setNombres(rs.getString("nomb_apode"));
                apo.setApellidoPaterno(rs.getString("apell_apo"));

                rel.setAlumno(alu);
                rel.setApoderado(apo);

                lista.add(rel);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return lista;
    }
}
