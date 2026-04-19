package pe.edu.pucp.SIME.personal.model;

import pe.edu.pucp.SIME.aula.model.Salon;
import java.util.List;

public class Profesor extends Trabajador{
    private String especialidad;
    private boolean esTutor;

    private List<Salon> salonesAsignados;

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEsTutor() {
        return esTutor;
    }

    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    public List<Salon> getSalonesAsignados() {
        return salonesAsignados;
    }

    public void setSalonesAsignados(List<Salon> salonesAsignados) {
        this.salonesAsignados = salonesAsignados;
    }
}
