package pe.edu.pucp.SIME.model.DTO;

public class AulaDTO {
    private int idAula;
    private Integer idMatriculaCabecera;
    private String periodo;
    private String nivel;
    private String grado;
    private String codigo;
    private String tipoAula;
    private int capacidad;
    private int vacantesOcupadas;
    private int vacantesDisponibles;
    private String profesorEncargado;
    private String estado;
    private boolean esCompartida;

    public int getIdAula() { return idAula; }
    public void setIdAula(int idAula) { this.idAula = idAula; }

    public Integer getIdMatriculaCabecera() { return idMatriculaCabecera; }
    public void setIdMatriculaCabecera(Integer idMatriculaCabecera) { this.idMatriculaCabecera = idMatriculaCabecera; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTipoAula() { return tipoAula; }
    public void setTipoAula(String tipoAula) { this.tipoAula = tipoAula; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public int getVacantesOcupadas() { return vacantesOcupadas; }
    public void setVacantesOcupadas(int vacantesOcupadas) { this.vacantesOcupadas = vacantesOcupadas; }

    public int getVacantesDisponibles() { return vacantesDisponibles; }
    public void setVacantesDisponibles(int vacantesDisponibles) { this.vacantesDisponibles = vacantesDisponibles; }

    public String getProfesorEncargado() { return profesorEncargado; }
    public void setProfesorEncargado(String profesorEncargado) { this.profesorEncargado = profesorEncargado; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean isEsCompartida() { return esCompartida; }
    public void setEsCompartida(boolean esCompartida) { this.esCompartida = esCompartida; }
}
