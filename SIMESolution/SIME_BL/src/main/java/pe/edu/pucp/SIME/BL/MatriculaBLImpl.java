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
import pe.edu.pucp.SIME.model.DTO.SolicitudMatriculaDTO;
import pe.edu.pucp.SIME.model.gestionAcademica.GradoSeccion;
import pe.edu.pucp.SIME.model.gestionAlumnos.Alumno;
import pe.edu.pucp.SIME.model.gestionAlumnos.RelacionFamiliar;
import pe.edu.pucp.SIME.model.gestionDePersonal.Persona;
import pe.edu.pucp.SIME.model.gestionDescuento.Descuento;
import pe.edu.pucp.SIME.model.gestionDescuento.TipoDeDescuento;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaCabecera;
import pe.edu.pucp.SIME.model.gestionMatricula.MatriculaDetalle;
import pe.edu.pucp.SIME.model.gestionMatricula.TipoMatricula;
import pe.edu.pucp.SIME.model.gestionPagos.ConceptoPago;
import pe.edu.pucp.SIME.model.gestionPagos.Pago;
import pe.edu.pucp.SIME.model.gestionPagos.TipoEstado;

import java.util.Date;

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
            Alumno alumno = solicitud.getEstudiante();
            Alumno alumnoExistente = alumnoDAO.buscarPorDni(alumno.getDni());
            if (alumnoExistente != null) {
                // Es un alumno regular, actualizamos sus datos
                alumno = alumnoDAO.update(alumno);
            } else {
                // Es un alumno nuevo (INSERT)
                alumno = alumnoDAO.save(alumno);
            }

            //gestion de apoderados
            int apoderadosExistentes = 0;

            if(alumno.getIdAlumno()>0){
                apoderadosExistentes = relacionDAO.contarApoderadosActivos(alumno.getIdAlumno());
            }
            int apoderadosNuevos = solicitud.getListaApoderados() != null ? solicitud.getListaApoderados().size() : 0;

            if ((apoderadosExistentes + apoderadosNuevos) > 3) {
                throw new Exception("Límite excedido: El estudiante solo puede tener un máximo de 3 apoderados. "
                        + "Actualmente tiene " + apoderadosExistentes + " registrados.");
            }

            //guardar familiares
            if (solicitud.getListaApoderados() != null) {
                for (ApoderadoDetalleDTO detalleFamiliar : solicitud.getListaApoderados()){
                    Persona apoderado = detalleFamiliar.getApoderado();
                    apoderado.setActivo(true);

                    if (apoderado.getIdPersona() == 0) {
                        apoderado = personaDAO.save(apoderado);
                    } else {
                        apoderado = personaDAO.update(apoderado);
                    }
                    RelacionFamiliar relacion = new RelacionFamiliar();
                    relacion.setAlumno(alumno);
                    relacion.setPersona(apoderado);
                    relacion.setParentesco(detalleFamiliar.getParentesco());
                    relacion.setContactoEmergencia(detalleFamiliar.isContactoEmergencia());
                    relacion.setObservaciones(detalleFamiliar.getObservacionesFamiliares());
                    relacion.setActivo(true);

                    relacionDAO.save(relacion);
                }
            }

            //Crear Matrícula Detalle

            MatriculaDetalle detalle = new MatriculaDetalle();
            detalle.setMatriculaCabecera(cabecera);
            detalle.setAlumno(alumno);
            detalle.setFechaMatricula(new Date());
            detalle.setEstado(TipoMatricula.MATRICULADO);
            detalle.setActivo(true);
            System.out.println("procesado");
            detalle = detalleDAO.save(detalle);
            System.out.println("procesado");
            // REGISTRAR DESCUENTO Y CALCULAR
            // ==========================================================
            double porcentajeDescuento = 0.0;

            if (solicitud.getIdTipoDescuento() > 0) {
                Descuento descuento = new Descuento();
                descuento.setMatriculaDetalle(detalle);

                TipoDeDescuento tipoDesc = new TipoDeDescuento();
                tipoDesc.setIdTipoDeDescuento(solicitud.getIdTipoDescuento());
                descuento.setTipoDeDescuento(tipoDesc);

                descuento.setPorcentaje(solicitud.getPorcentajeDescuentoAplicar());
                descuento.setMotivo("Beneficio aplicado en proceso de matrícula");
                descuento.setActivo(true);

                descuentoDAO.save(descuento);
                porcentajeDescuento = solicitud.getPorcentajeDescuentoAplicar();
            }

            //facturacion
            ConceptoPago conceptoMatricula = conceptoDAO.load(1);//matricula
            double costoBase = conceptoMatricula.getMonto();

            double montoDescontado = costoBase * (porcentajeDescuento / 100.0);
            double montoFinal = costoBase - montoDescontado;
            Pago pagoMatricula = new Pago();
            pagoMatricula.setMatriculaDetalle(detalle);
            pagoMatricula.setConceptoPago(conceptoMatricula);
            pagoMatricula.setMontoDescuento(montoDescontado);
            pagoMatricula.setMontoFinal(montoFinal);
            pagoMatricula.setFechaEmision(new Date());
            // Generamos vencimiento a 7 días, ejemplo:
            pagoMatricula.setFechaVencimiento(new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)));
            pagoMatricula.setEstado(TipoEstado.PENDIENTE);
            pagoMatricula.setObservacion("Derecho de matrícula anual");
            pagoMatricula.setActivo(true);

            pagoDAO.save(pagoMatricula);

            if (alumno.isAlumnoNuevo()) {
                // Concepto ID 2 = Cuota de Ingreso / Inscripción
                ConceptoPago conceptoInscripcion = conceptoDAO.load(3);
                Pago pagoInscripcion = new Pago();
                pagoInscripcion.setMatriculaDetalle(detalle);
                pagoInscripcion.setConceptoPago(conceptoInscripcion);
                pagoInscripcion.setMontoDescuento(0.0);
                pagoInscripcion.setMontoFinal(conceptoInscripcion.getMonto());
                pagoInscripcion.setFechaEmision(new Date());
                pagoInscripcion.setFechaVencimiento(new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)));
                pagoInscripcion.setEstado(TipoEstado.PENDIENTE);
                pagoInscripcion.setObservacion("Cuota de nuevo ingreso");
                pagoInscripcion.setActivo(true);
                pagoDAO.save(pagoInscripcion);
                //  Concepto ID 4 = Examen psicologico
                ConceptoPago conceptoExamen = conceptoDAO.load(4);
                Pago pagoExamen = new Pago();
                pagoExamen.setMatriculaDetalle(detalle);
                pagoExamen.setConceptoPago(conceptoInscripcion);
                pagoExamen.setMontoDescuento(0.0);
                pagoExamen.setMontoFinal(conceptoInscripcion.getMonto());
                pagoExamen.setFechaEmision(new Date());
                pagoExamen.setFechaVencimiento(new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)));
                pagoExamen.setEstado(TipoEstado.PENDIENTE);
                pagoExamen.setObservacion("Costo de examen psicologico");
                pagoInscripcion.setActivo(true);
                pagoDAO.save(pagoExamen);
            }

            cabecera.setVacantesOcupadas(cabecera.getVacantesOcupadas() + 1);
            cabeceraDAO.update(cabecera);

            //
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
}
