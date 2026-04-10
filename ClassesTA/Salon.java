import java.util.List;
import java.util.ArrayList;
class Salon {
    private int idSalon;
    private String tipo; /// unico o polidocencia
    private int capacidad;

    private List<Profesor>profesoresAsignados;

    //SETTERS Y GETTERS
    
    public int getIdSalon() {
        return this.idSalon;
    }

    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return this.capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Profesor> getProfesoresAsignados() {
        return this.profesoresAsignados;
    }

    public void setProfesoresAsignados(List<Profesor> profesoresAsignados) {
        this.profesoresAsignados = profesoresAsignados;
    }
}
