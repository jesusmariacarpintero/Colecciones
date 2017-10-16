package com.book.app.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entities.Collection;
import entities.Item;
import entities.User;

public class HttpHelper {
	private static final String USER_ID = "user_id";
	private static final String USER_EMAIL = "user_email";
	private static final String USER_NAME = "user_name";

	public static String getStyleInicio(){
		return "<style>"
		+".div0 {" 
		+"    border: 1px solid black;"
		+"}"
		+".div12 {" 
		+"    border: 1px solid black;" 
		+"    display: inline-block;"
		+"}"				
		+".div123 {" 
		+"    border: 1px solid black;"
		+"    background-color: #DBE9EE;"
		+"    display: inline-block;"
		+"    width: 150px;"
		+"    margin: 20px;"
		+"}"
		+".divtable {" 
		+"    border: 1px solid black;"
		+"}"
		+"table {" 
		+"    font-family: arial, sans-serif;"
		+"    border-collapse: collapse;"
		+"    width: 100%;"
		+"}"

		+"td, th {"
		+"    border: 1px solid #dddddd;"
		+ "   text-align: left;"
		+ "   padding: 8px;"
		+"}"

		+"tr:nth-child(even) {"
		+"    background-color: #dddddd;"
		+"}"
		+"</style>";
	}	
	
	public static String getStyleTable(){
		return "<style>" 
		+"table {" 
		+"    font-family: arial, sans-serif;"
		+"    border-collapse: collapse;"
		+"    width: 100%;"
		+"}"

		+"td, th {"
		+"    border: 1px solid #dddddd;"
		+ "   text-align: left;"
		+ "   padding: 8px;"
		+"}"

		+"tr:nth-child(even) {"
		+"    background-color: #dddddd;"
		+"}"
		+"</style>"; 
	}
	
	public static void deleteSessionUser(HttpServletRequest request){
		HttpSession session = request.getSession(); 	
		session.setAttribute(USER_EMAIL, null); 
		session.setAttribute(USER_ID, null); 
		session.setAttribute(USER_NAME,null);
		session = request.getSession(false); 
	}
	
	public static void saveSessionUser(HttpServletRequest request, User user){
		HttpSession session = request.getSession(); 		
		session.setAttribute(USER_ID, user.getId()); 
		session.setAttribute(USER_EMAIL, user.getEmail()); 
		session.setAttribute(USER_NAME, user.getName());
		session = request.getSession(true);  	
	}
		
	public static User getSessionUser(HttpServletRequest request){
		HttpSession session = request.getSession(); 		
		String id = (String) session.getAttribute(USER_ID); 
		String email = (String) session.getAttribute(USER_EMAIL); 
		String name = (String) session.getAttribute(USER_NAME); 
		
		User user =null; 
		
		if(name!=null && id!=null && email!=null){	
			user = new User();
			user.setId(id);
			user.setName(name);
			user.setEmail(email); 
		}
		session = request.getSession(true);
		
		return user;  
	}

	public static Collection getParametesCollection(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null; 
	}	
}
