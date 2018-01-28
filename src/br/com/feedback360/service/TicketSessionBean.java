package br.com.feedback360.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.feedback360.entities.QuestionOptions;
import br.com.feedback360.entities.Questionnaire;
import br.com.feedback360.entities.Ticket;
import br.com.feedback360.entities.User;
import br.com.feedback360.enums.TicketStatus;
import br.com.feedback360.vo.TicketAnswerVO;
import br.com.feedback360.vo.TicketCreateVO;
import br.com.feedback360.vo.TicketSummaryVO;

@Stateless
public class TicketSessionBean {
	
	@PersistenceContext
	private EntityManager em;

	@EJB
	private QuestionnaireSessionBean questionnaireSessionBean;
	
	public Ticket create(TicketCreateVO vo){
		Ticket ticket = new Ticket();
		
		User from = em.find(User.class, vo.getFromId());
		User target = em.find(User.class, vo.getTargetId());
		Questionnaire questionnaire = em.find(Questionnaire.class, vo.getQuestionnaireId());
		
		ticket.setCreated(new Date());
		ticket.setFrom(from);
		ticket.setTarget(target);
		ticket.setQuestionnaire(questionnaire);
		ticket.setStatus(TicketStatus.CREATED);
		
		em.persist(ticket);
		
		return ticket;
	}
	
	public Ticket answer(TicketAnswerVO vo){
		
		Ticket ticket = em.find(Ticket.class, vo.getId());
		ticket.setStatus(TicketStatus.ANSWERED);
		ticket.setAnswered(new Date());
		ticket.setQuestionOptions(getOptionsFromList(vo.getAnswers()));
		ticket = em.merge(ticket);
		
		int load = ticket.getQuestionOptions().size();
		load = ticket.getTarget().getDepartment().getManager().getId();
		load = ticket.getFrom().getDepartment().getManager().getId();
		
		return ticket;
	}
	
	public List<QuestionOptions> getOptionsFromList(List<Integer> ids){
		return em.createQuery("SELECT opt FROM QuestionOptions opt WHERE opt.id IN :ids", QuestionOptions.class)
			.setParameter("ids", ids)
			.getResultList();
	}
	
	public List<TicketSummaryVO> getTicketsFromCompany(int companyId){
		return em.createQuery("SELECT new br.com.feedback360.vo.TicketSummaryVO(tk.id, tk.target.name, tk.from.name, tk.created, tk.answered, tk.questionnaire.title, tk.status) FROM Ticket tk WHERE tk.questionnaire.company.id = :companyId", TicketSummaryVO.class)
				.setParameter("companyId", companyId)
				.getResultList();
	}
	
	public List<TicketSummaryVO> getOpenTicketsFromUser(int userId){
		return em.createQuery("SELECT new br.com.feedback360.vo.TicketSummaryVO(tk.id, tk.target.name, tk.from.name, tk.created, tk.answered, tk.questionnaire.title, tk.status) FROM Ticket tk WHERE tk.from.id = :userId AND tk.status = :status", TicketSummaryVO.class)
				.setParameter("userId", userId)
				.setParameter("status", TicketStatus.CREATED)
				.getResultList();
	}
	
	public Map<String,Object> getTicketAnswer(int ticketId){
		
		Map<String,Object> mapRetorno = new HashMap<String, Object>();
		
		Ticket ticket = em.find(Ticket.class,ticketId);
		int load = ticket.getQuestionOptions().size();
		load = ticket.getTarget().getDepartment().getManager().getId();
		load = ticket.getFrom().getDepartment().getManager().getId();
		
		int questionnaireId = ticket.getQuestionnaire().getId();
						
		ticket.setQuestionnaireId(questionnaireId);
				
		Questionnaire questionnaire = questionnaireSessionBean.findFull(questionnaireId); 
						
		mapRetorno.put("ticket", ticket);
		mapRetorno.put("questionnaire", questionnaire);
		
		return mapRetorno;
	}
	
	
}
