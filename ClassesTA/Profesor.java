import java.util.List;
import java.util.ArrayList;

class Profesor extends Trabajador{//  tutor inicial, profes de polidocencia 
    private String especialidad;
    private boolean esTutor;

    private List<Salon> salonesAsignados;

    //SETTERS Y GETTERS

    public String getEspecialidad() {
        return this.especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEsTutor() {
        return this.esTutor;
    }

    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    public List<Salon> getSalonesAsignados() {
        return this.salonesAsignados;
    }

    public void setSalonesAsignados(List<Salon> salonesAsignados) {
        this.salonesAsignados = salonesAsignados;
    }
}
