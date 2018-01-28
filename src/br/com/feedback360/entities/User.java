package br.com.feedback360.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="user")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3451518668466737681L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	private String name;
	private String email;
	private String password;
	private String photo;
	private String jobTitle;
	@ManyToOne
	private Department department;
	
	@Transient
	private int departmentId;
	
	
		
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public int getDepartmentId() {
		if(this.department != null)
			return departmentId = this.getDepartment().getId();
		else
			return this.departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	
	
	public void load(User user){
		this.name = user.getName();
		this.email = user.getEmail();
		this.jobTitle = user.getJobTitle();
		this.photo = user.getPhoto();
	}
}
