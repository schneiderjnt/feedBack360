package br.com.feedback360.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.feedback360.entities.Company;
import br.com.feedback360.entities.Department;
import br.com.feedback360.entities.User;
import br.com.feedback360.enums.UserRoles;
import br.com.feedback360.vo.UserLogin;
import br.com.feedback360.vo.UserSession;

@Stateless
public class UserSessionBean {
	
	@PersistenceContext
	private EntityManager em;
	
	public User create(User user){
		Department dpt = em.find(Department.class, user.getDepartmentId());
		user.setDepartment(dpt);
		em.persist(user);
		return user;
	}
	
	public User find(int id){
		return em.find(User.class, id);
	}
	
	public User update(User userDetached){
		User user = em.find(User.class, userDetached.getId());
		user.load(userDetached);
		user.setDepartment(em.find(Department.class, userDetached.getDepartmentId()));
		user = em.merge(user);
		int load = user.getDepartment().getManager().getId();
		System.out.println("load:"+load);
		
		return user;
	}
	
	public List<User> getFromCompany(int companyId){
		return em.createQuery("SELECT usr FROM User usr WHERE usr.department.company.id = :companyId", User.class)
				.setParameter("companyId", companyId)
				.getResultList();
	}
	
	public List<User> getFromDepartment(List<Integer> departmentId){
		return em.createQuery("SELECT usr FROM User usr WHERE usr.department.id IN :departmentId", User.class)
				.setParameter("departmentId", departmentId)
				.getResultList();
	}
	
	
	public UserSession login(UserLogin userLogin){
		
		try{
			
			User user = em.createQuery("SELECT usr FROM User usr WHERE usr.email =:email", User.class)
				.setParameter("email", userLogin.getEmail())
				.getSingleResult();
			
			if(user == null)
				return null;
			
			int load = user.getDepartment().getManager().getId();
			System.out.println("Load managerId"+load);
			
			UserSession userSession = new UserSession(user);
			
			try{
				Company company = em.createQuery("SELECT cpm FROM Company cpm WHERE cpm.admin = :admin", Company.class)
					.setParameter("admin", user)
					.getSingleResult();
				
				if(company != null ){
					userSession.setCompanyId(company.getId());
					userSession.getRole().add(UserRoles.ADMIN);
					userSession.setAdmin(true);
				}
			
			}catch(Exception e){
				//e.printStackTrace();
			}
			
			try{
				
				List<Department> departments = em.createQuery("SELECT dpt FROM Department dpt WHERE dpt.manager = :manager", Department.class)
						.setParameter("manager", user)
						.getResultList();
				
				if(departments != null && departments.size()>0){
					
					userSession.getRole().add(UserRoles.MANAGER);
					userSession.setManager(true);
					
					userSession.setManagerOf(new ArrayList<Integer>());
					
					for(Department dpt:departments){
						userSession.getManagerOf().add(dpt.getId());
					}
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return userSession;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}