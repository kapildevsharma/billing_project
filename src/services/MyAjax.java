package services;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import controller.EmployeeController;
import model.Users;
import service.EmployeeService;
import service.MaterialService;
import service.PartyService;
import service.UsersService;

@RemoteProxy
public class MyAjax {
	static final Logger logger = Logger.getLogger(MyAjax.class);
	
	@Autowired
	private UsersService userServices;

	@Autowired
	private EmployeeService empServices;
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private PartyService partyService;

	public void setUserDAO(UsersService userServices) {
		this.userServices = userServices;
	}

	@RemoteMethod
	public List<Users> getUserList() {
		List<Users> listUser = null;
		try {
			listUser = userServices.listUsers();
			System.out.println(listUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listUser;
	}

	@RemoteMethod
	public int deleteUser(int userid){
		int flag =  userServices.deleteUser(userid);
		return flag;
	}
	
	@RemoteMethod
	public int deleteParty(int partyId){
		logger.info("delete partyId : "+partyId);
		return partyService.deleteParty(partyId);
	}

	@RemoteMethod
	public int deleteMaterial(int materialId){
		logger.info("delete materialId : "+materialId);
		return materialService.deleteMaterial(materialId);
	}
	
	@RemoteMethod
	public int deleteEmployee(int empId){
		logger.info("delete empId : "+empId);
		return empServices.deleteEmp(empId);
	}
	@RemoteMethod
	public Users validateUser(String name,String password  ) {
		logger.info("name :"+ name+" password  : "+ password );
		Users userExists = userServices.checkUserLogin(name,password);
		return userExists;
	}

	@RemoteMethod
	public String getPaginatyionEmployee(int totalrecord, int cpage,int limit){
		StringBuffer str = new StringBuffer();
		float totalPage = (totalrecord/limit);
		
		if(totalPage*limit<totalrecord){
			totalPage = totalPage+1;
		}
		
		System.out.println("totalrecord :"+totalrecord+" totalPage :"+totalPage+" cpage : "+cpage);
		
		str.append("<ul class='pagination pagination-sm justify-content-center'>");
		if(cpage==1){
			str.append("<li class='page-item disabled'> <a class='page-link' href='?id=1' tabindex='-1'>Previous</a> </li>");
		}else{
			str.append("<li class='page-item'> <a class='page-link' href='?id="+(cpage-1)+"' tabindex='-1'>Previous</a> </li>");
			
		}
		for(int i=1; i<=totalPage;i++){
			if(cpage!=i){
				str.append("<li class='page-item' style='padding-right: 5px;'><a class='page-link' href='?id="+i+"'>"+i+"</a></li>");
			}else{
				str.append("<li class='page-item active' style='padding-right: 5px;'><span class='page-link'>"+i+"</span></li>");
			}
		}
			
		if(totalPage==cpage){
			str.append("<li class='page-item disabled'> <a class='page-link' href='?id=3'>Next</a></li>");
		}else if(totalrecord>cpage){
			str.append("<li class='page-item'> <a class='page-link' href='?id="+(cpage+1)+"'>Next</a></li>");
		}
		str.append("</ul>");
		return str.toString();
	}
	
	@RemoteMethod
	public String getPaginatyionUser(int totalrecord, int cpage,int limit){
		StringBuffer str = new StringBuffer();
		float totalPage = (totalrecord/limit);
		
		if(totalPage*limit<totalrecord){
			totalPage = totalPage+1;
		}
		System.out.println("totalrecord :"+totalrecord+" totalPage :"+totalPage+" cpage : "+cpage);
		str.append("<ul class='pagination pagination-sm justify-content-center'>");
		if(cpage==1){
			str.append("<li class='page-item disabled'> <a class='page-link' href='?id=1' tabindex='-1'>Previous</a> </li>");
		}else{
			str.append("<li class='page-item'> <a class='page-link' href='?id="+(cpage-1)+"' tabindex='-1'>Previous</a> </li>");
			
		}
		for(int i=1; i<=totalPage;i++){
			if(cpage!=i){
				str.append("<li class='page-item' style='padding-right: 5px;'><a class='page-link' href='?id="+i+"'>"+i+"</a></li>");
			}else{
				str.append("<li class='page-item active' style='padding-right: 5px;'><span class='page-link'>"+i+"</span></li>");
			}
		}
			
		if(totalPage==cpage){
			str.append("<li class='page-item disabled'> <a class='page-link' href='?id=3'>Next</a></li>");
		}else if(totalrecord>cpage){
			str.append("<li class='page-item'> <a class='page-link' href='?id="+(cpage+1)+"'>Next</a></li>");
		}
		str.append("</ul>");
		return str.toString();
	}
	
	@RemoteMethod
	public String getPaginatyionParty(int totalrecord, int cpage,int limit){
		StringBuffer str = new StringBuffer();
		float totalPage = (totalrecord/limit);
		
		if(totalPage*limit<totalrecord){
			totalPage = totalPage+1;
		}
		
		System.out.println("totalrecord :"+totalrecord+" totalPage :"+totalPage+" cpage : "+cpage);
		str.append("<ul class='pagination pagination-sm justify-content-center'>");
		if(cpage==1){
			str.append("<li class='page-item disabled'> <a class='page-link' href='?id=1' tabindex='-1'>Previous</a> </li>");
		}else{
			str.append("<li class='page-item'> <a class='page-link' href='?id="+(cpage-1)+"' tabindex='-1'>Previous</a> </li>");
			
		}
		for(int i=1; i<=totalPage;i++){
			if(cpage!=i){
				str.append("<li class='page-item' style='padding-right: 5px;'><a class='page-link' href='?id="+i+"'>"+i+"</a></li>");
			}else{
				str.append("<li class='page-item active' style='padding-right: 5px;'><span class='page-link'>"+i+"</span></li>");
			}
		}
			
		if(totalPage==cpage){
			str.append("<li class='page-item disabled'> <a class='page-link' href='?id=3'>Next</a></li>");
		}else if(totalrecord>cpage){
			str.append("<li class='page-item'> <a class='page-link' href='?id="+(cpage+1)+"'>Next</a></li>");
		}
		str.append("</ul>");
		return str.toString();
	}
	
	@RemoteMethod
	public String getPaginatyionMaterial(int totalrecord, int cpage,int limit){
		StringBuffer str = new StringBuffer();
		float totalPage = (totalrecord/limit);
		
		if(totalPage*limit<totalrecord){
			totalPage = totalPage+1;
		}
		
		System.out.println("totalrecord :"+totalrecord+" totalPage :"+totalPage+" cpage : "+cpage);
		
		str.append("<ul class='pagination pagination-sm justify-content-center'>");
		if(cpage==1){
			str.append("<li class='page-item disabled'> <a class='page-link' href='?id=1' tabindex='-1'>Previous</a> </li>");
		}else{
			str.append("<li class='page-item'> <a class='page-link' href='?id="+(cpage-1)+"' tabindex='-1'>Previous</a> </li>");
			
		}
		for(int i=1; i<=totalPage;i++){
			if(cpage!=i){
				str.append("<li class='page-item' style='padding-right: 5px;'><a class='page-link' href='?id="+i+"'>"+i+"</a></li>");
			}else{
				str.append("<li class='page-item active' style='padding-right: 5px;'><span class='page-link'>"+i+"</span></li>");
			}
		}
		if(totalPage==cpage){
			str.append("<li class='page-item disabled'> <a class='page-link' href='?id=3'>Next</a></li>");
		}else if(totalrecord>cpage){
			str.append("<li class='page-item'> <a class='page-link' href='?id="+(cpage+1)+"'>Next</a></li>");
		}
		str.append("</ul>");
		return str.toString();
	}
}
