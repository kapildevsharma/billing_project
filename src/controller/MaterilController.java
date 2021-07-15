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
import service.MaterialService;
import service.PartyService;

@Controller
public class MaterilController {
	static final Logger logger = Logger.getLogger(MaterilController.class);
	
	
	@Autowired
	MaterialService materialService;

	@Autowired
	private PartyService partyService;
	
	@RequestMapping(value = "/addMaterial", method = RequestMethod.GET)
	public String processURL(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			modelMap.addAttribute("partyList", partyService.listParty());
			return "addMaterial";
		}
	}
	@RequestMapping(value = "/addMaterial", method = RequestMethod.POST)
	public String editMaterial(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			String userid = request.getParameter("updatemid")==null?"": request.getParameter("updatemid");
			if(userid.equals("")){
				userid="0";
			}
			int updateid = Integer.parseInt(userid) ;
			logger.info("Update material with materialId :"+updateid);
			if(updateid>0){
				modelMap.addAttribute("material",materialService.getMaterial(updateid));
				modelMap.addAttribute("partyList", partyService.listParty());
			}
			return "addMaterial";
		}
	}
	@RequestMapping(value = "/viewMaterial", method = RequestMethod.POST)
	public String addMaterial(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			return "redirect:index.jsp";
		}else{
			double materialPrice =0, materialUnit=0;
			int updatemid = 0;
			boolean saveMaterial = false;
			String materialName = request.getParameter("mname")==null?"":request.getParameter("mname").trim();
			String materialType = request.getParameter("mtype")==null?"":request.getParameter("mtype").trim();
			String munit = request.getParameter("munit")==null?"0":request.getParameter("munit").trim();
			String mprice = request.getParameter("mprice")==null?"0":request.getParameter("mprice").trim();
			String updateid = request.getParameter("updatemid")==null?"":request.getParameter("updatemid").trim();
			int isActive = request.getParameter("isActive")==null?0: Integer.parseInt(request.getParameter("isActive"));
			int party_id = request.getParameter("party")==null?0:Integer.parseInt(request.getParameter("party"));
			
			Party party = partyService.getParty(party_id);
			
			logger.info("Entered Details :: MaterialName: "+materialName+" materialType : "+materialType+" munit : "+munit
					+" mprice : "+mprice+" isActive :"+isActive+" updateid :"+updateid+" party_id:"+party_id);
			
			if(materialName.equals("") ||materialType.equals("") || munit.equals("") || mprice.equals("") || party_id==0){
				logger.info("Entered Required details for Material.");
				modelMap.addAttribute("msg", "* Please filled required field" );
				return "redirect:addMaterial";
			}
			try{
				materialUnit = Double.parseDouble(munit);
			}catch(NumberFormatException nfe){
				logger.error("Entered material unit invalid. munit :: "+munit+" :: "+nfe.getMessage());
				modelMap.addAttribute("msg", "Entered material unit invalid." );
				return "redirect:addMaterial";
			}
			
			try{
				materialPrice = Double.parseDouble(mprice);
			}catch(NumberFormatException nfe){
				logger.error("Entered material price invalid. mprice ::"+mprice+" :: "+nfe.getMessage());
				modelMap.addAttribute("msg", "Entered material price invalid." );
				return "redirect:addMaterial";
			}
			
			
			if(updateid.equals("")){updateid="0";}
			
			logger.info("MaterialName: "+materialName+" materialType : "+materialType+" materialUnit : "+materialUnit
					+" materialPrice : "+materialPrice+" isActive :"+isActive+" updateid :"+updateid);
			
			updatemid = Integer.parseInt(updateid);
			
			try{
				saveMaterial = materialService.saveMaterial(materialName, materialType, materialUnit, materialPrice, isActive, updatemid,party);
			}catch(Exception e ){
				e.printStackTrace();
				logger.error("Exception in saveMaterial :: "+e.getMessage());
			}
			
			if(saveMaterial){
				logger.info("Material details is saved successfully ");
				modelMap.addAttribute("materials",materialService.listMaterial());
				return "redirect:viewMaterial";
			}else{
				logger.info("Material details is not saved.");
				modelMap.addAttribute("msg", "Filled Required Field" );
				return "redirect:addMaterial";
			}
		}
		
	}

	@RequestMapping(value = "/viewMaterial", method = RequestMethod.GET)
	public String viewMaterial(ModelMap modelMap,HttpServletRequest request) {
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
	        List<Materials> mlist = materialService.listMaterial();
	        int totalRecord = mlist.size();
	        
	        List<Materials> list=null;  
	        if(totalRecord<total){
	        	 list=materialService.getMaterialsByPage(limit,total);  
	        }else{
	        	list = mlist;
	        }
	        
	        modelMap.addAttribute("materials",list);
	        modelMap.addAttribute("totalRecord", totalRecord);
	        modelMap.addAttribute("cpage",pageid);
			modelMap.addAttribute("material",null);
			return "viewMaterial";
		}
	}
	

}
