package br.com.feedback360.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.feedback360.entities.Company;
import br.com.feedback360.entities.Question;
import br.com.feedback360.entities.QuestionOptions;
import br.com.feedback360.entities.Questionnaire;
import br.com.feedback360.vo.QuestionnaireSummaryVO;

@Stateless
public class QuestionnaireSessionBean {
	
	@PersistenceContext
	private EntityManager em;
	
	public Questionnaire create(Questionnaire questionnaire){
		Company company = em.find(Company.class, questionnaire.getCompanyId());
		questionnaire.setCompany(company);
		
		for(Question question:questionnaire.getQuestion()){
			question.setQuestionnaire(questionnaire);			
			for(QuestionOptions option:question.getOptions()){
				option.setQuestion(question);
			}
		}
		
		em.persist(questionnaire);
		return questionnaire;
	}
	
	public Questionnaire update(Questionnaire detached){
		Questionnaire attached = em.find(Questionnaire.class, detached.getId());
		
		attached.setDescription(detached.getDescription());		
		attached.setTitle(detached.getTitle());
		
		return attached;
	}
	
	
	public List<QuestionnaireSummaryVO> getSummaryFromCompany(int companyId){
				
		return em.createQuery("SELECT new br.com.feedback360.vo.QuestionnaireSummaryVO(quest) FROM Questionnaire quest WHERE quest.company.id =:companyId",QuestionnaireSummaryVO.class)
				.setParameter("companyId", companyId)
				.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Questionnaire findFull(int id){
				
		Questionnaire questionnaire = em.find(Questionnaire.class, id);
		
		questionnaire.setCompanyId(questionnaire.getCompany().getId());
		
		for(Question quest:questionnaire.getQuestion()){
			int load = quest.getOptions().size();
		}
		
		return questionnaire;
	}
	
}
