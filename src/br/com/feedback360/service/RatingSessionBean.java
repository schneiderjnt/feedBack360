package br.com.feedback360.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.feedback360.entities.Question;
import br.com.feedback360.entities.QuestionOptions;
import br.com.feedback360.entities.Questionnaire;
import br.com.feedback360.entities.Ticket;
import br.com.feedback360.entities.User;
import br.com.feedback360.util.StringFormatUtil;
import br.com.feedback360.vo.RatingOptionVO;
import br.com.feedback360.vo.RatingQuestionVO;
import br.com.feedback360.vo.RatingQuestionnaireVO;
import br.com.feedback360.vo.RatingResumeVO;

@Stateless
public class RatingSessionBean {
	
	@PersistenceContext
	private EntityManager em;
	
	
	
	
	public RatingResumeVO getRating(int userId){
		
		RatingResumeVO rrVO = new RatingResumeVO();
		rrVO.setQuestionnaires(new ArrayList<RatingQuestionnaireVO>());
		
		User user = em.find(User.class, userId);
		
		int load = user.getDepartment().getManager().getId();
		System.out.println("manager Id:"+load);
		
		Map<Integer,RatingQuestionnaireVO> mapQuestionnaire = new HashMap<Integer,RatingQuestionnaireVO>();
				
		List<Ticket> ticketList = em.createQuery("SELECT ti FROM Ticket ti  join fetch ti.target  WHERE ti.target = :user", Ticket.class)
				.setParameter("user", user)
				.getResultList();
		
		for(Ticket ticket:ticketList){
			
			load = ticket.getTarget().getDepartment().getManager().getId();
			
			System.out.println("manager Id:"+load);
			
			Questionnaire questionnaire = ticket.getQuestionnaire();
			
			RatingQuestionnaireVO rqVO = mapQuestionnaire.get(questionnaire.getId());
			
			if(rqVO == null){
				rqVO = new RatingQuestionnaireVO();
				rqVO.setDescription(questionnaire.getDescription());
				rqVO.setTitle(questionnaire.getTitle());
				rqVO.setQuestions(new ArrayList<RatingQuestionVO>());
				rqVO.setId(questionnaire.getId());
				for(Question quest:questionnaire.getQuestion()){
					List<RatingOptionVO> roVOList = new ArrayList<RatingOptionVO>();
					for(QuestionOptions qo:quest.getOptions()){
						RatingOptionVO roVO = new RatingOptionVO(qo.getId(),qo.getText(),0);
						roVOList.add(roVO);
					}
					RatingQuestionVO rQuestionVO = new RatingQuestionVO(quest.getId(),quest.getText(),roVOList);
					rqVO.getQuestions().add(rQuestionVO);
					
					Collections.sort(roVOList);
				}
				mapQuestionnaire.put(questionnaire.getId(), rqVO);
			}
			
			rqVO.incrementCount();
			
			for(QuestionOptions questionOption: ticket.getQuestionOptions()){
				rqVO.incrementOption(questionOption.getId());
			}
			
			
			
			
		}
		
		for(Map.Entry<Integer,RatingQuestionnaireVO> entry :mapQuestionnaire.entrySet()){
				
			double total = entry.getValue().getCount();
			
			for(RatingQuestionVO rqVO :entry.getValue().getQuestions()){
				  Collections.sort(rqVO.getOptions());
				  Collections.reverse(rqVO.getOptions());
				  
				  for(RatingOptionVO roVO:rqVO.getOptions()){
					  roVO.setRate(StringFormatUtil.percentFormatter.format(roVO.getCount()/total));
				  }
			}
			
			rrVO.getQuestionnaires().add(entry.getValue());
		}
		
		rrVO.setTarget(user);
		
		return rrVO;
	}
	
}
