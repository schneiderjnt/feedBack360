package br.com.feedback360.webservices;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.feedback360.entities.Questionnaire;
import br.com.feedback360.entities.Ticket;
import br.com.feedback360.entities.User;
import br.com.feedback360.service.QuestionnaireSessionBean;
import br.com.feedback360.service.TicketSessionBean;
import br.com.feedback360.vo.TicketAnswerVO;
import br.com.feedback360.vo.TicketCreateVO;
import br.com.feedback360.vo.TicketSummaryVO;

@Stateless
@Path("/api/ticket")
public class TicketWebService extends BaseRest{
	
	@EJB
	private TicketSessionBean ticketSessionBean;
	
	@POST
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response create(TicketCreateVO vo){
		
		Ticket ticket = ticketSessionBean.create(vo);
		vo.setId(ticket.getId());
		return Response.status(Status.CREATED).entity(vo).build();
	}
	
	@PUT
	@Path("/{ticketId}")
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response answer(@PathParam("ticketId") int ticketId, TicketAnswerVO vo){
		
		vo.setId(ticketId);
		Ticket ticket = ticketSessionBean.answer(vo);
		return Response.status(Status.ACCEPTED).entity(ticket).build();		
	}
	
	@GET
	@Path("/{ticketId}")
	@Produces(TYPE_JSON)
	public Response getTicketAnswer(@PathParam("ticketId") int ticketId){
		
		Map<String,Object> mapReturn = ticketSessionBean.getTicketAnswer(ticketId);
		return Response.status(Status.OK).entity(mapReturn).build();
	}
	
	@GET
	@Produces(TYPE_JSON)
	public Response getFromCompany(@QueryParam("fromId") int userId){
		
		List<TicketSummaryVO> tickets = ticketSessionBean.getOpenTicketsFromUser(userId);
		return Response.ok(tickets).build();			
	}
	
}
