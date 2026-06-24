package pe.edu.pucp.SIME.model.DTO;

import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.TipoRelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

import java.util.ArrayList;
import java.util.List;

public class SolicitudMatriculaDTO {

    private Alumno estudiante;

    private List<ApoderadoDetalleDTO> listaApoderados = new ArrayList<>();

    private int idMatriculaCabecera; // Para saber a qué grado, sección y año va

    private int idTipoDescuento; // ID de SIME_TIPO_DESCUENTO (0 si no aplica ninguno)
    private double porcentajeDescuentoAplicar;// Ej: 20.0, 50.0 o 100.0
    private String motivoDescuento;

    public Alumno getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Alumno estudiante) {
        this.estudiante = estudiante;
    }

    public List<ApoderadoDetalleDTO> getListaApoderados() {
        return listaApoderados;
    }

    public void setListaApoderados(List<ApoderadoDetalleDTO> listaApoderados) {
        this.listaApoderados = listaApoderados;
    }

    public int getIdMatriculaCabecera() {
        return idMatriculaCabecera;
    }

    public void setIdMatriculaCabecera(int idMatriculaCabecera) {
        this.idMatriculaCabecera = idMatriculaCabecera;
    }

    public int getIdTipoDescuento() {
        return idTipoDescuento;
    }

    public void setIdTipoDescuento(int idTipoDescuento) {
        this.idTipoDescuento = idTipoDescuento;
    }

    public double getPorcentajeDescuentoAplicar() {
        return porcentajeDescuentoAplicar;
    }

    public void setPorcentajeDescuentoAplicar(double porcentajeDescuentoAplicar) {
        this.porcentajeDescuentoAplicar = porcentajeDescuentoAplicar;
    }

    public String getMotivoDescuento() {
        return motivoDescuento;
    }

    public void setMotivoDescuento(String motivoDescuento) {
        this.motivoDescuento = motivoDescuento;
    }
}

