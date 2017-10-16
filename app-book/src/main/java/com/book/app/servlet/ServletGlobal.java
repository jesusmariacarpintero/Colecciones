package com.book.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.app.business.InfAppServices;

import entities.User;

@WebServlet("/ServletGlobal")
public class ServletGlobal extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Inject
//	@EJB
	private InfAppServices service; 	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		   String option = request.getParameter("option");
		   String email = request.getParameter("email");
		   String mensaje = "Para acceder cliquee en Acceso Usuarios";
			
		   User user = new User();
		   if(option.equals("singin")){				
				try{					
					user = service.signInUser(email);	
					
					HttpHelper.saveSessionUser(request, user);
					response.sendRedirect("http://localhost:8080/app-book/servlet/inicio?option=inicio");
			    }catch(EJBException e){			    	
			    	e.getCausedByException(); 
			    	if(e.getClass().isAssignableFrom(EntityNotFoundException.class)){
			    		//El usuario no existe, responder adecuadamente y/o renviar al signUp
			    		mensaje = "El usuario no existe"; 
			    	}else {
			    		//Erro, compruebe los datos del formulario o intentelo mas tarde
			    		mensaje = "Inténtelo más tarde"; 
			    	}
			    }
				
		   }else if (option.equals("singup")){			   
			    String usuario = request.getParameter("user");
			    user.setName(usuario);
			    user.setEmail(email);
			    
				try{					
					service.signUpUser(user);													   
					HttpHelper.saveSessionUser(request, user);
					response.sendRedirect("http://localhost:8080/app-book/servlet/inicio?option=inicio");;
			    }catch(EJBException e){			    	
			    	e.getCausedByException(); 
			    	if(e.getClass().isAssignableFrom(EntityExistsException.class)){
			    		//El usuario ya existe, responder adecuadamente
			    		mensaje = "El usuario ya existe"; 
			    	}else {
			    		//Erro, compruebe los datos del formulario o intentelo mas tarde
			    		mensaje = "Inténtelo más tarde"; 
			    	} 
			    }		    
		   }else{
			   HttpHelper.deleteSessionUser(request);
		   }
		      		   
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head>");
	        out.println("<title>Problemas</title>");
	        out.println("</head>");
	        out.println("<body>");	       	       
	        out.println("<h1>Han ocurrido errores</h1>");
	        out.println(mensaje);
	        out.println("<p>");    
	        out.println("<a href=../index.html>Acceso Usuario</a>");
	        out.println("<p>");
	        out.println("</body>");
	        out.println("</html>");	        				
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}		
}