package pe.edu.pucp.SIME.model.DTO;

import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.TipoRelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

public class SolicitudMatriculaDTO {

    private Alumno estudiante;

    // Paso 3
    private Persona apoderado;
    private TipoRelacionFamiliar parentesco;
    private boolean contactoEmergencia;
    private String observacionesFamiliares;

    // Paso 4
    private int idMatriculaCabecera;
    private int idTipoDescuento; // ID de SIME_TIPO_DESCUENTO (0 si es "Ninguno")
    private double porcentajeDescuentoAplicar; // Ej: 20.0 o 100.0
    private String motivoDescuento;

    public String getMotivoDescuento() {
        return motivoDescuento;
    }

    public void setMotivoDescuento(String motivoDescuento) {
        this.motivoDescuento = motivoDescuento;
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

    public int getIdMatriculaCabecera() {
        return idMatriculaCabecera;
    }

    public void setIdMatriculaCabecera(int idMatriculaCabecera) {
        this.idMatriculaCabecera = idMatriculaCabecera;
    }

    public String getObservacionesFamiliares() {
        return observacionesFamiliares;
    }

    public void setObservacionesFamiliares(String observacionesFamiliares) {
        this.observacionesFamiliares = observacionesFamiliares;
    }

    public boolean isContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(boolean contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public TipoRelacionFamiliar getParentesco() {
        return parentesco;
    }

    public void setParentesco(TipoRelacionFamiliar parentesco) {
        this.parentesco = parentesco;
    }

    public Persona getApoderado() {
        return apoderado;
    }

    public void setApoderado(Persona apoderado) {
        this.apoderado = apoderado;
    }

    public Alumno getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Alumno estudiante) {
        this.estudiante = estudiante;
    }
}
