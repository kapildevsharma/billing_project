package service;

import java.util.Date;
import java.util.List;

import model.Employee;

public interface EmployeeService {

	public Employee checkEmployeeLogin(String Employeename,String Employeepassword);
	public boolean saveEmployee(String empname,String emp_password,String emailid,Date dob,Date doj,String contact_no,String address,int updateEmpID);
	public List<Employee> listEmployees();
	public List<Employee> getEmployeesByPage(int pageid, int total);
	public Employee getEmployee(int empid);
	public int deleteEmp(int empId);
}
