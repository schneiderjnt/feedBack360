package br.com.feedback360.vo;

import br.com.feedback360.entities.User;

public class DepartmentDetailsVO {
	
	private int id;
	private String title;
	private String managerName;
	private String managerPhoto;
	private int userQty;
	private int managerId;
	
	public DepartmentDetailsVO() {
		super();
	}
	
	public DepartmentDetailsVO(int id, String title, User manager, int userQty) {
		super();
		this.id = id;
		this.title = title;
		this.managerName = manager.getName();
		this.managerPhoto = manager.getPhoto();
		this.userQty = userQty;
		this.managerId = manager.getId();
	}
	
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
	
	public int getUserQty() {
		return userQty;
	}
	public void setUserQty(int userQty) {
		this.userQty = userQty;
	}

	public String getManagerPhoto() {
		return managerPhoto;
	}

	public void setManagerPhoto(String managerPhoto) {
		this.managerPhoto = managerPhoto;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
}
