package pe.edu.pucp.SIME.aula.DAO.gestionMatricula;

import pe.edu.pucp.SIME.aula.DAO.BaseDAO;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;
import pe.edu.pucp.SIME.model.DTO.VacanteMatriculaDTO;

import java.sql.SQLException;
import java.util.List;

public interface MatriculaCabeceraDAO extends BaseDAO<MatriculaCabecera,Integer> {
	MatriculaCabecera obtenerPorGradoSeccionActivo(int idGradoSeccion) throws SQLException;
	boolean existeVacanteDisponible(int idMatriculaCabecera) throws SQLException;
	List<VacanteMatriculaDTO> listarVacantes(String periodo, String nivel, String grado) throws SQLException;
	boolean incrementarVacanteOcupada(int idMatriculaCabecera) throws SQLException;
}
