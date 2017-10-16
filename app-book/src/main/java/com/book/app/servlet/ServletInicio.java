package com.book.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Servlet implementation class Inicio
 */
@WebServlet("/ServletInicio")
public class ServletInicio extends HttpServlet {
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
		//	HttpHelper.deleteSessionUser(request);
			request.getSession(false);
			response.sendRedirect("http://localhost:8080/app-book/index.html"); 
			return; 
		}		
		String option = request.getParameter("option");
		if(option.equals("singout")){	
		//	HttpHelper.deleteSessionUser(request);
			request.getSession(false);
			response.sendRedirect("http://localhost:8080/app-book/index.html");			
		}
		
		List<Collection> listCollections = service.allCollections(user);
			 	
		String collectionActual = null;
		String collectionId = null;
		List<Item> listItems = null;
		if(option.equals("inicio") && listCollections.size()>0){
			collectionId = listCollections.get(0).getId();
			collectionActual = listCollections.get(0).getName();
			listItems = service.allItems(listCollections.get(0));
		}else if(option.equals("show")){			
			collectionId = request.getParameter("colecctionId");
			collectionActual = service.equalCollection(listCollections, collectionId);
			listItems = service.allItems(listCollections.get(Integer.valueOf("0")));
		}else{
			collectionActual = "Sin colecciones";
		}
		
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head>");
	        	out.println("<title>Inicio Colecciones</title>");
	        	// style
	        	out.println(HttpHelper.getStyleInicio());
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<div class='div0'>");
	        	out.println("<h1>Inicio Colecciones 3</h1>");
	        	
	        	out.println("<div class='div12'>");
	        		out.println("<h2>" + user.getName() + "</h2>");	
		        	out.println("<form action= './inicio?option=singout' method='Post'>");
		        	out.println("<input type='submit' value='Sing out'>");
		        	out.println("</form>");
	        	out.println("</div>");
	        				
	        	out.println("<div class='div12'>");
	        		out.println("<div class='div123'>");
	        			out.println("<h2>" + collectionActual + "</h2>");
	        		out.println("</div>");
	        		out.println("<div class='div123'>");
        				out.println("<h3><a href=../editarcoleccion.html>Editar</a></h3>");
        			out.println("</div>");
        			out.println("<div class='div123'>");
    					out.println("<h3><a href=../eliminarcoleccion.html>Eliminar</a></h3>");
    				out.println("</div>");
    				if(collectionId!=null){    					
    					out.println("<div class='div123'>");
    						out.println("<h3><a href=../servlet/insertaritem?option=inicio&&collectionId=" + collectionId + ">Añadir Item</a></h3>");
    					out.println("</div>");
    				}
    			out.println("</div>");
    				
    			out.println("<h3><a href=../servlet/insertarcoleccion?option=inicio>Añadir Colección</a></h3>");     			    			
    			
    			out.println("<div class='divtable'>");     			
    				out.println("<div class='div0'>");
    					out.println("<h3>Colecciones:</h3>");
    					out.println("<table>"); 
    					out.println("<tr><th>ID </th>"
    							+ "<th>Nombre </th>"
    							+ "<th>Descripcion</th>"
    							+ "</tr>");
    	        
    					for (Collection collection : listCollections) {
    						out.println("<tr>"); 
    						out.println("<td>  <a href=../servlet/inicio?option=show&colecctionId=" + collection.getId() + ">" + collection.getId() + "</a> </td>"); 
    						out.println("<td> " + collection.getName() + " </td>"); 
    						out.println("<td> " + collection.getDescription() + " </td>");
    						out.println("</tr> "); 
    					}
    					out.println("</table>");
    	        	out.println("</div>");
    	        	
    				out.println("<div class='div0'>");
    					out.println("<h3>Items:</h3>");
    					out.println("<table>"); 
    					out.println("<tr><th>ID </th>"
    							+ "<th>Nombre </th>"
    							+ "<th>Descripcion</th>"
    							+ "</tr>");
    	        
    					for (Item item : listItems) {
    						out.println("<tr>"); 
    						out.println("<td>" + item.getId() + "</td>"); 
    						out.println("<td>" + item.getName() + "</td>"); 
    						out.println("<td>" + item.getDescription() +  "</td>");
    						out.println("</tr> "); 
    					}
    					out.println("</table>");
    				out.println("</div>");     	        	
    	        out.println("</div>");
  			
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
