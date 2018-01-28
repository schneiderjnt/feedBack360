package br.com.feedback360.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.feedback360.entities.Company;
import br.com.feedback360.entities.Department;
import br.com.feedback360.entities.User;
import br.com.feedback360.vo.CompanyCreateVO;
import br.com.feedback360.vo.DepartmentDetailsVO;

@Stateless
public class CompanySessionBean {
	
	@PersistenceContext
	EntityManager em;
	
	public Company create(CompanyCreateVO createVO){
		
		Company company = createVO.getCompany();
				
		Department dpt = new Department();
		dpt.setCompany(company);
		dpt.setTitle("Indefinido");
		
		User user = createVO.getAdmin();
		user.setDepartment(dpt);
		company.setAdmin(user);
		dpt.setManager(user);
		em.persist(company);
		em.persist(dpt);
		em.persist(user);
				
		return company;
	}
	
	public Company updateCompany(Company company){
		em.merge(company);
		return company;
	}
	
	public Company find(int id){
		return em.find(Company.class, id);
	}
	
	public Department addDepartment(Department department, int companyId){
		Company company = em.find(Company.class, companyId);
		User manager = em.find(User.class, department.getManagerId());
		department.setManager(manager);
		department.setCompany(company);
		em.persist(department);
		return department;
	}
	
	public Department updateDepartment(Department detached, int companyId){
		Department department = em.find(Department.class,detached.getId());		
		department.setTitle(detached.getTitle());
		User manager = em.find(User.class, detached.getManagerId());
		department.setManager(manager);
		department = em.merge(department);
		return department;
	}
		
	public List<Department> getDepartments(int companyId){
		
		List<Department> departmentList = em.createQuery("SELECT dpt FROM Department dpt JOIN FETCH dpt.manager  WHERE dpt.company.id = :companyId", Department.class)
				.setParameter("companyId", companyId)
				.getResultList();
		
		return departmentList;
	}
	
	public List<DepartmentDetailsVO> getDepartmentsDetails(int companyId){
		List<DepartmentDetailsVO> listVO = new ArrayList<DepartmentDetailsVO>();
		List<Department> departments = em.createQuery("SELECT dpt FROM Department dpt WHERE dpt.company.id = :companyId", Department.class)
				.setParameter("companyId", companyId)
				.getResultList();
		
		for(Department dpt :departments){
			DepartmentDetailsVO vo = new DepartmentDetailsVO(dpt.getId(), dpt.getTitle(), dpt.getManager(), dpt.getUsers()==null?0:dpt.getUsers().size());
			listVO.add(vo);
		}
		
		return listVO;
	}
}
