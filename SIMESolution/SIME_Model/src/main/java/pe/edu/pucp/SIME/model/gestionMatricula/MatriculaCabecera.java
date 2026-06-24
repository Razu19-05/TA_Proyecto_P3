package pe.edu.pucp.SIME.model.gestionMatricula;

import pe.edu.pucp.SIME.model.gestionAcademica.Aula;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;
import pe.edu.pucp.SIME.model.gestionAcademica.PeriodoAcademico;

import java.util.Date;

public class MatriculaCabecera {
    private int idMatriculaCabecera;
    private PeriodoAcademico periodoAcademico;
    private GradoSeccion gradoSeccion;
    private Aula aula;
    private Date fechaInicioMatricula;
    private Date fechaFinMatricula;
    private int totalVacantes;
    private int vacantesOcupadas;
    private boolean activo;

    public int getIdMatriculaCabecera() {
        return idMatriculaCabecera;
    }

    public void setIdMatriculaCabecera(int idMatriculaCabecera) {
        this.idMatriculaCabecera = idMatriculaCabecera;
    }

    public PeriodoAcademico getPeriodoAcademico() {
        return periodoAcademico;
    }

    public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    public GradoSeccion getGradoSeccion() {
        return gradoSeccion;
    }

    public void setGradoSeccion(GradoSeccion gradoSeccion) {
        this.gradoSeccion = gradoSeccion;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Date getFechaInicioMatricula() {
        return fechaInicioMatricula;
    }

    public void setFechaInicioMatricula(Date fechaInicioMatricula) {
        this.fechaInicioMatricula = fechaInicioMatricula;
    }

    public Date getFechaFinMatricula() {
        return fechaFinMatricula;
    }

    public void setFechaFinMatricula(Date fechaFinMatricula) {
        this.fechaFinMatricula = fechaFinMatricula;
    }

    public int getTotalVacantes() {
        return totalVacantes;
    }

    public void setTotalVacantes(int totalVacantes) {
        this.totalVacantes = totalVacantes;
    }

    public int getVacantesOcupadas() {
        return vacantesOcupadas;
    }

    public void setVacantesOcupadas(int vacantesOcupadas) {
        this.vacantesOcupadas = vacantesOcupadas;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
