package br.com.feedback360.webservices;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.feedback360.entities.Questionnaire;
import br.com.feedback360.service.QuestionnaireSessionBean;

@Stateless
@Path("/api/questionnaire")
public class QuestionnaireWebService extends BaseRest{
	
	@EJB
	private QuestionnaireSessionBean questionnaireSessionBean;
		
	@POST
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response create(Questionnaire questionnaire,@Context HttpServletRequest request){
		questionnaireSessionBean.create(questionnaire);
		return Response.status(Status.CREATED).entity(questionnaire).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(TYPE_JSON)
	public Response find(@PathParam("id") int id){
		Questionnaire questionnaire = questionnaireSessionBean.findFull(id);
		return Response.ok(questionnaire).build();
	}
	
	@PUT
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response update(Questionnaire questionnaire,@Context HttpServletRequest request){
		questionnaireSessionBean.create(questionnaire);
		return Response.status(Status.CREATED).entity(questionnaire).build();
	}
}
