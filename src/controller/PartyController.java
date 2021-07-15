package controller;

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

import model.Materials;
import model.Party;
import service.PartyService;

@Controller
public class PartyController {

	static final Logger logger = Logger.getLogger(PartyController.class);
	
	@Autowired
	PartyService partyService;
	
	@RequestMapping(value = "/addParty", method = RequestMethod.GET)
	public String getPartyURL(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			return "addParty";
		}
	}
	@RequestMapping(value = "/addParty", method = RequestMethod.POST)
	public String editParty(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			String userid = request.getParameter("updatePartyId")==null?"": request.getParameter("updatePartyId");
			if(userid.equals("")){
				userid="0";
			}
			int updatePartyId = Integer.parseInt(userid) ;
			logger.info("Update party deatils with PartyId :"+updatePartyId);
			if(updatePartyId>0){
				modelMap.addAttribute("party",partyService.getParty(updatePartyId));
			}
			return "addParty";
		}
	}
	@RequestMapping(value = "/viewParty", method = RequestMethod.POST)
	public String addParty(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			String name = request.getParameter("name")==null?"":request.getParameter("name").trim();
			String address = request.getParameter("address")==null?"":request.getParameter("address").trim();
			String tin_number = request.getParameter("tin_no")==null?"":request.getParameter("tin_no").trim();
			String contact_no = request.getParameter("contact_no")==null?"":request.getParameter("contact_no").trim();
			String updateid = request.getParameter("updateid")==null?"":request.getParameter("updateid");
			int isActive = request.getParameter("isActive")==null?0: Integer.parseInt(request.getParameter("isActive"));
			if(updateid.equals("")){updateid="0";}
			logger.info("name: "+name+" tin_number : "+tin_number +" contact_no : "+contact_no
					+" isActive :"+isActive+" address : "+address+" updateid :"+updateid);
			int updatePartyId = 0;
			boolean isSaveParty = false;
			
			updatePartyId = Integer.parseInt(updateid);
			if(name.equals("") || tin_number.equals("") || contact_no.equals("")){
				logger.info("Enter requried details for Party");
				isSaveParty = false;
			}else{
				try{
					isSaveParty = partyService.saveParty(name, contact_no, tin_number, address, isActive, updatePartyId);
				}catch(Exception e ){
					e.printStackTrace();
					logger.error("Exception in saveParty :: "+e.getMessage());
				}
			}
			if(isSaveParty){
				logger.info("Party details is saved successfully");
				modelMap.addAttribute("parties",partyService.listParty());
				return "redirect:viewParty";
			}else{
				logger.info("Party details is not saved");
				modelMap.addAttribute("msg", "* Please filled required field" );
				return "redirect:addParty";
			}
		}
	}

	@RequestMapping(value = "/viewParty", method = RequestMethod.GET)
	public String viewUsers(ModelMap modelMap,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			int pageid = request.getParameter("id")==null?1:Integer.parseInt(request.getParameter("id"));
			
			List<Party> list=partyService.listParty();  
	        modelMap.addAttribute("parties",list);
			modelMap.addAttribute("totalRecord",list.size());
	        modelMap.addAttribute("cpage",pageid);
			return "viewParty";
		}
	}
}