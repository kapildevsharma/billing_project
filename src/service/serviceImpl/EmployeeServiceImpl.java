package service.serviceImpl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import controller.EmployeeController;
import dao.EmployeeDAO;
import model.Employee;
import service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Override
	@Transactional
	public Employee checkEmployeeLogin(String Employeename,String Employeepassword)
	{
		 Employee existEmployee = null;
		 try{
			 existEmployee = employeeDAO.checkEmployeeLogin(Employeename, Employeepassword);
		 }catch(Exception e ){
			 e.printStackTrace();
			 logger.error("Exception :: "+e.getMessage());
		 }
         return existEmployee;
		
	}
	
	@Transactional 
	@Override
	public boolean saveEmployee(String empname,String emp_password,String emailid,Date dob,Date doj,String contact_no,String address,int updateEmpID)
	{
		boolean isSave = false;
		try{							
			isSave = employeeDAO.saveEmployee(empname,emp_password,emailid,dob,doj,contact_no,address,updateEmpID);
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("Exception save employee details :: "+e.getMessage());
		}
		return isSave;
	}
	
	@Transactional 
	@Override
	public List<Employee> listEmployees() {
		 return employeeDAO.listEmployees();
	}
	@Transactional 
	@Override
	public Employee getEmployee(int empid) {
		return employeeDAO.getEmployee(empid);
	}
	
	@Override
	@Transactional
	public int deleteEmp(int empid){
		return employeeDAO.deleteEmp(empid);
	}

	@Override
	@Transactional
	public List<Employee> getEmployeesByPage(int pageid, int total) {
		return employeeDAO.getEmployeesByPage(pageid, total);
	}
}

