package pe.edu.pucp.SIME.aula.DAO.gestionDePersonal;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.DTO.ResumenPersonalDTO;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.sql.SQLException;

public interface PersonaDAO extends BaseDAO<Persona, Integer> {
    Persona buscarPorDni(String dni) throws SQLException;
    ResumenPersonalDTO obtenerEstadisticas() throws SQLException;
}
