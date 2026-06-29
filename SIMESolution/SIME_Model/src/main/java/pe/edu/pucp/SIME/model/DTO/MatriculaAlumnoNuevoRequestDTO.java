package pe.edu.pucp.SIME.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class MatriculaAlumnoNuevoRequestDTO {

    private AlumnoNuevoDTO alumno;
    private List<ApoderadoNuevoDTO> apoderados = new ArrayList<>();

    private int idMatriculaCabecera;

    private String tipoDescuento;
    private String motivoDescuento;
    private double porcentajeDescuento;

    public AlumnoNuevoDTO getAlumno() { return alumno; }
    public void setAlumno(AlumnoNuevoDTO alumno) { this.alumno = alumno; }

    public List<ApoderadoNuevoDTO> getApoderados() { return apoderados; }
    public void setApoderados(List<ApoderadoNuevoDTO> apoderados) { this.apoderados = apoderados; }

    public int getIdMatriculaCabecera() { return idMatriculaCabecera; }
    public void setIdMatriculaCabecera(int idMatriculaCabecera) { this.idMatriculaCabecera = idMatriculaCabecera; }

    public String getTipoDescuento() { return tipoDescuento; }
    public void setTipoDescuento(String tipoDescuento) { this.tipoDescuento = tipoDescuento; }

    public String getMotivoDescuento() { return motivoDescuento; }
    public void setMotivoDescuento(String motivoDescuento) { this.motivoDescuento = motivoDescuento; }

    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public void setPorcentajeDescuento(double porcentajeDescuento) { this.porcentajeDescuento = porcentajeDescuento; }
}