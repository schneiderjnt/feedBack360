package br.com.feedback360.vo;

import java.util.List;

import br.com.feedback360.entities.Questionnaire;

public class QuestionnaireSummaryVO {
	
	private int id;
	private String title;
	private String description;
	private int questionQty;
	
		
	public QuestionnaireSummaryVO(Questionnaire quest) {
		super();
		this.id = quest.getId();
		this.title = quest.getTitle();
		this.description = quest.getDescription();
		this.questionQty = quest.getQuestion() == null?0:quest.getQuestion().size();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getQuestionQty() {
		return questionQty;
	}
	public void setQuestionQty(int questionQty) {
		this.questionQty = questionQty;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
