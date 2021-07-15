package controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.Users;
import service.UsersService;
import util.UserValidation;

@Controller
public class LoginController {
	static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	UsersService usersService;

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String processLoginForm(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}
	
	@RequestMapping(value = "/loginUser",method = RequestMethod.POST)
	public  @ResponseBody String loginForm(HttpServletRequest request, HttpServletResponse response,@RequestBody String userInfo) {
		JSONObject json = null;
		  int loginUserId = 0;
		try {
			json = new JSONObject(userInfo);
			loginUserId = json.getInt("loginUserId" );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(loginUserId==0){
			logger.debug("Invalid Access");
			return "false";
		}else{
			logger.debug("loginUserId "+loginUserId+" valueOne :"+userInfo);
			Users loginUser = usersService.getUser(loginUserId);
			boolean projectLicenseFlag = checkExpireKey(loginUser, request);
			if(!projectLicenseFlag){
				request.getSession().setAttribute("loginUser", loginUser);
				request.getSession().setMaxInactiveInterval(15*60);
				return "true";
			}else{
				return "false";
			}
		}
		
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    request.getSession().setAttribute("loginUser", null);
	    request.getSession().invalidate();
	    return "redirect:login?logout";
	}
	
	public boolean checkExpireKey(Users loginUser,HttpServletRequest request){
		boolean licenseFlag = false;
		try {
			Date date = new Date();

			Properties properties = (Properties) request.getSession().getAttribute("projectLicense.properties");
			String license_date = properties.getProperty("date");

			System.out.println("license_date : " + license_date);

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date licenseDate = formatter.parse(license_date);
			Date currentDate = formatter.parse(formatter.format(date));
			
			String projectExpireDate = new UserValidation().getProjectExpireDate();
			Date project_expire_date = formatter.parse(projectExpireDate);
			System.out.println("Project License Date : "+project_expire_date+" :: "+licenseDate);
			
			if(loginUser==null){
				if(!UserValidation.isValidUserFlag()){
					UserValidation.setValidUserFlag(true);
				}
			}else{
				if (currentDate.compareTo(licenseDate) > 0 && currentDate.compareTo(project_expire_date)>0) {
					System.out.println("License is expired on " + licenseDate+" validUserFlag:: "+UserValidation.isValidUserFlag());
					UserValidation.setValidUserFlag(true);
					licenseFlag = true;
				} else if (currentDate.compareTo(licenseDate) <= 0) {
					try {
						int employeeId = loginUser.getId();
						
						int isActive = loginUser.getIsActive();
						if (isActive==0) {
							UserValidation.setValidUserFlag(true);
							licenseFlag = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("license Flag "+licenseFlag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return licenseFlag;
		
	}
	
}
