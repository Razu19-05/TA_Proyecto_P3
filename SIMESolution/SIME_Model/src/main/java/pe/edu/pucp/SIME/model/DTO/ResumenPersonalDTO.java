package pe.edu.pucp.SIME.model.DTO;

public class ResumenPersonalDTO {
    private int cantidadProfesores;
    private int cantidadAdministrativos;
    private int cantidadServicio;

    public int getCantidadProfesores() {
        return cantidadProfesores;
    }

    public void setCantidadProfesores(int cantidadProfesores) {
        this.cantidadProfesores = cantidadProfesores;
    }

    public int getCantidadAdministrativos() {
        return cantidadAdministrativos;
    }

    public void setCantidadAdministrativos(int cantidadAdministrativos) {
        this.cantidadAdministrativos = cantidadAdministrativos;
    }

    public int getCantidadServicio() {
        return cantidadServicio;
    }

    public void setCantidadServicio(int cantidadServicio) {
        this.cantidadServicio = cantidadServicio;
    }
}
