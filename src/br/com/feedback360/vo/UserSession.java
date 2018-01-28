package br.com.feedback360.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import br.com.feedback360.entities.Department;
import br.com.feedback360.entities.User;
import br.com.feedback360.enums.UserRoles;

public class UserSession implements Serializable{
	
	private User user;
	private List<UserRoles> role;
	private int companyId;
		
	private List<Integer> managerOf;
	private boolean admin;
	private boolean manager;
	
	
	public UserSession(User user) {
		super();
		this.user = user;
		this.role = new ArrayList<UserRoles>();
		this.role.add(UserRoles.EMPLOYEE);
		this.companyId = user.getDepartment().getCompany().getId();
	}
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<UserRoles> getRole() {
		return role;
	}
	public void setRole(List<UserRoles> role) {
		this.role = role;
	}
	
	public List<Integer> getManagerOf() {
		return managerOf;
	}
	public void setManagerOf(List<Integer> managerOf) {
		this.managerOf = managerOf;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
