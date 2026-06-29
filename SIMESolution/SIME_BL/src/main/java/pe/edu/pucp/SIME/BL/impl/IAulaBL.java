package pe.edu.pucp.SIME.BL.impl;

import pe.edu.pucp.SIME.model.DTO.AulaDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.Aula;

import java.util.List;

public interface IAulaBL {
    List<AulaDTO> listarAulas(String periodo, String nivel, String grado, String estado, String codigo) throws Exception;
    AulaDTO obtenerDetalleAula(int idAula) throws Exception;
    Aula guardarAula(Aula aula) throws Exception;
    Aula actualizarAula(Aula aula) throws Exception;
    void eliminarAula(int idAula) throws Exception;
    boolean actualizarCapacidad(int idAula, int capacidad) throws Exception;
    AulaDTO guardarAulaPorNivel(AulaDTO aulaDTO) throws Exception;

}
