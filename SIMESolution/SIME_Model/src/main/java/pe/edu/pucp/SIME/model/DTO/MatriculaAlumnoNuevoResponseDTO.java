package pe.edu.pucp.SIME.model.DTO;


import java.util.ArrayList;
import java.util.List;

public class MatriculaAlumnoNuevoResponseDTO {

    private int idAlumno;
    private int idMatriculaDetalle;
    private String mensaje;
    private List<PagoMatriculaDTO> pagos = new ArrayList<>();

    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }

    public int getIdMatriculaDetalle() { return idMatriculaDetalle; }
    public void setIdMatriculaDetalle(int idMatriculaDetalle) { this.idMatriculaDetalle = idMatriculaDetalle; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public List<PagoMatriculaDTO> getPagos() { return pagos; }
    public void setPagos(List<PagoMatriculaDTO> pagos) { this.pagos = pagos; }
}
