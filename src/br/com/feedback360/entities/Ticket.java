package br.com.feedback360.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.com.feedback360.enums.TicketStatus;

@Entity
@Table(name="ticket")
public class Ticket implements Serializable{
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date answered;
	@ManyToOne(fetch=FetchType.EAGER)
	private User target;
	@ManyToOne(fetch=FetchType.EAGER)
	private User from;
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	private Questionnaire questionnaire;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="ticket_question_options",joinColumns={@JoinColumn(name="Ticket_id")},inverseJoinColumns={@JoinColumn(name="questionOptions_id")})
	private List<QuestionOptions> questionOptions;
	
	@Transient
	private int questionnaireId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public TicketStatus getStatus() {
		return status;
	}
	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getAnswered() {
		return answered;
	}
	public void setAnswered(Date answered) {
		this.answered = answered;
	}

	public User getTarget() {
		return target;
	}
	public void setTarget(User target) {
		this.target = target;
	}

	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public List<QuestionOptions> getQuestionOptions() {
		return questionOptions;
	}
	public void setQuestionOptions(List<QuestionOptions> questionOptions) {
		this.questionOptions = questionOptions;
	}
	
	public int getQuestionnaireId() {
		return questionnaireId;
	}
	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}
}
