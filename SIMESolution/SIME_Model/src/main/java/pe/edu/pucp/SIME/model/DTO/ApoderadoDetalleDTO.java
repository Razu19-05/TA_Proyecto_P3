package pe.edu.pucp.SIME.model.DTO;

import pe.edu.pucp.SIME.model.gestionAlumnos.TipoRelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;

public class ApoderadoDetalleDTO
{
    private Persona apoderado;
    private TipoRelacionFamiliar parentesco;
    private boolean contactoEmergencia;
    private String observacionesFamiliares;

    public Persona getApoderado() {
        return apoderado;
    }

    public void setApoderado(Persona apoderado) {
        this.apoderado = apoderado;
    }

    public TipoRelacionFamiliar getParentesco() {
        return parentesco;
    }

    public void setParentesco(TipoRelacionFamiliar parentesco) {
        this.parentesco = parentesco;
    }

    public boolean isContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(boolean contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getObservacionesFamiliares() {
        return observacionesFamiliares;
    }

    public void setObservacionesFamiliares(String observacionesFamiliares) {
        this.observacionesFamiliares = observacionesFamiliares;
    }
}
