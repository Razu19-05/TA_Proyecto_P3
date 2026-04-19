package pe.edu.pucp.SIME.personal.model;

public class PersonalServicio extends Trabajador{
    private String area; // limpieza,kiosco, porteria

    public String getArea(){
        return this.area;
    }

    public void setArea(String area){
        this.area = area;
    }
}
