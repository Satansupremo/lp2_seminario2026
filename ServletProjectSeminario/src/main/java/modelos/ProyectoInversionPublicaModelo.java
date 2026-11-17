package modelos;

import java.util.ArrayList;
import java.util.List;

import entidades.ProyectoInversionPublica;

public class ProyectoInversionPublicaModelo {

    private static final List<ProyectoInversionPublica> proyectos = new ArrayList<>();
    private static long siguienteId = 1;

    public List<String> validar(ProyectoInversionPublica proyecto) {
        List<String> errores = new ArrayList<>();

        if (proyecto == null) {
            errores.add("Debe proporcionar los datos del proyecto");
            return errores;
        }

        String nombre = proyecto.getNombreProyecto();
        int longitudNombre = nombre == null ? 0 : nombre.trim().length();
       
        if (longitudNombre < 4 || longitudNombre > 60) {
           
        	
        	errores.add("El nombre del proyecto es requerido y debe tener entre 4 y 60 caracteres");
        }

        if (proyecto.getFechaInicio() == null) {
            
        	errores.add("La fecha de inicio del proyecto es requerida");
        }

        if (proyecto.getFechaCulminacion() == null) {
           
        	errores.add("La fecha de culminacion del proyecto es requerida");
        }

        String asesor = proyecto.getAsesorTecnico();
        if (asesor == null || asesor.trim().isEmpty()) {
            
        	
        	errores.add("El asesor tecnico es requerido");
        }

        if (proyecto.getPresupuestoAsignado() == null) {
            
        	errores.add("El presupuesto asignado es requerido");
        }

        return errores;
    }

    public List<String> registrar(ProyectoInversionPublica proyecto) {
        List<String> errores = validar(proyecto);

        if (errores.isEmpty()) {
           
        	synchronized (proyectos) {
               
        		proyecto.setId(siguienteId);
               
        		siguienteId++;
               
        		proyectos.add(copiarProyecto(proyecto));
            }
        }

        return errores;
    }

    public List<ProyectoInversionPublica> listar() {
        List<ProyectoInversionPublica> resultado = new ArrayList<>();
        
        synchronized (proyectos) {
           
        	for (ProyectoInversionPublica proyecto : proyectos) {
                resultado.add(copiarProyecto(proyecto));
           
        	}
       
        }
        return resultado;
    }

    private ProyectoInversionPublica copiarProyecto(ProyectoInversionPublica proyectoOriginal) {
       
    	ProyectoInversionPublica proyectoCopiado = new ProyectoInversionPublica();
       
        proyectoCopiado.setId(proyectoOriginal.getId());
       
        proyectoCopiado.setNombreProyecto(proyectoOriginal.getNombreProyecto());
        
        
        //este es el unico opcional, el resto esta validado a nivel backend y frontend .
        
        proyectoCopiado.setDescripcionTecnica(proyectoOriginal.getDescripcionTecnica());
        
        
       
        proyectoCopiado.setFechaInicio(proyectoOriginal.getFechaInicio());
        
        proyectoCopiado.setFechaCulminacion(proyectoOriginal.getFechaCulminacion());
        
        proyectoCopiado.setAsesorTecnico(proyectoOriginal.getAsesorTecnico());
        
        proyectoCopiado.setPresupuestoAsignado(proyectoOriginal.getPresupuestoAsignado());
        
        return proyectoCopiado;
    }
}
