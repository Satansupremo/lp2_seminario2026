package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import entidades.ProyectoInversionPublica;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelos.ProyectoInversionPublicaModelo;

@WebServlet("/proyectos")
public class ProyectoInversionPublicaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProyectoInversionPublicaModelo modelo = new ProyectoInversionPublicaModelo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrarPagina(request, response, new ArrayList<>());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        ProyectoInversionPublica proyecto = new ProyectoInversionPublica();
        proyecto.setNombreProyecto(valor(request, "nombreProyecto"));
        
        proyecto.setDescripcionTecnica(valor(request, "descripcionTecnica"));
        
        proyecto.setAsesorTecnico(valor(request, "asesorTecnico"));

        List<String> errores = new ArrayList<>();
        
        proyecto.setFechaInicio(parsearFecha(valor(request, "fechaInicio"), "inicio", errores));
        
        proyecto.setFechaCulminacion(parsearFecha(valor(request, "fechaCulminacion"), "culminacion", errores));
        
        proyecto.setPresupuestoAsignado(parsearPresupuesto(valor(request, "presupuestoAsignado"), errores));

        if (errores.isEmpty()) {
            errores = modelo.registrar(proyecto);
            
        } else {
            errores.addAll(modelo.validar(proyecto));
            
        }

        if (errores.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/proyectos?registrado=1");
            
        } else {
            mostrarPagina(request, response, errores);
            
        }
    }

    
    
    
    // metodos completametarios 
    private String valor(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        
        return valor == null ? null : valor.trim();
    }

    private LocalDate parsearFecha(String valor, String campo, List<String> errores) {
        if (valor == null || valor.isEmpty()) {
            return null;
            
        }
        try {
            return LocalDate.parse(valor);
        } catch (DateTimeParseException exception) {
        	
        	
            errores.add("La fecha de " + campo + " no tiene un formato valido");
            return null;
        }
    }

    private BigDecimal parsearPresupuesto(String valor, List<String> errores) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(valor);
        } catch (NumberFormatException exception) {
            errores.add("El presupuesto debe ser un numero valido");
            return null;
        }
    }

    private void mostrarPagina(HttpServletRequest request, HttpServletResponse response, List<String> errores)
            throws ServletException, IOException {
    	
    	
        request.setAttribute("proyectos", modelo.listar());
        request.setAttribute("errores", errores);
        request.getRequestDispatcher("/WEB-INF/views/proyectos.jsp").forward(request, response);
        
        // p
    }
}
