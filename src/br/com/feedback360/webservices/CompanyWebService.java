package br.com.feedback360.webservices;

import java.util.List;

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

import br.com.feedback360.entities.Company;
import br.com.feedback360.entities.Department;
import br.com.feedback360.service.CompanySessionBean;
import br.com.feedback360.service.QuestionnaireSessionBean;
import br.com.feedback360.service.TicketSessionBean;
import br.com.feedback360.service.UserSessionBean;
import br.com.feedback360.vo.CompanyCreateVO;
import br.com.feedback360.vo.DepartmentDetailsVO;
import br.com.feedback360.vo.QuestionnaireSummaryVO;
import br.com.feedback360.vo.TicketSummaryVO;
import br.com.feedback360.vo.UserLogin;
import br.com.feedback360.vo.UserSession;

@Stateless
@Path("/api/company")
public class CompanyWebService extends BaseRest{
	
	private static final int userId=1;
	
	@EJB
	private CompanySessionBean companySessionBean;
	
	@EJB
	private QuestionnaireSessionBean questionnaireSessionBean;
	
	@EJB
	private TicketSessionBean ticketSessionBean;
	
	@EJB
	private UserSessionBean userSessionBean;
		
	@POST
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response create(CompanyCreateVO companyCreateVO,@Context HttpServletRequest request){
		Company company = companySessionBean.create(companyCreateVO);
		
		UserLogin userLogin = new UserLogin();
		userLogin.setEmail(company.getAdmin().getEmail());
		userLogin.setPassword(company.getAdmin().getPassword());
		
		UserSession userSession = userSessionBean.login(userLogin);
		
		addInSession(userSession, this.SESSION_USER, request);
		
		return Response.status(Status.CREATED).entity(company).build();
	}
	
	@POST
	@Path("/{companyId}/department")
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response updateDepartment(Department department,@PathParam("companyId") int companyId, @Context HttpServletRequest request){
		department = companySessionBean.addDepartment(department, companyId);
		return Response.status(Status.CREATED).entity(department).build();
	}
	
	@PUT
	@Path("/{companyId}/department")
	@Consumes(TYPE_JSON)
	@Produces(TYPE_JSON)
	public Response addDepartment(Department department,@PathParam("companyId") int companyId, @Context HttpServletRequest request){
		department = companySessionBean.updateDepartment(department, companyId);
		return Response.status(Status.OK).entity(department).build();
	}
	
	@GET
	@Path("/{companyId}/department")
	@Produces(TYPE_JSON)
	public Response getDepartment(@PathParam("companyId") int companyId){
		
		List<Department> departments = companySessionBean.getDepartments(companyId);
		
		return Response.ok(departments).build();		
	}
	
	@GET
	@Path("/{companyId}/department/details")
	@Produces(TYPE_JSON)
	public Response getDepartmentFull(@PathParam("companyId") int companyId){
		
		List<DepartmentDetailsVO> departments = companySessionBean.getDepartmentsDetails(companyId);
		
		System.out.println(departments.size());
		
		return Response.ok(departments).build();		
	}
		
	@GET
	@Path("/{companyId}")
	@Produces(TYPE_JSON)
	public Response find(@PathParam("companyId") int companyId){
		Company company = companySessionBean.find(companyId);
		return Response.ok(company).build();
	}
	
	@GET
	@Path("/{companyId}/questionnaire")
	@Produces(TYPE_JSON)
	public Response findQuestionnarie(@PathParam("companyId") int companyId){
		List<QuestionnaireSummaryVO> questionnaries = questionnaireSessionBean.getSummaryFromCompany(companyId);
		return Response.ok(questionnaries).build();
	}
	
	@GET
	@Path("/{companyId}/ticket")
	@Produces(TYPE_JSON)
	public Response findTickets(@PathParam("companyId") int companyId){
		List<TicketSummaryVO> tickets = ticketSessionBean.getTicketsFromCompany(companyId);
		return Response.ok(tickets).build();
	}
}
