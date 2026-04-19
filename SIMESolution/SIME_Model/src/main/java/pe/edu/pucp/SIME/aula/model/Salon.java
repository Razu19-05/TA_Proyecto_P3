package pe.edu.pucp.SIME.aula.model;

import pe.edu.pucp.SIME.personal.model.Profesor;
import java.util.List;

public class Salon {
    private int idSalon;
    private String tipo; /// unico o polidocencia
    private int capacidad;

    private List<Profesor> profesoresAsignados;

    public int getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Profesor> getProfesoresAsignados() {
        return profesoresAsignados;
    }

    public void setProfesoresAsignados(List<Profesor> profesoresAsignados) {
        this.profesoresAsignados = profesoresAsignados;
    }
}
