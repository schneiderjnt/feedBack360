package br.com.feedback360.webservices;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.feedback360.entities.User;
import br.com.feedback360.enums.UserRoles;
import br.com.feedback360.service.RatingSessionBean;
import br.com.feedback360.service.UserSessionBean;
import br.com.feedback360.vo.RatingResumeVO;
import br.com.feedback360.vo.UserLogin;
import br.com.feedback360.vo.UserSession;

@Stateless
@Path("/api/user")
public class UserWebService extends BaseRest {
	
	@EJB
	private UserSessionBean userSessionBean;
	
	@EJB
	private RatingSessionBean ratingSessionBean;
	
	@GET
	@Path("/{id}/rating")
	@Produces(TYPE_JSON)
	public Response getRating(@PathParam("id") int id){
		RatingResumeVO rrvo = ratingSessionBean.getRating(id);
		return Response.ok(rrvo).build();			
	}
	
	@GET
	@Path("/{id}")
	@Produces(TYPE_JSON)
	public Response get(@PathParam("id") int id){
		User user = userSessionBean.find(id);
		return Response.ok(user).build();			
	}
	
	
	@POST
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response create(User user){
		
		userSessionBean.create(user);
		return Response.status(Status.CREATED).entity(user).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response update(User user,@PathParam("id") int id){
		user.setId(id);
		user = userSessionBean.update(user);
		return Response.status(Status.OK).entity(user).build();
	}
	
	@GET
	@Produces(TYPE_JSON)
	public Response getFromCompany(@QueryParam("company") int companyId,@Context HttpServletRequest request){
				
		UserSession userSession = (UserSession) getInSession(SESSION_USER, request);
		
		if(userSession.getRole().contains(UserRoles.ADMIN)){
			List<User> users = userSessionBean.getFromCompany(companyId);
			return Response.ok(users).build();
		}else if(userSession.getRole().contains(UserRoles.MANAGER)){
			List<User> users = userSessionBean.getFromDepartment(userSession.getManagerOf());
			return Response.ok(users).build();
		}else{
			return Response.ok().build();
		}

	}
	
	@GET
	@Produces(TYPE_JSON)
	public Response getFromDepartment(@QueryParam("department") String departmentId){
		
		System.out.println(departmentId);
		
		String[] ids= departmentId.split(",");
		
		List<Integer> idsInt = new ArrayList<Integer>();
		
		for(String id:Arrays.asList(ids)){
			idsInt.add(Integer.valueOf(id));
		}
		
		List<User> users = userSessionBean.getFromDepartment(idsInt);
		return Response.ok(users).build();			
	}
	
	
	@POST
	@Path("/login")
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response login(UserLogin userLogin,@Context HttpServletRequest request ){
		
		UserSession userSession = userSessionBean.login(userLogin);
		
		if(userSession != null){
			addInSession(userSession, SESSION_USER, request);
			return Response.ok(userSession).build();
		}else{
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@GET
	@Path("/login")
	@Produces(TYPE_JSON)
	public Response getLoginUser(@Context HttpServletRequest request){
		
		UserSession userSession = (UserSession) getInSession(SESSION_USER, request);
		
		if(userSession == null)
			return Response.status(Status.UNAUTHORIZED).build();
		else
			return Response.ok(userSession).build();
	}
	
	@GET
	@Path("/logout")
	@Produces(TYPE_JSON)
	public Response getLogout(@Context HttpServletRequest request){
		addInSession(null, SESSION_USER, request);
		URI uri = URI.create("application.html#/home");
		return Response.temporaryRedirect(uri).build();
	}
	
}