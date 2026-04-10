import java.util.List;

class ProfesorSalon {
    private int idProfesorSalon;
    private Profesor profesor;
    private Salon salon;

    private Periodo periodo;

    //SETTERS Y GETTERS

    public int getIdProfesorSalon() {
        return this.idProfesorSalon;
    }

    public void setIdProfesorSalon(int idProfesorSalon) {
        this.idProfesorSalon = idProfesorSalon;
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Salon getSalon() {
        return this.salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Periodo getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
