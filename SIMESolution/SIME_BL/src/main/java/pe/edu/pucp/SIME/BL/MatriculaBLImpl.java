package pe.edu.pucp.SIME.BL;

import pe.edu.pucp.SIME.BL.impl.IMatriculaBL;
import pe.edu.pucp.SIME.aula.DAO.gestionAcademica.GradoSeccionDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.AlumnoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionAlumnos.RelacionFamiliarDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionDePersonal.PersonaDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionDescuento.DescuentoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaCabeceraDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionMatricula.MatriculaDetalleDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionPagos.ConceptoPagoDAO;
import pe.edu.pucp.SIME.aula.DAO.gestionPagos.PagoDAO;
import pe.edu.pucp.SIME.aula.impl.gestionAcademica.GradoSeccionDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.AlumnoDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionAlumnos.RelacionFamiliarDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionDePersonal.PersonaDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionDescuento.DescuentoDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaCabeceraDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionMatricula.MatriculaDetalleDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionPagos.ConceptoPagoDAOImpl;
import pe.edu.pucp.SIME.aula.impl.gestionPagos.PagoDAOImpl;
import pe.edu.pucp.SIME.configuracion.TransactionContext;
import pe.edu.pucp.SIME.model.DTO.ApoderadoDetalleDTO;
import pe.edu.pucp.SIME.model.DTO.MatriculaAlumnoDTO;
import pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionDePersonal.TipoPersona;
import pe.edu.pucp.SIME.model.gestionDescuento.Descuento;
import pe.edu.pucp.SIME.model.gestionDescuento.TipoDeDescuento;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;
import pe.edu.pucp.SIME.model.gestionMatricula.TipoMatricula;
import pe.edu.pucp.SIME.model.gestionPagos.ConceptoPago;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;
import pe.edu.pucp.SIME.model.gestionPagos.TipoEstado;
import pe.edu.pucp.SIME.model.DTO.HistorialMatriculaDTO;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MatriculaBLImpl implements IMatriculaBL {
    private AlumnoDAO alumnoDAO = new AlumnoDAOImpl();
    private PersonaDAO personaDAO = new PersonaDAOImpl();
    private RelacionFamiliarDAO relacionDAO = new RelacionFamiliarDAOImpl();
    private MatriculaCabeceraDAO cabeceraDAO = new MatriculaCabeceraDAOImpl();
    private MatriculaDetalleDAO detalleDAO = new MatriculaDetalleDAOImpl();
    private DescuentoDAO descuentoDAO = new DescuentoDAOImpl();
    private PagoDAO pagoDAO = new PagoDAOImpl();
    private ConceptoPagoDAO conceptoDAO = new ConceptoPagoDAOImpl();
    private GradoSeccionDAO gradoDAO = new GradoSeccionDAOImpl();

    private MatriculaDetalle guardarDetalle(Alumno alumno,MatriculaCabecera cabecera) throws Exception{
        MatriculaDetalle detalle = new MatriculaDetalle();
        detalle.setMatriculaCabecera(cabecera);
        detalle.setAlumno(alumno);
        detalle.setFechaMatricula(new Date(System.currentTimeMillis()));
        detalle.setEstado(TipoMatricula.MATRICULADO);
        detalle.setActivo(true);
        return detalleDAO.save(detalle);
    }
    private String observacion(ConceptoPago conceptoPago){
        String nombre = conceptoPago.getNombre();
        switch (nombre) {
            case "MATRICULA":
                return "Costo de matrícula";
            case "PENSION":
                return "Pensión Mensual";
            case "INSCRIPCION":
                return "Costo de Inscripcion";
            case "EXAMEN_PSICOLOGICO":
                return "Examen Psicologico";
            case "BUZO" :
                return "Buzo Escolar";
            case "UTILES":
                return "Útiles Escolares";
            default: return null;
        }
    }
    private Pago guardarPagos(MatriculaDetalle detalle,int id_concepto, double porcentajeDescuento) throws Exception{
        ConceptoPago concepto = conceptoDAO.load(id_concepto);
        Pago pagoInscripcion = new Pago();
        pagoInscripcion.setMatriculaDetalle(detalle);
        pagoInscripcion.setConceptoPago(concepto);

        double montoDesc = concepto.getMonto()*porcentajeDescuento/100;
        pagoInscripcion.setMontoDescuento(montoDesc);
        pagoInscripcion.setMontoFinal(concepto.getMonto()-montoDesc);
        pagoInscripcion.setFechaEmision(new Date(System.currentTimeMillis()));
        pagoInscripcion.setFechaVencimiento(new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)));
        pagoInscripcion.setEstado(TipoEstado.PENDIENTE);
        pagoInscripcion.setObservacion(observacion(concepto));
        pagoInscripcion.setActivo(true);
        return pagoDAO.save(pagoInscripcion);
    }

    private Alumno registrarAlumno(Alumno alumnoSolicitud) throws Exception{
        Alumno alumnoExistente = alumnoDAO.buscarPorDni(alumnoSolicitud.getDni());
        if (alumnoExistente == null || !alumnoExistente.isActivo()) {
            throw new Exception("El alumno no está registrado en la base de datos o está inactivo.");
        }

        if (alumnoSolicitud.getDireccion() != null && !alumnoSolicitud.getDireccion().isBlank()) {
            alumnoExistente.setDireccion(alumnoSolicitud.getDireccion());
        }
        if (alumnoSolicitud.getTelefono() != null && !alumnoSolicitud.getTelefono().isBlank()) {
            alumnoExistente.setTelefono(alumnoSolicitud.getTelefono());
        }
        if (alumnoSolicitud.getCorreo() != null && !alumnoSolicitud.getCorreo().isBlank()) {
            alumnoExistente.setCorreo(alumnoSolicitud.getCorreo());
        }

        return alumnoDAO.update(alumnoExistente);
    }

    private void validarApoderado(Alumno alumno,SolicitudMatriculaDTO solicitud) throws Exception{
        int apoderadosExistentes = 0;
        if(alumno.getIdAlumno()>0){
            apoderadosExistentes = relacionDAO.contarApoderadosActivos(alumno.getIdAlumno());
        }
        int apoderadosNuevos = solicitud.getListaApoderados() != null ? solicitud.getListaApoderados().size() : 0;
        if ((apoderadosExistentes + apoderadosNuevos) > 3) {
            throw new Exception("Límite excedido: El estudiante solo puede tener un máximo de 3 apoderados. "
                    + "Actualmente tiene " + apoderadosExistentes + " registrados.");
        }
    }

    private RelacionFamiliar registrarRelacionFamiliar(ApoderadoDetalleDTO detalleFamiliar,Alumno alumno) throws Exception{
        Persona apoderado = detalleFamiliar.getApoderado();
        Persona apoderadoBuscar = personaDAO.buscarPorDni(apoderado.getDni());
        if (apoderadoBuscar == null) {
            apoderado.setTipo(TipoPersona.EXTERNO);
            apoderado = personaDAO.save(apoderado);
        } else {
            apoderado.setIdPersona(apoderadoBuscar.getIdPersona());
            apoderado = personaDAO.update(apoderado);
        }
        RelacionFamiliar relacion = new RelacionFamiliar();
        relacion.setAlumno(alumno);
        relacion.setPersona(apoderado);
        relacion.setParentesco(detalleFamiliar.getParentesco());
        relacion.setContactoEmergencia(detalleFamiliar.isContactoEmergencia());
        relacion.setObservaciones(detalleFamiliar.getObservacionesFamiliares());
        relacion.setActivo(true);

        return relacionDAO.save(relacion);
    }

    private Descuento registrarDescuento(MatriculaDetalle detalle,SolicitudMatriculaDTO solicitud) throws Exception{
        if (solicitud.getIdTipoDescuento() > 0) {
            Descuento descuento = new Descuento();
            descuento.setMatriculaDetalle(detalle);
            TipoDeDescuento tipoDesc = new TipoDeDescuento();
            tipoDesc.setIdTipoDeDescuento(solicitud.getIdTipoDescuento());
            descuento.setTipoDeDescuento(tipoDesc);
            descuento.setPorcentaje(solicitud.getPorcentajeDescuentoAplicar());
            descuento.setMotivo(solicitud.getMotivoDescuento());
            descuento.setActivo(true);
            return descuentoDAO.save(descuento);
        }
        return null;
    }
    public void procesarMatriculaCompleta(SolicitudMatriculaDTO solicitud) throws Exception{
        try{
            //abre conexion
            TransactionContext.getConnection();
            //validar vacantes
            MatriculaCabecera cabecera = cabeceraDAO.load(solicitud.getIdMatriculaCabecera());
            if(cabecera == null || !cabecera.isActivo()){
                throw new Exception("El proceso de matrícula no esta activo");
            }
            if (cabecera.getVacantesOcupadas() >= cabecera.getTotalVacantes()) {
                throw new Exception("No hay vacantes disponibles para este grado/sección.");
            }
            //seleccion de alumno
            Alumno alumno = registrarAlumno(solicitud.getEstudiante());
            //gestion de apoderados
            validarApoderado(alumno, solicitud);
            //guardar familiares
            if (solicitud.getListaApoderados() != null) {
                for (ApoderadoDetalleDTO detalleFamiliar : solicitud.getListaApoderados()){
                    RelacionFamiliar rf = registrarRelacionFamiliar(detalleFamiliar, alumno);
                }
            }
            //Crear Matrícula Detalle
            MatriculaDetalle detalle = guardarDetalle(alumno, cabecera);
            // REGISTRAR DESCUENTO Y CALCULAR
            double porcentajeDescuento = 0.0;
            Descuento descuento = registrarDescuento(detalle, solicitud);
            // descuento es null cuando no se aplica descuento (idTipoDescuento <= 0).
            if (descuento != null) {
                porcentajeDescuento = descuento.getPorcentaje();
            }
            //facturacion
            //El descuento se aplica únicamente sobre la MATRICULA.
            //Concepto ID 1 = Matricula
            guardarPagos(detalle,1,porcentajeDescuento);
            //Concepto ID 2 = Pension (sin descuento)
            guardarPagos(detalle,2,0.0);
            //Concepto ID 6 (sin descuento)
            guardarPagos(detalle,6,0.0);
            cabecera.setVacantesOcupadas(cabecera.getVacantesOcupadas() + 1);
            cabeceraDAO.update(cabecera);
            TransactionContext.commit();
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al procesar la matrícula: " + e.getMessage());
        } finally {
            //cerrar connection
            TransactionContext.close();
        }
    }

    @Override
    public int verificarVacantesDisponibles(int idMatriculaCabecera) throws Exception {
        try {
            TransactionContext.getConnection();
            MatriculaCabecera cabecera = cabeceraDAO.load(idMatriculaCabecera);
            if (cabecera == null || !cabecera.isActivo()) {
                throw new Exception("La cabecera de matrícula no existe o no está activa");
            }
            TransactionContext.commit();
            int disponibles = cabecera.getTotalVacantes() - cabecera.getVacantesOcupadas();

            return Math.max(disponibles, 0);
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al verificar vacantes: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public int verificarVacantesPorNivelGrado(String nivel, String grado) throws Exception {
        try {
            // Buscar GradoSeccion por nivel y grado
            TransactionContext.getConnection();
            GradoSeccion gradoSec = gradoDAO.buscarPorNivelYGrado(nivel, grado);
            if (gradoSec == null) {
                throw new Exception("No se encontró el grado/sección para el nivel y grado proporcionados");
            }
            MatriculaCabecera cabecera = cabeceraDAO.obtenerPorGradoSeccionActivo(gradoSec.getIdGradoSeccion());

            int disponibles = cabecera.getTotalVacantes() - cabecera.getVacantesOcupadas();
            TransactionContext.commit();
            return Math.max(disponibles, 0);
        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al verificar vacantes por nivel/grado: " + e.getMessage());
        } finally {
            TransactionContext.close();
        }
    }

    @Override
    public MatriculaDetalle insertarMatriculaDetalle(MatriculaDetalle matriculaDetalle) throws Exception {
        try{
            TransactionContext.getConnection();
            MatriculaDetalle detalle = detalleDAO.save(matriculaDetalle);
            TransactionContext.commit();
            return detalle;
        } catch (Exception e){
            TransactionContext.rollback();
            throw new Exception("Error al insertar alumno");
        } finally{
            TransactionContext.close();
        }

    }

    @Override
    public MatriculaDetalle cargarMatriculaAlumno(int idAlumno) throws Exception {
        return detalleDAO.obtenerPorAlumno(idAlumno);
    }

    @Override
    public List<MatriculaAlumnoDTO> listarMatriculasPorAlumno(int idAlumno) throws Exception {
        List<MatriculaDetalle> detalles = detalleDAO.listarMatriculasPorAlumno(idAlumno);
        List<MatriculaAlumnoDTO> resultado = new ArrayList<>();

        for (MatriculaDetalle detalle : detalles) {
            MatriculaCabecera cabecera = detalle.getMatriculaCabecera();

            MatriculaAlumnoDTO dto = new MatriculaAlumnoDTO();
            dto.setIdMatriculaDetalle(detalle.getIdMatriculaDetalle());
            dto.setFechaMatricula(detalle.getFechaMatricula());
            dto.setEstado(detalle.getEstado().name());
            dto.setActivo(detalle.isActivo());

            if (cabecera != null) {
                dto.setIdMatriculaCabecera(cabecera.getIdMatriculaCabecera());
                dto.setFechaInicio(cabecera.getFechaInicioMatricula());
                dto.setFechaFin(cabecera.getFechaFinMatricula());

                if (cabecera.getPeriodoAcademico() != null) {
                    dto.setPeriodo(String.valueOf(cabecera.getPeriodoAcademico().getAnioEscolar()));
                }
                if (cabecera.getGradoSeccion() != null) {
                    dto.setNivel(cabecera.getGradoSeccion().getTipo().name());
                    dto.setGrado(cabecera.getGradoSeccion().getGrado());
                }
                if (cabecera.getAula() != null) {
                    dto.setAula(cabecera.getAula().getCodigo());
                }
            }

            resultado.add(dto);
        }

        return resultado;
    }

    @Override
    public List<HistorialMatriculaDTO> listarHistorialMatriculas() throws Exception {
        try {
            TransactionContext.getConnection();

            List<HistorialMatriculaDTO> historial =
                    detalleDAO.listarHistorialMatriculas();

            TransactionContext.commit();

            return historial;

        } catch (Exception e) {
            TransactionContext.rollback();
            throw new Exception("Error al listar historial de matrículas: " + e.getMessage());

        } finally {
            TransactionContext.close();
        }
    }
}
