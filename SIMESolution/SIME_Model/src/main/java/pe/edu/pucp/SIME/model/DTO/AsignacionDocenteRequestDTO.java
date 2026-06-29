package pe.edu.pucp.SIME.model.DTO;

public class AsignacionDocenteRequestDTO {

    private int idPersona;
    private Integer idAula;
    private Integer idMatriculaCabecera;
    private boolean esTutor;
    private String observacion;

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdAula() {
        return idAula;
    }

    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public Integer getIdMatriculaCabecera() {
        return idMatriculaCabecera;
    }

    public void setIdMatriculaCabecera(Integer idMatriculaCabecera) {
        this.idMatriculaCabecera = idMatriculaCabecera;
    }

    public boolean isEsTutor() {
        return esTutor;
    }

    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
