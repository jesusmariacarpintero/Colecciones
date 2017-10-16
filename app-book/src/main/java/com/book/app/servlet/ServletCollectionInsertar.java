package com.book.app.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.junit.Assert;

import com.book.app.business.InfAppServices;
import com.book.test.tools.MockHelper;

import entities.Collection;
import entities.Image;
import entities.User;

/**
 * Servlet implementation class ServletCollectionInsertar
 */
@WebServlet("/ServletCollectionInsertar")
public class ServletCollectionInsertar extends HttpServlet {
	private static final long serialVersionUID = 1L;       

	/*@EJB
	private AppServices ejbServices;*/
	@Inject
	private InfAppServices service;	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = HttpHelper.getSessionUser(request); 
		if(user==null){
			response.sendRedirect("http://localhost:8080/app-book/index.html"); 
			return; 
		} 
		
		String option = request.getParameter("option");
		String message = null;
		if(option.equals("insertar")){
			Collection collection = new Collection();
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			collection.setName(name);
			collection.setDescription(description);
			try{
				service.addCollection(user.getId(), collection);
				message = "Se ha insertado la colección. Inserte otra colección";			   		
				}catch(Exception e){
					message = "No se ha podido insertar la colección";
				}
		}else{
			message = "Insertar una colección";
		}
		
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head>");
	        	out.println("<title>Insertar Colección</title>");
	        	// style
	        out.println("</head>");
	        out.println("<body>");
	        	out.println("<h1><h1>Insertar Colección</h1></h1>");
	        	out.println("<h2>" + user.getName() + "</h2>");
	        	out.println("<h3>" + message + "</h3>");
	        	out.println("<form action= './insertarcoleccion?option=insertar' method='Post'>");
	        	out.println("Nombre:<br>");
	        	out.println("<input type='text' name='name' value=''>");
	        	out.println("<br>");
	        	out.println("Descripción:<br>");
	        	out.println("<input type='text' name='description' value=''>");
	        	out.println("<br><br>");	        	
	        	out.println("<input type='submit' value='Insertar'>");
	        	out.println("</form>"); 
    			
	        	out.println("<br><br>");
	        	
    			out.println("<h3><a href=./inicio?option=inicio method='Post'>Inicio</a></h3>");    				
	        out.println("</div>");
	        out.println("</body>");
	        out.println("</html>");	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
