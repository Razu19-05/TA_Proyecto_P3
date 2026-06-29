package pe.edu.pucp.SIME.model.DTO;

public class ProfesorAulaDTO {

    private int idAsignacionDocente;
    private int idPersona;
    private Integer idMatriculaCabecera;

    private String dni;
    private String nombresCompletos;
    private String tipo;
    private String especialidad;
    private String cargo;
    private String area;
    private String telefono;
    private String correo;

    private boolean esTutor;
    private String observacion;
    private String estado;

    public int getIdAsignacionDocente() {
        return idAsignacionDocente;
    }

    public void setIdAsignacionDocente(int idAsignacionDocente) {
        this.idAsignacionDocente = idAsignacionDocente;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdMatriculaCabecera() {
        return idMatriculaCabecera;
    }

    public void setIdMatriculaCabecera(Integer idMatriculaCabecera) {
        this.idMatriculaCabecera = idMatriculaCabecera;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombresCompletos() {
        return nombresCompletos;
    }

    public void setNombresCompletos(String nombresCompletos) {
        this.nombresCompletos = nombresCompletos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isEsTutor() {
        return esTutor;
    }

    public void setEsTutor(boolean esTutor) {
        this.esTutor = esTutor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}