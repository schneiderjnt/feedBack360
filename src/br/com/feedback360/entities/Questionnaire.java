package br.com.feedback360.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="questionnaire")
public class Questionnaire {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	private String title;
	private String description;
	
	@JsonIgnore
	@ManyToOne(optional=false,fetch=FetchType.LAZY)
	private Company company;
	
	@OneToMany(mappedBy="questionnaire",fetch=FetchType.EAGER,orphanRemoval=true,cascade=CascadeType.ALL)
	private List<Question> question;
	
	@Transient
	private int companyId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public List<Question> getQuestion() {
		return question;
	}
	public void setQuestion(List<Question> question) {
		this.question = question;
	}
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
