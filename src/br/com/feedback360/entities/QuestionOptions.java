package br.com.feedback360.entities;

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
@Table(name="question_options")
public class QuestionOptions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	private String text;
	
	@JsonIgnore
	@ManyToOne(optional=false,cascade=CascadeType.ALL)
	private Question question;

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

	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
}
