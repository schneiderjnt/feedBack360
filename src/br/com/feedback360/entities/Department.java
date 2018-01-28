package br.com.feedback360.entities;

import java.io.Serializable;
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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnoreType;


@Entity
@Table(name="department")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Department implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private Company company;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
	private User manager;
	
	@JsonIgnore
	@OneToMany(mappedBy="department",fetch=FetchType.LAZY)
	private List<User> users;
		
	@Transient
	private int managerId;
		
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
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	
	public int getManagerId() {
		if(this.manager!=null)
			this.managerId = this.manager.getId();
		
		return this.managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
   
}
