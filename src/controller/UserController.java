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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Party;
import model.Users;
import service.UsersService;

@Controller
public class UserController {
	static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	UsersService usersService;

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String processURL(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		return "addUser";
	}
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String editUser(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			String userid = request.getParameter("updateid")==null?"": request.getParameter("updateid");
			if(userid.equals("")){
				userid="0";
			}
			int updateid = Integer.parseInt(userid) ;
			logger.info("Update user deatils with Userid :"+updateid);
			if(updateid>0){
				modelMap.addAttribute("user",usersService.getUser(updateid));
			}
			return "addUser";
		}
	}
	@RequestMapping(value = "/viewUser", method = RequestMethod.POST)
	public String addUser(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		/*HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}*/

		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		String birthDate = request.getParameter("dob")==null?"":request.getParameter("dob");
		String contact = request.getParameter("contact")==null?"":request.getParameter("contact");
		String pwd = request.getParameter("pwd")==null?"":request.getParameter("pwd");
		String updateid = request.getParameter("updateid")==null?"":request.getParameter("updateid");
		if(updateid.equals("")){updateid="0";}
		logger.info("Name: "+name+" address : "+address+" birthDate : "+birthDate
				+" contact_no : "+contact+" password :"+pwd+" updateid :"+updateid);
		int update_id = 0,contact_no=0;
		boolean is_saveUser = false;
		Date birth_date =null;
		
		update_id = Integer.parseInt(updateid);
		if(name.equals("") || pwd.equals("") || birthDate.equals("")){
			logger.debug("Entered required details for User");
			is_saveUser = false;
		}else{
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				birth_date = formatter.parse(birthDate);
				birth_date = new java.sql.Date(birth_date.getTime());
				logger.debug("Birth date ::  "+birth_date);
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("Entered birthDate invalid :: "+birthDate );
				is_saveUser = false;
				modelMap.addAttribute("msg", "* Incorrect Entered birthDate " );
				return "redirect:addUser";
			}
			if (!contact.equals("")){/*
				if(contact.length()>=10 && contact.length()<13){
					try{
						contact_no = Integer.parseInt(contact);
					}catch(Exception e ){
						System.out.println("UserController :: Contact number not integer" );
						contact_no =0;
					}
				}
				if(contact_no==0 ){
					is_saveUser = false;
					System.out.println("UserController :: User enter invalid contact number" );
					modelMap.addAttribute("msg", "* Entered contact number invalid ");
					return "redirect:addUser";
				}
			*/}
			try{
				Users user = new Users();
				user.setUsername(name);
				user.setPassword(pwd);
				user.setAddress(address);
				user.setDob(birth_date);
				user.setContact_no(contact);
				user.setId(update_id);
				is_saveUser = usersService.saveUser(user);
			}catch(Exception e ){
				e.printStackTrace();
				logger.error("Exception in saveUser() ::: "+e.getMessage());
			}
		}
		logger.info("User details :: "+is_saveUser );
		if(is_saveUser){
			logger.info("User details is saved successfully");
			modelMap.addAttribute("users",usersService.listUsers());
			return "viewUser";
		}else{
			logger.info("User details is not saved");
			modelMap.addAttribute("msg", "Filled Required Field" );
			return "redirect:addUser";
		}
	
	}

	@RequestMapping(value = "/viewUser", method = RequestMethod.GET)
	public String viewUsers(ModelMap modelMap,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			int pageid = request.getParameter("id")==null?1:Integer.parseInt(request.getParameter("id"));
			int max=5,limit = 1;  
	        if(pageid==1){}  
	        else{  
	        	limit=(pageid-1)*max+1;  
	        } 
	        List<Users> userlist =  usersService.listUsers();
	        int totalRecord =userlist.size();
	        
			List<Users> list=null;
			
			if(totalRecord>max){
				list = usersService.getUsersByPage(limit, max);
			}else{
				list = userlist;
			}
			
	        modelMap.addAttribute("users",list);
			modelMap.addAttribute("totalRecord",totalRecord);
	        modelMap.addAttribute("cpage",pageid);
			modelMap.addAttribute("user",null);
			return "viewUser";
		}
	}
	
}


