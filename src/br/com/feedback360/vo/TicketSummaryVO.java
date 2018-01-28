package br.com.feedback360.vo;

import java.util.Date;

import br.com.feedback360.enums.TicketStatus;

public class TicketSummaryVO {
	
	private int id;
	private String targetName;
	private String fromName;
	private Date create;
	private Date answered;
	private String QuestionnaireTitle;
	private TicketStatus status;
			
	public TicketSummaryVO(int id, String targetName, String fromName,
			Date create, Date answered, String questionnaireTitle,
			TicketStatus status) {
		super();
		this.id = id;
		this.targetName = targetName;
		this.fromName = fromName;
		this.create = create;
		this.answered = answered;
		QuestionnaireTitle = questionnaireTitle;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public Date getCreate() {
		return create;
	}
	public void setCreate(Date create) {
		this.create = create;
	}
	public Date getAnswered() {
		return answered;
	}
	public void setAnswered(Date answered) {
		this.answered = answered;
	}
	public String getQuestionnaireTitle() {
		return QuestionnaireTitle;
	}
	public void setQuestionnaireTitle(String questionnaireTitle) {
		QuestionnaireTitle = questionnaireTitle;
	}
	public TicketStatus getStatus() {
		return status;
	}
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
		
}
