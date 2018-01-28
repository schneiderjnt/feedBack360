package br.com.feedback360.vo;

import java.util.List;

import br.com.feedback360.entities.User;

public class RatingResumeVO {
	
	private User target;
	private List<RatingQuestionnaireVO> questionnaires;
	
	public User getTarget() {
		return target;
	}
	public void setTarget(User target) {
		this.target = target;
	}
	public List<RatingQuestionnaireVO> getQuestionnaires() {
		return questionnaires;
	}
	public void setQuestionnaires(List<RatingQuestionnaireVO> questionnaires) {
		this.questionnaires = questionnaires;
	}
}
