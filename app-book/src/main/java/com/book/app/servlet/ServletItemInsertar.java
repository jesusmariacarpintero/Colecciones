package com.book.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.app.business.InfAppServices;

import entities.Collection;
import entities.Item;
import entities.User;

/**
 * Servlet implementation class ServletItemInsertar
 */
@WebServlet("/ServletItemInsertar")
public class ServletItemInsertar extends HttpServlet {
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
		String colecctionId = request.getParameter("collectionId");
		String nameCollection = service.equalCollectionItem(colecctionId);
	//	String nameCollection = "COLECCION";
		String message = null;
		if(option.equals("insertar")){
			Item item = new Item();
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			item.setName(name);
			item.setDescription(description);
			try{
				service.addItem(colecctionId, item, null);
				message = "Se ha insertado el Item. Inserte otro item";			   		
				}catch(Exception e){
					message = "No se ha podido insertar el Item";
				}
		}else{
			message = "Insertar un Item";
		}
		
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head>");
	        	out.println("<title>Insertar Item</title>");
	        	// style
	        out.println("</head>");
	        out.println("<body>");
	        	out.println("<h1><h1>Insertar Item en la colección: " + nameCollection + "</h1></h1>");
	        	out.println("<h2>" + user.getName() + "</h2>");
	        	out.println("<h3>" + message + "</h3>");
	        	out.println("<form action= './insertaritem?option=insertar&&collectionId=" + colecctionId + "' method='Post'>");
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
