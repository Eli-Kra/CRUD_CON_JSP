package estudiante.controller;

import java.awt.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import estudiante.dao.EstudianteDAO;
import estudiante.model.Estudiante;

public class EstudianteServlet extends HttpServlet {
	EstudianteDAO estudianteDAO = new EstudianteDAO();
	
	
	
	public EstudianteServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String action = request.getParameter("buscarAction");
		System.out.println(action);
		if(action != null) {
			switch (action) {
			case "buscarPorCodigo":
				buscarPorCodigo(request, response);
				break;
			case "buscarPorNombre":
				buscarPorNombre(request, response);
				break;
			case "nuevo":
				nuevoEstudiante(request, response);
				break;
			}	
		}else {
			List<Estudiante> result = estudianteDAO.();
			mostrarListaEstudiantes(request, response, result);
		}		
		
	}
	
	private void buscarPorCodigo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int idEstudiante = Integer.valueOf(req.getParameter("idEstudiante"));
		Estudiante estudiante = null;
		try {
			estudiante = estudianteDAO.obtenerEstudiante(idEstudiante);
		} catch (Exception ex) {
			Logger.getLogger(EstudianteServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
		req.setAttribute("estudiante", estudiante);
		req.setAttribute("action", "actualizar");
		String paginaJSP = "/vista/nuevo-estudiante.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(paginaJSP);
		dispatcher.forward(req, resp);
	}
	
	private void nuevoEstudiante(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("action", "");
		String paginaJSP = "/vista/nuevo-estudiante.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(paginaJSP);
		dispatcher.forward(req, resp);
	}
	private void buscarPorNombre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nombres = req.getParameter("nombreEstudiante");
		List<Estudiante> result = estudianteDAO.buscarPorNombre(nombres);
		mostrarListaEstudiantes(req, resp, result);
	}
	private void mostrarListaEstudiantes(HttpServletRequest req, HttpServletResponse resp, List listaEstudiantes)
			throws ServletException, IOException {
		String paginaJsp = "/vista/lista-estudiantes.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(paginaJsp);
		req.setAttribute("listaEstudiantes", listaEstudiantes);
		dispatcher.forward(req, resp);
	}
	
	 @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        String action = req.getParameter("action");
	        System.out.println("Do Post");
	        System.out.println(action);
	        switch (action) {
	            case "guardar":
	                guardarEstudiante(req, resp);
	                break;
	            case "actualizar":
	                actualizarEstudiante(req, resp);
	                break;            
	            case "eliminar":
	                eliminarEstudiante(req, resp);
	                break;            
	        }

	    }
	 private void guardarEstudiante(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        int codigo = Integer.parseInt(req.getParameter("Codigo"));
	        String nombres = req.getParameter("Nombres");
	        String apellidos = req.getParameter("Apellidos");        
	        String semestre = req.getParameter("semestre");
	      
	        Estudiante estudiante = new Estudiante(codigo, nombres, apellidos, semestre);
	        int idEstudiante = estudianteDAO.guardarEstudiante(estudiante);
	        List<Estudiante> listaEstudiantes = estudianteDAO.obtenerEstudiantes();
	        req.setAttribute("idEstudiante", idEstudiante);
	        String message = "Resgistro creado satisfactoriamente.";
	        req.setAttribute("message", message);
	        mostrarListaEstudiantes(req, resp, listaEstudiantes);
	    }
	 
	 private void actualizarEstudiante(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
		    int codigo = Integer.parseInt(req.getParameter("Codigo"));
		    String nombres=req.getParameter("Nombres");
	        String apellidos = req.getParameter("Apellidos");	       
	        String semestre = req.getParameter("semestre");
	        
	   
	        int idEstudiante = Integer.valueOf(req.getParameter("idEstudiante"));
	        Estudiante estudiante = new Estudiante(codigo, nombres, apellidos, semestre);
	        estudiante.setCodigo(idEstudiante);
	        boolean success = estudianteDAO.actualizarEstudiante(estudiante);
	        String message = null;
	        if (success) {
	            message = "Registro actualizado satisfactoriamente.";
	        }
	        List<Estudiante> listaEstudiantes = estudianteDAO.obtenerEstudiantes();
	        req.setAttribute("idEstudiante", idEstudiante);
	        req.setAttribute("message", message);
	        mostrarListaEstudiantes(req, resp, listaEstudiantes);
	    }  
	 
	 private void eliminarEstudiante(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        int idEstudiante = Integer.valueOf(req.getParameter("idEstudiante"));
	        boolean confirmar = estudianteDAO.eliminarEstudiante(idEstudiante);
	        if (confirmar){
	            String message = "Registro eliminado satisfactoriamente.";
	            req.setAttribute("message", message);
	        }
	        List<Estudiante> listaEstudiantes = estudianteDAO.obtenerEstudiantes();
	        mostrarListaEstudiantes(req, resp, listaEstudiantes);
	    }


}
