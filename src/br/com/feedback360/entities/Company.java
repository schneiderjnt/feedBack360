package br.com.feedback360.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="company")
public class Company implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String logo;
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private User admin;
	
	@JsonIgnore
	@OneToMany(mappedBy="company",fetch = FetchType.LAZY)
	private List<Questionnaire> questionaries;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	public List<Questionnaire> getQuestionaries() {
		return questionaries;
	}
	public void setQuestionaries(List<Questionnaire> questionaries) {
		this.questionaries = questionaries;
	}
}
