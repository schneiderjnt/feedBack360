package br.com.feedback360.vo;

public class TicketCreateVO {
	
	private int id;
	private int questionnaireId;
	private int targetId;
	private int fromId;
	
	public int getQuestionnaireId() {
		return questionnaireId;
	}
	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
