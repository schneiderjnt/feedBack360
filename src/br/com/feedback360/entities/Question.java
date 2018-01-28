package br.com.feedback360.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="question")
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	private String text;
	private int position;
	
	@JsonIgnore
	@ManyToOne(optional=false)
	private Questionnaire questionnaire;
	
	@OneToMany(mappedBy="question",orphanRemoval=true,cascade=CascadeType.ALL)
	private List<QuestionOptions> options;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}
	public List<QuestionOptions> getOptions() {
		return options;
	}
	public void setOptions(List<QuestionOptions> options) {
		this.options = options;
	}
}
