package br.com.feedback360.webservices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class BaseRest {
	
	public static final String TYPE_JSON="application/json";
	public static final String SESSION_USER = "userSession";
	
	
	public void addInSession(Object objcet, String mapKey, HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute(mapKey, objcet);
	}
	
	public Object getInSession(String mapKey, HttpServletRequest request){
		HttpSession session = request.getSession();
		return session.getAttribute(mapKey);
	}
	
}
