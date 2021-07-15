package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Employee;
import service.EmployeeService;

@Controller
public class EmployeeController {

	static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeservice;
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
	public String processURL(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			modelMap.addAttribute("employee",null);
			return "addEmployee";
		}
	}
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public String editEmployee(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			String empid = request.getParameter("updateEmpID")==null?"": request.getParameter("updateEmpID");
			if(empid.equals("")){
				empid="0";
			}
			int updateEmpID = Integer.parseInt(empid) ;
			logger.info("Updating User id :"+updateEmpID);
			if(updateEmpID>0){
				modelMap.addAttribute("employee",employeeservice.getEmployee(updateEmpID));
			}
			return "addEmployee";
		}
	}
	
	@RequestMapping(value = "/viewEmployee", method = RequestMethod.POST)
	public String addEmployee(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			String name = request.getParameter("name")==null?"":request.getParameter("name").trim();
			String address = request.getParameter("address")==null?"":request.getParameter("address").trim();
			String birthDate = request.getParameter("dob")==null?"":request.getParameter("dob").trim();
			String contact = request.getParameter("contact")==null?"":request.getParameter("contact").trim();
			String pwd = request.getParameter("pwd")==null?"":request.getParameter("pwd").trim();
			String updateid = request.getParameter("updateid")==null?"":request.getParameter("updateid").trim();
			String emailid = request.getParameter("emailid")==null?"":request.getParameter("emailid").trim();
			String joiningDate = request.getParameter("doj")==null?"":request.getParameter("doj").trim();
			
			if(updateid.equals("")){updateid="0";}
			logger.info("Entered Details:: Name: "+name+" address : "+address+" birthDate : "+birthDate+" emailid:"+emailid
					+" contact_no : "+contact+" password :"+pwd+" updateid :"+updateid+" joiningDate:"+joiningDate);
			int update_id = 0;
			boolean is_saveEmployee = false;
			Date birth_date =null, joining_date=null;
			
			update_id = Integer.parseInt(updateid);
			if(name.equals("") || pwd.equals("") || birthDate.equals("")){
				logger.info("Entered required details of employee");
				is_saveEmployee = false;
			}else{
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					birth_date = formatter.parse(birthDate);
					joining_date = formatter.parse(joiningDate);
					birth_date = new java.sql.Date(birth_date.getTime());
					joining_date = new java.sql.Date(joining_date.getTime());
					logger.debug("birth_date "+birth_date+" joining_date :"+joining_date);
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error("Entered date incorrect :: "+birthDate );
					is_saveEmployee = false;
					modelMap.addAttribute("msg", "* Entered BirthDate invalid " );
					return "redirect:addEmployee";
				}
				try{
					is_saveEmployee = employeeservice.saveEmployee(name, pwd, emailid, birth_date, joining_date, contact, address, update_id);
				}catch(Exception e ){
					e.printStackTrace();
					logger.error("Exception in saveEmployee() ::: "+e.getMessage());
				}
			}
			if(is_saveEmployee){
				logger.info("Employee details is saved successfully ");
				modelMap.addAttribute("employees",employeeservice.listEmployees());
				return "redirect:viewEmployee";
			}else{
				logger.info("Employee details is not saved");
				modelMap.addAttribute("msg", "Filled Required Field" );
				return "redirect:addEmployee";
			}
		}
		
	}

	@RequestMapping(value = "/viewEmployee", method = RequestMethod.GET)
	public String viewEmployee(ModelMap modelMap,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			int pageid = request.getParameter("id")==null?1:Integer.parseInt(request.getParameter("id"));

			int total=5,limit = 1;  
	        if(pageid==1){}  
	        else{  
	        	limit=(pageid-1)*total+1;  
	        }  
	        List<Employee> list=employeeservice.getEmployeesByPage(limit,total);  
	        modelMap.addAttribute("employees",list);
	        modelMap.addAttribute("totalRecord",employeeservice.listEmployees().size());
	        modelMap.addAttribute("cpage",pageid);
			modelMap.addAttribute("employee",null);
			return "viewEmployee";
		}
	}
	
}
