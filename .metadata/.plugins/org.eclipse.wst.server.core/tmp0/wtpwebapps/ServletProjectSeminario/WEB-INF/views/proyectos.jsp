<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,entidades.ProyectoInversionPublica" %>
<%!
private String escaparHtml(Object valor) {
    if (valor == null) {
        return "";
    }
    return valor.toString()
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
}
%>
<%
List<?> errores = (List<?>) request.getAttribute("errores");
List<?> proyectos = (List<?>) request.getAttribute("proyectos");
boolean registrado = "1".equals(request.getParameter("registrado"));
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Proyectos de inversion publica</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <main class="container mt-4 mb-4">
        <h1 class="h3">Proyectos de inversion publica</h1>
        <p>Registro y lista de proyectos</p>

        <% if (registrado) { %>
        <div class="alert alert-success" role="alert">Proyecto registrado correctamente</div>
        <% } %>

        <% if (errores != null && !errores.isEmpty()) { %>
        <div class="alert alert-danger" role="alert">
            <p>Revisa estos datos</p>
            <ul class="mb-0">
                <% for (Object error : errores) { %>
                <li><%= escaparHtml(error) %></li>
                <% } %>
            </ul>
        </div>
        <% } %>

        <h2 class="h5">Registrar proyecto</h2>
        <form action="<%= request.getContextPath() %>/proyectos" method="post">
            <div class="mb-3">
                <label for="nombreProyecto" class="form-label">Nombre del proyecto *</label>
                <input type="text" class="form-control" id="nombreProyecto" name="nombreProyecto"
                       minlength="4" maxlength="60" required
                       value="<%= escaparHtml(request.getParameter("nombreProyecto")) %>">
                <div class="form-text">Escribe entre 4 y 60 caracteres</div>
            </div>

            <div class="mb-3">
                <label for="descripcionTecnica" class="form-label">Descripcion tecnica</label>
                <textarea class="form-control" id="descripcionTecnica" name="descripcionTecnica" rows="3"><%= escaparHtml(request.getParameter("descripcionTecnica")) %></textarea>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="fechaInicio" class="form-label">Fecha de inicio *</label>
                    <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" required
                           value="<%= escaparHtml(request.getParameter("fechaInicio")) %>">
                </div>

                <div class="col-md-6 mb-3">
                    <label for="fechaCulminacion" class="form-label">Fecha de culminacion *</label>
                    <input type="date" class="form-control" id="fechaCulminacion" name="fechaCulminacion" required
                           value="<%= escaparHtml(request.getParameter("fechaCulminacion")) %>">
                </div>
            </div>

            <div class="mb-3">
                <label for="asesorTecnico" class="form-label">Asesor tecnico *</label>
                <input type="text" class="form-control" id="asesorTecnico" name="asesorTecnico" required
                       value="<%= escaparHtml(request.getParameter("asesorTecnico")) %>">
            </div>

            <div class="mb-3">
                <label for="presupuestoAsignado" class="form-label">Presupuesto asignado *</label>
                <div class="input-group">
                    <span class="input-group-text">S/</span>
                    <input type="number" class="form-control" id="presupuestoAsignado" name="presupuestoAsignado"
                           min="0" step="0.01" required
                           value="<%= escaparHtml(request.getParameter("presupuestoAsignado")) %>">
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Registrar nuevoo proyecto</button>
        </form>

        <hr>

        <h2 class="h5">Proyectos registrados (<%= proyectos == null ? 0 : proyectos.size() %>)</h2>
        <% if (proyectos == null || proyectos.isEmpty()) { %>
        <p>No hay proyectos registrados.</p>
        <% } else { %>
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Proyecto</th>
                        <th>Asesor tecnico</th>
                        <th>Fecha de inicio</th>
                        <th>Fecha de culminacion</th>
                        <th>Presupuesto</th>
                        <th>Descripcion tecnica</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Object item : proyectos) {
                        ProyectoInversionPublica proyecto = (ProyectoInversionPublica) item; %>
                    <tr>
                        <td><%= escaparHtml(proyecto.getNombreProyecto()) %></td>
                        
                        <td><%= escaparHtml(proyecto.getAsesorTecnico()) %></td>
                        
                        <td><%= escaparHtml(proyecto.getFechaInicio()) %></td>
                        <td><%= escaparHtml(proyecto.getFechaCulminacion()) %></td>
                        
                        <td>S/ <%= escaparHtml(proyecto.getPresupuestoAsignado()) %></td>
                        <td><%= escaparHtml(proyecto.getDescripcionTecnica()) %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        <% } %>
    </main>
</body>
</html>
