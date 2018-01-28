package br.com.feedback360.vo;

import br.com.feedback360.entities.Company;
import br.com.feedback360.entities.User;

public class CompanyCreateVO {
	
	private Company company;
	private User admin;
	private String departmentTitle;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	public String getDepartmentTitle() {
		return departmentTitle;
	}
	public void setDepartmentTitle(String departmentTitle) {
		this.departmentTitle = departmentTitle;
	}
}
