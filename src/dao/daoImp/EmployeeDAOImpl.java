package dao.daoImp;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.EmployeeDAO;
import model.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

	static final Logger logger = Logger.getLogger(EmployeeDAOImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	 public boolean saveEmployee(String empname,String emp_password,String emailid,Date dob,Date doj,String contact_no,String address,int updateEmpID)
	 {
		boolean issave=false;
		Session session = sessionFactory.getCurrentSession();
		logger.info("EmployeeName:"+empname+" Passwrod "+emp_password+" Email : "
				+emailid+"Date Of Birth :"+dob+"Date Of Joining: "+doj+"Contact No : "+contact_no+"Address"+address+
				"updateid :: "+updateEmpID );
				
		int isActive =0;
		Employee employee=null;
		try{
	        if(updateEmpID>0){
	        	employee = (Employee) sessionFactory.getCurrentSession().get(Employee.class, updateEmpID);
	        }else{
	        	employee = new Employee();
	        }
	        
	        employee.setAddress(address);
	        employee.setContact_no(contact_no);
	        employee.setDob(dob);
	        employee.setDoj(doj);
	        employee.setEmailid(emailid);
	        employee.setEmp_password(emp_password);
	        employee.setEmpname(empname);
	        employee.setIsActive(isActive);
	        session.saveOrUpdate(employee);
	        issave =true;
	        
		}catch(Exception e ){
			issave = false;
			e.printStackTrace();
			logger.error("Exception in saveOrUpdate employee details ::"+e.getMessage());
		}
       
		return issave;
	 }
	
	        
	@Override
	public List<Employee> listEmployees() {
		return (List<Employee>) sessionFactory.getCurrentSession().createCriteria(Employee.class).list();
	}
	
	@Override
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Employee> getEmployeesByPage(int PageNumber, int PageSize){
		List<Employee> list = null;
		try{
			/*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Employee.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list= (List<Employee>)criteria.list();
			if(list.size()>(PageSize+PageNumber-1)){
				list =list.subList(PageNumber-1, (PageSize+PageNumber-1));
			}else{
				list =list.subList(PageNumber-1, list.size());
			}*/
			
			PageNumber = PageSize*(PageNumber-1);
			Session session = sessionFactory.getCurrentSession();
			String SQL_QUERY =" from Employee order by EmployeeId OFFSET :PageNumber ROWS  FETCH NEXT :PageSize ROWS ONLY";
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("PageNumber",PageNumber);
			query.setParameter("PageSize",PageSize);
			list = ((Criteria) query).list();
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public Employee getEmployee(int userid) {
		return (Employee) sessionFactory.getCurrentSession().get(Employee.class, userid);
	}
	    
	@SuppressWarnings("deprecation")
	@Override
	public Employee checkEmployeeLogin(String Employeename, String Employeepassword) {
		
		Employee loginuser  = null;
		/* Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY =" from Login login where login.username= :uname and login.password= :pwd";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter("uname",userName);
		query.setParameter("pwd",userPassword);
		List<UserRegister> list = query.list();
		if ((list != null) && (list.size() > 0)) {
			loginuser = list.get(0);
		}
		*/
		
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Employee.class);
		cr.add(Restrictions.eq("employeename", Employeename)).add(Restrictions.eq("password", Employeepassword));
		loginuser = (Employee) cr.uniqueResult();
		
		return loginuser;
	}
	
	@Override
	public int deleteEmp(int empid){
		int flag = 0;
		flag = sessionFactory.getCurrentSession().createQuery("DELETE FROM Employee WHERE emp_id = "+empid).executeUpdate();
		return flag;
	}
}
