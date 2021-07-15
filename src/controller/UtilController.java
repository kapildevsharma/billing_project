package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import model.Employee;
import model.Materials;
import model.Party;
import model.Users;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.EmployeeService;
import service.MaterialService;
import service.PartyService;
import service.UsersService;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class UtilController {

	static final Logger logger = Logger.getLogger(UtilController.class);
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	PartyService partyService;
	
	@Autowired
	MaterialService materialService;
		
	@RequestMapping(value = "/exceldownload", method = RequestMethod.GET)
	public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response)  {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			//	return "redirect:index.jsp";
		}else{
			ServletContext context = request.getServletContext();
	        String filePath=null;
	        
	        String flag = request.getParameter("id");
	        if(flag.equals("employee")) {
	        	filePath  = generateEntityFileTypeEXLEmployee(request);
	        }
	        if(flag.equals("user")){
	        	filePath  = generateEntityFileTypeEXLUser(request);
	        }
	        if(flag.equals("party")) {
	        	filePath  = generateEntityFileTypeEXLParty(request);
	        }
	        if(flag.equals("material")) {
	        	filePath  = generateEntityFileTypeEXLMaterial(request);
	        }
	       
	        logger.info("Generated Excel File Path ::"+filePath);
	        
	        File downloadFile = new File(filePath);
	        FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(downloadFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
	         
	        String mimeType = context.getMimeType(filePath);
	        if (mimeType == null) {
	            mimeType = "application/octet-stream";
	        }
	 
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	 
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	                downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	 
	        OutputStream outStream = null;
			try {
				outStream = response.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
	        byte[] buffer = new byte[1024];
	        int bytesRead = -1;
	        try {
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
		        }
	        } catch (IOException e) {
	        	logger.error("Exception reading excel file "+e.getMessage());
				e.printStackTrace();
			}finally{
				try {inputStream.close();} catch (IOException e) {e.printStackTrace();}
				try {outStream.close();} catch (IOException e) {e.printStackTrace();}
			}
		}
    }
		
	@RequestMapping(value = "/pdfdownload", method = RequestMethod.GET)
	public void downloadPDFFile(HttpServletRequest request, HttpServletResponse response)  {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
			//return "redirect:index.jsp";
		}else{
	        // get absolute path of the application
	        ServletContext context = request.getServletContext();
	        String appPath = context.getRealPath("");
	        logger.debug("appPath = " + appPath);
	        
	        String filePath=null;
	        String flag = request.getParameter("id");
	        if(flag.equals("employee")){
	        	filePath = generateEntityFileTypePDFEmployee(request);
	        }
	        if(flag.equals("user")) {
	        	 filePath = generateEntityFileTypePDFUser(request);
	        }
	        if(flag.equals("party")) {
	        	 filePath = generateEntityFileTypePDFParty(request);
	        }
	        if(flag.equals("material")){
	        	 filePath = generateEntityFileTypePDFMaterial(request);
	        }
	        logger.info("Gernated PDF File path ::"+filePath);
	        
	        File downloadFile = new File(filePath);
	        FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(downloadFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
	         
	        String mimeType = context.getMimeType(filePath);
	        if (mimeType == null) {
	            mimeType = "application/octet-stream";
	        }
	 
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	 
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	                downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	 
	        OutputStream outStream = null;
			try {
				outStream = response.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
	        byte[] buffer = new byte[1024];
	        int bytesRead = -1;
	        try {
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
		        }
	        } catch (IOException e) {
	        	logger.error("Exception reading pdf file "+e.getMessage());
				e.printStackTrace();
			}finally{
				try {inputStream.close();} catch (IOException e) {e.printStackTrace();}
				try {outStream.close();} catch (IOException e) {e.printStackTrace();}
			}
		}
    }
	
	@RequestMapping(value = "/csvdownload", method = RequestMethod.GET)
	public void downloadCSVFile(HttpServletRequest request, HttpServletResponse response)  {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) 
		{
		//	return "redirect:index.jsp";
		}else{
			ServletContext context = request.getServletContext();
	        String filePath=null;
	        String flag = request.getParameter("id");
	        
	        if(flag.equals("employee")) {
	        	filePath  = generateEntityFileTypeCSVEmployee(request);
	        }
			if (flag.equals("user")) {
				filePath = generateEntityFileTypeCSVUser(request);
			}
			if (flag.equals("party")) {
				filePath = generateEntityFileTypeCSVParty(request);
			}
			if (flag.equals("material")) {
				filePath = generateEntityFileTypeCSVMaterial(request);
			}
	        
			logger.info("Generated CSV File Path ::"+filePath);
	        
	        File downloadFile = new File(filePath);
	        FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(downloadFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
	         
	        String mimeType = context.getMimeType(filePath);
	        if (mimeType == null) {
	            mimeType = "application/octet-stream";
	        }
	 
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	 
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"",
	                downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	 
	        OutputStream outStream = null;
			try {
				outStream = response.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
	        byte[] buffer = new byte[1024];
	        int bytesRead = -1;
	        try {
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
		        }
	        } catch (IOException e) {
	        	logger.error("Exception reading CSV file "+e.getMessage());
				e.printStackTrace();
			}finally{
				try {inputStream.close();} catch (IOException e) {e.printStackTrace();}
				try {outStream.close();	} catch (IOException e) {e.printStackTrace();}
			}
		}
    }
		
	// generate Employee EXL File 
	public String generateEntityFileTypeEXLEmployee(HttpServletRequest request){

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		File file = null;
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation1") + "/Excel";
			String fileName = "employeedetail" + String.valueOf(System.currentTimeMillis()).substring(6) + ".xls";
			
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();

			file = new File(basePath + "/" + fileName);
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));

			WritableCellFormat timesBoldUnderline;
			WritableCellFormat header;
			WritableCellFormat subHeader;
			WritableCellFormat columns;
			WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet("Employee Infomation", 0);
			WritableSheet excelSheet = workbook.getSheet(0);

			WritableFont times20ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 20, WritableFont.BOLD, false);
			WritableFont times12ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false);
			WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
			
			timesBoldUnderline = new WritableCellFormat(times20ptBoldUnderline);
			timesBoldUnderline.setAlignment(Alignment.CENTRE);
			// Lets automatically wrap the cells
			timesBoldUnderline.setWrap(true);
			timesBoldUnderline.setBackground(Colour.LIGHT_GREEN);
			
			header = new WritableCellFormat(times12ptBoldUnderline);
			header.setBackground(Colour.VERY_LIGHT_YELLOW);
			
			subHeader = new WritableCellFormat(times10ptBoldUnderline);
			subHeader.setBackground(Colour.LIGHT_ORANGE);
			subHeader.setShrinkToFit(true);
			
			List<Employee> employeelist = employeeService.listEmployees();
			int numberOfRow = 1 + employeelist.size();
			
			// Write a few headers
			excelSheet.mergeCells(0, 0, numberOfRow, 1);
			Label label;
			label = new Label(0, 0, "Employee Details", timesBoldUnderline);
			excelSheet.addCell(label);
			excelSheet.mergeCells(0, 3, numberOfRow, 4);
			label = new Label(0, 3, "");
			excelSheet.addCell(label);
			
			
			label = new Label(0, 2, "Create By Admin",subHeader);
			excelSheet.addCell(label);
			
			label = new Label(1, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(2, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(3, 2, "",subHeader);
			excelSheet.addCell(label);
			
			label = new Label(4, 2, "Create Date"+ ": " + sdf.format(new Date()),subHeader);
			excelSheet.addCell(label);
			
			excelSheet.getSettings().setDefaultColumnWidth(18);

			int k = 5;
			label = new Label(0, k, "Employee Id", header);
			excelSheet.addCell(label);
			label = new Label(1, k, "Employee Name", header);
			excelSheet.addCell(label);
			label = new Label(2, k, "Date Of Birth", header);
			excelSheet.addCell(label);
			label = new Label(3, k, "Contact No", header);
			excelSheet.addCell(label);
			label = new Label(4, k, "Address", header);
			excelSheet.addCell(label);
			label = new Label(5, k, "Email", header);
			excelSheet.addCell(label);
			label = new Label(6, k, "DateOfJoining", header);
			excelSheet.addCell(label);
			k = k + 1;
			if (employeelist.size() == 0) {
				excelSheet.mergeCells(0, k, 6, k);
				label = new Label(0, k, "No Records Found");
				excelSheet.addCell(label);
			}
			columns = new WritableCellFormat(times10ptBoldUnderline);
			
			if (employeelist.size() > 0) {
				for (Employee employeeOrder : employeelist) {
					label = new Label(0, k, String.valueOf(employeeOrder.getEmp_id()),columns);
					excelSheet.addCell(label);

					label = new Label(1, k, employeeOrder.getEmpname(),columns);
					excelSheet.addCell(label);

					label = new Label(2, k, "" + sdf.format(employeeOrder.getDob()),columns);
					excelSheet.addCell(label);

					label = new Label(3, k, "" + employeeOrder.getContact_no(),columns);
					excelSheet.addCell(label);

					label = new Label(4, k, "" + employeeOrder.getAddress(),columns);
					excelSheet.addCell(label);
					
					label = new Label(5, k, "" + employeeOrder.getEmailid(),columns);
					excelSheet.addCell(label);
					
					label = new Label(6, k, "" + employeeOrder.getDoj(),columns);
					excelSheet.addCell(label);

					k++;
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in creating employee excel file ::"+e.getMessage());
		}
		return file.getAbsolutePath();
	}
		
	// generate User EXL File 
	public String generateEntityFileTypeEXLUser(HttpServletRequest request){

		File file = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation") + "/Excel";
			String fileName = "userdetail" + String.valueOf(System.currentTimeMillis()).substring(6) + ".xls";
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();

			file = new File(basePath + "/" + fileName);
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));

			WritableCellFormat timesBoldUnderline;
			WritableCellFormat header;
			WritableCellFormat subHeader;
			WritableCellFormat columns;
			WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet("User Infomation", 0);
			WritableSheet excelSheet = workbook.getSheet(0);

			WritableFont times20ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 20, WritableFont.BOLD, false);
			WritableFont times12ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false);
			WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
			
			timesBoldUnderline = new WritableCellFormat(times20ptBoldUnderline);
			timesBoldUnderline.setAlignment(Alignment.CENTRE);
			// Lets automatically wrap the cells
			timesBoldUnderline.setWrap(true);
			timesBoldUnderline.setBackground(Colour.LIGHT_GREEN);
			
			header = new WritableCellFormat(times12ptBoldUnderline);
			header.setBackground(Colour.VERY_LIGHT_YELLOW);
			
			subHeader = new WritableCellFormat(times10ptBoldUnderline);
			subHeader.setBackground(Colour.LIGHT_ORANGE);
			subHeader.setShrinkToFit(true);
			
			List<Users> userlist = usersService.listUsers();
			int numberOfRow = 1 + userlist.size();
			
			// Write a few headers
			excelSheet.mergeCells(0, 0, numberOfRow, 1);
			Label label;
			label = new Label(0, 0, "User Details", timesBoldUnderline);
			excelSheet.addCell(label);
			excelSheet.mergeCells(0, 3, numberOfRow, 4);
			label = new Label(0, 3, "");
			excelSheet.addCell(label);
			
			label = new Label(0, 2, "Create By Admin",subHeader);
			excelSheet.addCell(label);
			
			label = new Label(1, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(2, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(3, 2, "",subHeader);
			excelSheet.addCell(label);
			
			label = new Label(4, 2, "Create Date"+ ": " + sdf.format(new Date()),subHeader);
			excelSheet.addCell(label);
			
			excelSheet.getSettings().setDefaultColumnWidth(18);

			int k = 5;
			label = new Label(0, k, "User Id", header);
			excelSheet.addCell(label);
			label = new Label(1, k, "UserName", header);
			excelSheet.addCell(label);
			label = new Label(2, k, "Date Of Birth", header);
			excelSheet.addCell(label);
			label = new Label(3, k, "Contact No", header);
			excelSheet.addCell(label);
			label = new Label(4, k, "Address", header);
			excelSheet.addCell(label);

			k = k + 1;
			if (userlist.size() == 0) {
				excelSheet.mergeCells(0, k, 4, k);
				label = new Label(0, k, "No Records Fouond");
				excelSheet.addCell(label);
			}
			columns = new WritableCellFormat(times10ptBoldUnderline);
			
			if (userlist.size() > 0) {
				for (Users userOrder : userlist) {
					label = new Label(0, k, String.valueOf(userOrder.getId()),columns);
					excelSheet.addCell(label);

					label = new Label(1, k, userOrder.getUsername(),columns);
					excelSheet.addCell(label);

					label = new Label(2, k, "" + sdf.format(userOrder.getDob()),columns);
					excelSheet.addCell(label);

					label = new Label(3, k, "" + userOrder.getContact_no(),columns);
					excelSheet.addCell(label);

					label = new Label(4, k, "" + userOrder.getAddress(),columns);
					excelSheet.addCell(label);

					k++;
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in creating user excel file ::"+e.getMessage());
		}
		return file.getAbsolutePath();
	}
		
	// generate Party EXL File 
	public String generateEntityFileTypeEXLParty(HttpServletRequest request){
		File file = null;
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation2") + "/Excel";
			logger.debug("basepath : " + basePath);
			
			String fileName = "partydetail" + String.valueOf(System.currentTimeMillis()).substring(6) + ".xls";
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();

			file = new File(basePath + "/" + fileName);
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));

			WritableCellFormat timesBoldUnderline;
			WritableCellFormat header;
			WritableCellFormat subHeader;
			WritableCellFormat columns;
			WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet("Party Infomation", 0);
			WritableSheet excelSheet = workbook.getSheet(0);

			WritableFont times20ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 20, WritableFont.BOLD, false);
			WritableFont times12ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false);
			WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
			
			timesBoldUnderline = new WritableCellFormat(times20ptBoldUnderline);
			timesBoldUnderline.setAlignment(Alignment.CENTRE);
			timesBoldUnderline.setWrap(true);
			timesBoldUnderline.setBackground(Colour.LIGHT_GREEN);
			
			header = new WritableCellFormat(times12ptBoldUnderline);
			header.setBackground(Colour.VERY_LIGHT_YELLOW);
			
			subHeader = new WritableCellFormat(times10ptBoldUnderline);
			subHeader.setBackground(Colour.LIGHT_ORANGE);
			subHeader.setShrinkToFit(true);
			
			List<Party> partylist = partyService.listParty();
			int numberOfRow = 1 + partylist.size();
			
			excelSheet.mergeCells(0, 0, numberOfRow, 1);
			Label label;
			label = new Label(0, 0, "Party Details", timesBoldUnderline);
			excelSheet.addCell(label);
			excelSheet.mergeCells(0, 3, numberOfRow, 4);
			label = new Label(0, 3, "");
			excelSheet.addCell(label);
			
			label = new Label(0, 2, "Create By Admin",subHeader);
			excelSheet.addCell(label);
			
			label = new Label(1, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(2, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(3, 2, "",subHeader);
			excelSheet.addCell(label);
			
			label = new Label(4, 2, "",subHeader);
			excelSheet.addCell(label);
			
			excelSheet.getSettings().setDefaultColumnWidth(18);

			int k = 5;
			label = new Label(0, k, "Party Id", header);
			excelSheet.addCell(label);
			label = new Label(1, k, "PartyName", header);
			excelSheet.addCell(label);
			label = new Label(2, k, "TIN_Number", header);
			excelSheet.addCell(label);
			label = new Label(3, k, "Contact No", header);
			excelSheet.addCell(label);
			label = new Label(4, k, "Address", header);
			excelSheet.addCell(label);

			k = k + 1;
			if (partylist.size() == 0) {
				excelSheet.mergeCells(0, k, 4, k);
				label = new Label(0, k, "No Records Fouond");
				excelSheet.addCell(label);
			}
			columns = new WritableCellFormat(times10ptBoldUnderline);
			
			if (partylist.size() > 0) {
				for (Party partyOrder : partylist) {
					label = new Label(0, k, String.valueOf(partyOrder.getParty_id()),columns);
					excelSheet.addCell(label);
					label = new Label(1, k, partyOrder.getName(),columns);
					excelSheet.addCell(label);
					label = new Label(2, k, "" + partyOrder.getTin_number(),columns);
					excelSheet.addCell(label);
					label = new Label(3, k, "" + partyOrder.getContact_no(),columns);
					excelSheet.addCell(label);
					label = new Label(4, k, "" + partyOrder.getAddress(),columns);
					excelSheet.addCell(label);
					k++;
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in creating party excel file::"+e.getMessage());
		}
		return file.getAbsolutePath();
	}
		
	// generate Material EXL File 
	public String generateEntityFileTypeEXLMaterial(HttpServletRequest request){
		File file = null;
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation3") + "/Excel";
			String fileName = "materialdetail" + String.valueOf(System.currentTimeMillis()).substring(6) + ".xls";
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();

			file = new File(basePath + "/" + fileName);
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));

			WritableCellFormat timesBoldUnderline;
			WritableCellFormat header;
			WritableCellFormat subHeader;
			WritableCellFormat columns;
			WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet("Material Infomation", 0);
			WritableSheet excelSheet = workbook.getSheet(0);

			WritableFont times20ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 20, WritableFont.BOLD, false);
			WritableFont times12ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false);
			WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
			
			timesBoldUnderline = new WritableCellFormat(times20ptBoldUnderline);
			timesBoldUnderline.setAlignment(Alignment.CENTRE);
			timesBoldUnderline.setWrap(true);
			timesBoldUnderline.setBackground(Colour.LIGHT_GREEN);
			
			header = new WritableCellFormat(times12ptBoldUnderline);
			header.setBackground(Colour.VERY_LIGHT_YELLOW);
			
			subHeader = new WritableCellFormat(times10ptBoldUnderline);
			subHeader.setBackground(Colour.LIGHT_ORANGE);
			subHeader.setShrinkToFit(true);
			
			List<Materials> materiallist = materialService.listMaterial();
			int numberOfRow = 1 + materiallist.size();
			
			excelSheet.mergeCells(0, 0, numberOfRow, 1);
			Label label;
			label = new Label(0, 0, "Material Details", timesBoldUnderline);
			excelSheet.addCell(label);
			excelSheet.mergeCells(0, 3, numberOfRow, 4);
			label = new Label(0, 3, "");
			excelSheet.addCell(label);
			
			label = new Label(0, 2, "Create By Admin",subHeader);
			excelSheet.addCell(label);
			label = new Label(1, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(2, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(3, 2, "",subHeader);
			excelSheet.addCell(label);
			label = new Label(4, 2, "",subHeader);
			excelSheet.addCell(label);
			excelSheet.getSettings().setDefaultColumnWidth(18);

			int k = 5;
			label = new Label(0, k, "Material Name", header);
			excelSheet.addCell(label);
			label = new Label(1, k, "Material Type", header);
			excelSheet.addCell(label);
			label = new Label(2, k, "Material Price", header);
			excelSheet.addCell(label);
			label = new Label(3, k, "Material Unit", header);
			excelSheet.addCell(label);

			k = k + 1;
			if (materiallist.size() == 0) {
				excelSheet.mergeCells(0, k, 3, k);
				label = new Label(0, k, "No Records Fouond");
				excelSheet.addCell(label);
			}
			columns = new WritableCellFormat(times10ptBoldUnderline);
			
			if (materiallist.size() > 0) {
				for (Materials materialsOrder : materiallist) {
					label = new Label(0, k, materialsOrder.getMaterialName(),columns);
					excelSheet.addCell(label);
					label = new Label(1, k, "" + materialsOrder.getMaterialType(),columns);
					excelSheet.addCell(label);
					label = new Label(2, k, "" + materialsOrder.getPrice(),columns);
					excelSheet.addCell(label);
					label = new Label(3, k, "" + materialsOrder.getUnit(),columns);
					excelSheet.addCell(label);
					k++;
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in creating material excel file ::"+e.getMessage());
		}
		return file.getAbsolutePath();
	}
		
	// generate Employee PDF file
	public String generateEntityFileTypePDFEmployee(HttpServletRequest request) {
		Document document = null;
		FileOutputStream fos = null;
		PdfWriter writer = null;
		File file = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		Font greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.GREEN);
		Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation1") + "/PDF";
			logger.debug("basepath : " + basePath+" realPath :: "+request.getServletContext().getRealPath("/"));
			String fileName = "employee" + String.valueOf(System.currentTimeMillis()).substring(6) + ".pdf";
			
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();
			else{
				File[] files = directory.listFiles();
				for(File f :files){
					f.delete();
				}
			}
			System.out.println("file path : " + directory.getAbsolutePath());
			file = new File(basePath + "/" + fileName);
			
			if(!file.exists()){
				file.createNewFile();
			}else{
				file.delete();
				file.createNewFile();
			}
			
			document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 10f);
			fos = new FileOutputStream(basePath+File.separator + fileName);
			PdfWriter.getInstance(document, fos);
			document.open();

			document.addAuthor("Ayushi");
			document.addCreator("Precise");
			document.addSubject("All Record of Employees");
			document.addCreationDate();
			document.addTitle("Employee Details");
			
            document.newPage();
              
			PdfPTable mainTable = new PdfPTable(1);
			mainTable.setWidthPercentage(90);
			PdfPCell[] cell = new PdfPCell[3];
			cell[0] = new PdfPCell(new Phrase(" "));
			cell[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);
			document.add(new Phrase("\n"));

			cell[2] = new PdfPCell(new Phrase(""));
			cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[2].setBorder(0);
			mainTable.addCell(cell[2]);
			document.add(mainTable);
			document.add(new Phrase("\n"));

			float[] tblwidthz = { .15f };
			mainTable = new PdfPTable(tblwidthz);
			mainTable.setWidthPercentage(100);
			cell = new PdfPCell[1];

			Image companyLogo = Image.getInstance(request.getServletContext().getRealPath("/")
					+"/resources/images/ebilling_logo.png");
			companyLogo.scalePercent(50);
			companyLogo.setBackgroundColor(BaseColor.CYAN);
			document.add(companyLogo);
			
			cell[0] = new PdfPCell(new Phrase("EMPLOYEE INFORMATION", catFont));
			cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);
			document.add(mainTable);
			document.add(new Phrase("\n"));

			float[] tblwidths = { .15f, .20f };

			mainTable = new PdfPTable(tblwidths);
			mainTable.setWidthPercentage(90);
			cell = new PdfPCell[2];

			cell[0] = new PdfPCell(new Phrase("Created By Admin", greenFont));
			cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);

			cell[1] = new PdfPCell(new Phrase("Created Date" + ": " + sdf.format(new Date()), greenFont));
			cell[1].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell[1].setBorder(0);
			mainTable.addCell(cell[1]);

			document.add(mainTable);
			document.add(new Phrase("\n"));

			List<Employee> employeelist = employeeService.listEmployees();
			
			int totalColumn = 7;
			float[] tblwidth = new float[totalColumn];
			
			tblwidth[0] = .10f;
			for (int i = 1; i < totalColumn-1; i++)
				tblwidth[i] = .10f;
			
			tblwidth[totalColumn-1] = .22f;
			
			PdfPTable headerTable = new PdfPTable(tblwidth);
			headerTable.setWidthPercentage(90);
			
			cell = new PdfPCell[totalColumn];
			// header
			cell[0] = new PdfPCell(new Phrase("Employee Id",subFont));
			cell[0].setBackgroundColor(BaseColor.GRAY);
			cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[0]);

			cell[1] = new PdfPCell(new Phrase("Employee Name",subFont));
			cell[1].setBackgroundColor(BaseColor.GRAY);
			cell[1].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[1]);

			cell[2] = new PdfPCell(new Phrase("Date Of Birth",subFont));
			cell[2].setBackgroundColor(BaseColor.GRAY);
			cell[2].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[2]);

			cell[3] = new PdfPCell(new Phrase("Contact No",subFont));
			cell[3].setBackgroundColor(BaseColor.GRAY);
			cell[3].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[3]);

			cell[4] = new PdfPCell(new Phrase("Employee Address",subFont));
			cell[4].setBackgroundColor(BaseColor.GRAY);
			cell[4].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[4]);
			document.add(headerTable);

			cell[5] = new PdfPCell(new Phrase("Email ID",subFont));
			cell[5].setBackgroundColor(BaseColor.GRAY);
			cell[5].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[5]);
			document.add(headerTable);
			
			cell[6] = new PdfPCell(new Phrase("Date of Joining",subFont));
			cell[6].setBackgroundColor(BaseColor.GRAY);
			cell[6].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[6]);
			document.add(headerTable);
			
			PdfPTable dbTable=null;
			if (employeelist.size() > 0) {
				int index = 0;
				for (Employee employeeOrder : employeelist) {
					index = 0;
					dbTable = new PdfPTable(tblwidth);
					dbTable.setWidthPercentage(90);
					cell = new PdfPCell[totalColumn];

					cell[index] = new PdfPCell(new Phrase("" + employeeOrder.getEmp_id()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(employeeOrder.getEmpname()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(""+employeeOrder.getDob()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(employeeOrder.getContact_no()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(employeeOrder.getAddress()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;
					document.add(dbTable);
					
					cell[index] = new PdfPCell(new Phrase(employeeOrder.getEmailid()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;
					document.add(dbTable);
					
					cell[index] = new PdfPCell(new Phrase("" +employeeOrder.getDoj()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;
					document.add(dbTable);
				}
			}else{
				logger.info("No record here");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in creating employee pdf file ::" + e.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		return file.getAbsolutePath();
	}
		
	// generate User PDF file
	public String generateEntityFileTypePDFUser(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		Document document = null;
		FileOutputStream fos = null;
		PdfWriter writer = null;
		File file = null;
		/*HttpSession session = request.getSession(false);
		System.out.println("UserStausAjax :: view User Details   ::: ruid " + (Integer) session.getAttribute("ruid"));
		if (session == null || session.getAttribute("ruid") == null) {
			throw new IllegalStateException("Invalid User.");
		}*/		
		
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	//	Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
		Font greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.GREEN);
		Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	//	Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation") + "/PDF";
			logger.debug("basepath : " + basePath+" realPath :: "+request.getServletContext().getRealPath("/"));
			String fileName = "user" + String.valueOf(System.currentTimeMillis()).substring(6) + ".pdf";
			
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();
			else{
				File[] files = directory.listFiles();
				for(File f :files){
					f.delete();
				}
			}
			
			file = new File(basePath + "/" + fileName);
			if(!file.exists()){
				file.createNewFile();
			}else{
				file.delete();
				file.createNewFile();
			}
			
			document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 10f);
			fos = new FileOutputStream(basePath+File.separator + fileName);
			PdfWriter.getInstance(document, fos);
			document.open();

			// create PDF Meta Data
			document.addAuthor("Kapil");
			document.addCreator("Precise");
			document.addSubject("All Record of Users");
			document.addCreationDate();
			document.addTitle("User Details");
			
            document.newPage();
              
			PdfPTable mainTable = new PdfPTable(1);
			mainTable.setWidthPercentage(90);
			PdfPCell[] cell = new PdfPCell[3];
			cell[0] = new PdfPCell(new Phrase(" "));
			cell[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);

			document.add(new Phrase("\n"));

			cell[2] = new PdfPCell(new Phrase(""));
			cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[2].setBorder(0);
			mainTable.addCell(cell[2]);

			document.add(mainTable);
			document.add(new Phrase("\n"));

			float[] tblwidthz = { .15f };
			mainTable = new PdfPTable(tblwidthz);
			mainTable.setWidthPercentage(100);
			cell = new PdfPCell[1];

			Image companyLogo = Image.getInstance(request.getServletContext().getRealPath("/")
					+"/resources/images/ebilling_logo.png");
		//	companyLogo.setAbsolutePosition(25, 700);
			companyLogo.scalePercent(50);
			companyLogo.setBackgroundColor(BaseColor.CYAN);
			document.add(companyLogo);
			
			cell[0] = new PdfPCell(new Phrase("USER INFORMATION", catFont));
			cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);
			
			
			/*cell[1]= new PdfPCell(companyLogo);
			cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[1].setBorder(0);
			mainTable.addCell(cell[1]);*/
			
			document.add(mainTable);

			document.add(new Phrase("\n"));

			float[] tblwidths = { .15f, .20f };

			mainTable = new PdfPTable(tblwidths);
			mainTable.setWidthPercentage(90);
			cell = new PdfPCell[2];

			cell[0] = new PdfPCell(new Phrase("Created By Admin", greenFont));
			cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);

			cell[1] = new PdfPCell(new Phrase("Created Date" + ": " + sdf.format(new Date()), greenFont));
			cell[1].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell[1].setBorder(0);
			mainTable.addCell(cell[1]);

			document.add(mainTable);
			document.add(new Phrase("\n"));

			int totalColumn = 5;
			float[] tblwidth = new float[totalColumn];
			
			tblwidth[0] = .10f;
			for (int i = 1; i < totalColumn-1; i++)
				tblwidth[i] = .10f;
			
			tblwidth[totalColumn-1] = .22f;
			
			PdfPTable headerTable = new PdfPTable(tblwidth);
			headerTable.setWidthPercentage(90);
			
			cell = new PdfPCell[totalColumn];
			// header
			cell[0] = new PdfPCell(new Phrase("User Id",subFont));
			cell[0].setBackgroundColor(BaseColor.GRAY);
			cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[0]);

			cell[1] = new PdfPCell(new Phrase("User Name",subFont));
			cell[1].setBackgroundColor(BaseColor.GRAY);
			cell[1].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[1]);

			cell[2] = new PdfPCell(new Phrase("Date Of Birth",subFont));
			cell[2].setBackgroundColor(BaseColor.GRAY);
			cell[2].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[2]);

			cell[3] = new PdfPCell(new Phrase("Contact No",subFont));
			cell[3].setBackgroundColor(BaseColor.GRAY);
			cell[3].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[3]);

			cell[4] = new PdfPCell(new Phrase("User Address",subFont));
			cell[4].setBackgroundColor(BaseColor.GRAY);
			cell[4].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[4]);
			document.add(headerTable);

			PdfPTable dbTable=null;
			List<Users> userlist = usersService.listUsers();
			
			if (userlist.size() > 0) {
				int index = 0;
				for (Users userOrder : userlist) {
					index = 0;
					dbTable = new PdfPTable(tblwidth);
					dbTable.setWidthPercentage(90);
					cell = new PdfPCell[totalColumn];

					cell[index] = new PdfPCell(new Phrase("" + userOrder.getId()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(userOrder.getUsername()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(""+userOrder.getDob()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(userOrder.getContact_no()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(userOrder.getAddress()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;
					document.add(dbTable);
				}
			}else{
				logger.info("No record of Users");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in creating USER PDF file ::" + e.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		return file.getAbsolutePath();
	}
		
	// generate Party PDF file
	public String generateEntityFileTypePDFParty(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Document document = null;
		FileOutputStream fos = null;
		PdfWriter writer = null;
		File file = null;
	
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		Font greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.GREEN);
		Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation2") + "/PDF";
			String fileName = "party" + String.valueOf(System.currentTimeMillis()).substring(6) + ".pdf";
			
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();
			else{
				File[] files = directory.listFiles();
				for(File f :files){
					f.delete();
				}
			}
			
			file = new File(basePath + "/" + fileName);
			
			if(!file.exists()){
				file.createNewFile();
			}else{
				file.delete();
				file.createNewFile();
			}
			
			document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 10f);
			fos = new FileOutputStream(basePath+File.separator + fileName);
			PdfWriter.getInstance(document, fos);
			document.open();

			document.addAuthor("Ayushi");
			document.addCreator("Precise");
			document.addSubject("All Record of Users");
			document.addCreationDate();
			document.addTitle("Party Details");
			
            document.newPage();
              
			PdfPTable mainTable = new PdfPTable(1);
			mainTable.setWidthPercentage(90);
			PdfPCell[] cell = new PdfPCell[3];
			cell[0] = new PdfPCell(new Phrase(" "));
			cell[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);
			document.add(new Phrase("\n"));

			cell[2] = new PdfPCell(new Phrase(""));
			cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[2].setBorder(0);
			mainTable.addCell(cell[2]);
			document.add(mainTable);
			document.add(new Phrase("\n"));

			float[] tblwidthz = { .15f };
			mainTable = new PdfPTable(tblwidthz);
			mainTable.setWidthPercentage(100);
			cell = new PdfPCell[1];

			Image companyLogo = Image.getInstance(request.getServletContext().getRealPath("/")
					+"/resources/images/ebilling_logo.png");
			companyLogo.scalePercent(50);
			companyLogo.setBackgroundColor(BaseColor.CYAN);
			document.add(companyLogo);
			
			cell[0] = new PdfPCell(new Phrase("PARTY INFORMATION", catFont));
			cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);
			document.add(mainTable);
			document.add(new Phrase("\n"));

			float[] tblwidths = { .15f, .20f };

			mainTable = new PdfPTable(tblwidths);
			mainTable.setWidthPercentage(90);
			cell = new PdfPCell[2];

			cell[0] = new PdfPCell(new Phrase("Created By Admin", greenFont));
			cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);

			cell[1] = new PdfPCell(new Phrase("Created Date" + ": " + sdf.format(new Date()), greenFont));
			cell[1].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell[1].setBorder(0);
			mainTable.addCell(cell[1]);
			document.add(mainTable);
			document.add(new Phrase("\n"));

			int totalColumn = 5;
			float[] tblwidth = new float[totalColumn];
			tblwidth[0] = .10f;
			for (int i = 1; i < totalColumn-1; i++)
				tblwidth[i] = .10f;
			
			tblwidth[totalColumn-1] = .22f;
			
			PdfPTable headerTable = new PdfPTable(tblwidth);
			headerTable.setWidthPercentage(90);
			
			cell = new PdfPCell[totalColumn];
			cell[0] = new PdfPCell(new Phrase("Party Id",subFont));
			cell[0].setBackgroundColor(BaseColor.GRAY);
			cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[0]);

			cell[1] = new PdfPCell(new Phrase("Party Name",subFont));
			cell[1].setBackgroundColor(BaseColor.GRAY);
			cell[1].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[1]);

			cell[2] = new PdfPCell(new Phrase("TIN Number",subFont));
			cell[2].setBackgroundColor(BaseColor.GRAY);
			cell[2].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[2]);

			cell[3] = new PdfPCell(new Phrase("Contact No",subFont));
			cell[3].setBackgroundColor(BaseColor.GRAY);
			cell[3].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[3]);

			cell[4] = new PdfPCell(new Phrase("Party Address",subFont));
			cell[4].setBackgroundColor(BaseColor.GRAY);
			cell[4].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[4]);
			document.add(headerTable);

			PdfPTable dbTable=null;
			List<Party> partylist  = partyService.listParty();
			if (partylist.size() > 0) {
				int index = 0;
				for (Party partyOrder : partylist) {
					index = 0;
					dbTable = new PdfPTable(tblwidth);
					dbTable.setWidthPercentage(90);
					cell = new PdfPCell[totalColumn];

					cell[index] = new PdfPCell(new Phrase("" + partyOrder.getParty_id()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(partyOrder.getName()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(""+partyOrder.getTin_number()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(partyOrder.getContact_no()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(partyOrder.getAddress()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;
					document.add(dbTable);
				}
			}else{
				logger.info("No record Of Party");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in creating Party PDF file ::" + e.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		return file.getAbsolutePath();
		
	}
		
	// generate Material PDF file
	public String generateEntityFileTypePDFMaterial(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Document document = null;
		FileOutputStream fos = null;
		PdfWriter writer = null;
		File file = null;
		
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		Font greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.GREEN);
		Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation3") + "/PDF";
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();
			else{
				File[] files = directory.listFiles();
				for(File f :files){
					f.delete();
				}
			}
			
			String fileName = "material" + String.valueOf(System.currentTimeMillis()).substring(6) + ".pdf";
			file = new File(basePath + "/" + fileName);
			if(!file.exists()){
				file.createNewFile();
			}else{
				file.delete();
				file.createNewFile();
			}
			
			document = new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 10f);
			fos = new FileOutputStream(basePath+File.separator + fileName);
			PdfWriter.getInstance(document, fos);
			document.open();

			document.addAuthor("Ayushi");
			document.addCreator("Precise");
			document.addSubject("All Record of Material");
			document.addCreationDate();
			document.addTitle("Material Details");
			
            document.newPage();
              
			PdfPTable mainTable = new PdfPTable(1);
			mainTable.setWidthPercentage(90);
			PdfPCell[] cell = new PdfPCell[3];
			cell[0] = new PdfPCell(new Phrase(" "));
			cell[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);
			document.add(new Phrase("\n"));

			cell[2] = new PdfPCell(new Phrase(""));
			cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[2].setBorder(0);
			mainTable.addCell(cell[2]);
			document.add(mainTable);
			document.add(new Phrase("\n"));

			float[] tblwidthz = { .15f };
			mainTable = new PdfPTable(tblwidthz);
			mainTable.setWidthPercentage(100);
			cell = new PdfPCell[1];

			Image companyLogo = Image.getInstance(request.getServletContext().getRealPath("/")
					+"/resources/images/ebilling_logo.png");
			companyLogo.scalePercent(50);
			companyLogo.setBackgroundColor(BaseColor.CYAN);
			document.add(companyLogo);
			
			cell[0] = new PdfPCell(new Phrase("Material INFORMATION", catFont));
			cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);
			document.add(mainTable);
			document.add(new Phrase("\n"));

			float[] tblwidths = { .15f, .20f };
			mainTable = new PdfPTable(tblwidths);
			mainTable.setWidthPercentage(90);
			cell = new PdfPCell[2];

			cell[0] = new PdfPCell(new Phrase("Created By Admin", greenFont));
			cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell[0].setBorder(0);
			mainTable.addCell(cell[0]);

			cell[1] = new PdfPCell(new Phrase("Created Date" + ": " + sdf.format(new Date()), greenFont));
			cell[1].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell[1].setBorder(0);
			mainTable.addCell(cell[1]);
			document.add(mainTable);
			document.add(new Phrase("\n"));

			int totalColumn = 5;
			float[] tblwidth = new float[totalColumn];
			tblwidth[0] = .10f;
			for (int i = 1; i < totalColumn-1; i++)
				tblwidth[i] = .10f;
			
			tblwidth[totalColumn-1] = .22f;
			
			PdfPTable headerTable = new PdfPTable(tblwidth);
			headerTable.setWidthPercentage(90);
			cell = new PdfPCell[totalColumn];
			
			cell[0] = new PdfPCell(new Phrase("Material Id",subFont));
			cell[0].setBackgroundColor(BaseColor.GRAY);
			cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[0]);

			cell[1] = new PdfPCell(new Phrase("Material Name",subFont));
			cell[1].setBackgroundColor(BaseColor.GRAY);
			cell[1].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[1]);

			cell[2] = new PdfPCell(new Phrase("Material Type",subFont));
			cell[2].setBackgroundColor(BaseColor.GRAY);
			cell[2].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[2]);

			cell[3] = new PdfPCell(new Phrase("Material Price",subFont));
			cell[3].setBackgroundColor(BaseColor.GRAY);
			cell[3].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[3]);

			cell[4] = new PdfPCell(new Phrase("Material Unit",subFont));
			cell[4].setBackgroundColor(BaseColor.GRAY);
			cell[4].setHorizontalAlignment(Element.ALIGN_LEFT);
			headerTable.addCell(cell[4]);
			document.add(headerTable);

			PdfPTable dbTable=null;
			List<Materials> materiallist = materialService.listMaterial();
			if (materiallist.size() > 0) {
				int index = 0;
				for (Materials materialOrder : materiallist) {
					index = 0;
					dbTable = new PdfPTable(tblwidth);
					dbTable.setWidthPercentage(90);
					cell = new PdfPCell[totalColumn];

					cell[index] = new PdfPCell(new Phrase("" + materialOrder.getId()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(materialOrder.getMaterialName()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(""+materialOrder.getMaterialType()));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(String.valueOf(materialOrder.getPrice())));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;

					cell[index] = new PdfPCell(new Phrase(String.valueOf(materialOrder.getUnit())));
					cell[index].setHorizontalAlignment(Element.ALIGN_LEFT);
					dbTable.addCell(cell[index]);
					index++;
					document.add(dbTable);
				}
			}else{
				logger.info("No record Of Material");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in creating Material PDF file ::" + e.getMessage());
		} finally {
			if (document != null && document.isOpen())
				document.close();
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		return file.getAbsolutePath();
		
	}
	
	// generate Employee CSV file
	public String generateEntityFileTypeCSVEmployee(HttpServletRequest request){
		FileWriter fileWriter = null;
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String filePath = null;
		String FILE_HEADER = "EmployeeId,EmployeeName,Date Of Birth,Contact No., Employee Address,Email ID, Date of Joining";
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation1") + "/CSV";
			logger.debug("basepath : " + basePath);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();
			
			String fileName = "employee" + String.valueOf(System.currentTimeMillis()).substring(6) + ".csv";
			filePath = basePath + "/" + fileName;
			
			fileWriter = new FileWriter(filePath);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Employee INFORMATION");
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append("Created By Admin");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Created Date" + ": " + sdf.format(new Date()));
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			List<Employee> employeelist  = employeeService.listEmployees();
			for (Employee student : employeelist) {
				fileWriter.append(String.valueOf(student.getEmp_id()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getEmpname());
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append(sdf.format(student.getDob()));
				System.out.println("dob :"+sdf.format(student.getDob()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getContact_no()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getAddress()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getEmailid()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(sdf.format(student.getDoj()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			logger.info("Employee CSV file was created successfully");
		} catch (Exception e) {
			logger.error("Error in creating Employee CSV File.");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				logger.error("Error while flushing/closing fileWriter during creating Employee CSV File.");
				e.printStackTrace();
			}
		}
		return filePath;
	}
	
	// generate User CSV file
	public String generateEntityFileTypeCSVUser(HttpServletRequest request){
		FileWriter fileWriter = null;
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String filePath = null;
		String FILE_HEADER = "UserId,UsertName,Date Of Birth,Contact No., User Address";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation") + "/CSV";
			String fileName = "user" + String.valueOf(System.currentTimeMillis()).substring(6) + ".csv";
			
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();
			
			filePath = basePath + "/" + fileName;
			
			fileWriter = new FileWriter(filePath);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("USER INFORMATION");
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append("Created By Admin");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Created Date" + ": " + sdf.format(new Date()));
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			List<Users> userlist = usersService.listUsers();
			for (Users student : userlist) {
				fileWriter.append(String.valueOf(student.getId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getUsername());
				fileWriter.append(COMMA_DELIMITER);
				
				fileWriter.append(sdf.format(student.getDob()));
				System.out.println("dob :"+sdf.format(student.getDob()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getContact_no()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getAddress()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			logger.info("User CSV file was created successfully");
		} catch (Exception e) {
			logger.error("Error in creating User CSV File");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				logger.error("Error while flushing/closing fileWriter during creating User CSV File");
				e.printStackTrace();
			}
		}
		return filePath;
	}

	// generate Party CSV file
	public String generateEntityFileTypeCSVParty(HttpServletRequest request){
		FileWriter fileWriter = null;
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String filePath = null;
		String FILE_HEADER = "PartyId,PartyName,TinNumber,Contact No.,Party Address";
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation2") + "/CSV";
			String fileName = "party" + String.valueOf(System.currentTimeMillis()).substring(6) + ".csv";
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();
			
			filePath = basePath + "/" + fileName;
			
			fileWriter = new FileWriter(filePath);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("PARTY INFORMATION");
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append("Created By Admin");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(NEW_LINE_SEPARATOR);
			//
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			List<Party> partylist = partyService.listParty();
			for (Party student : partylist) {
				fileWriter.append(String.valueOf(student.getParty_id()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getTin_number());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getContact_no()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getAddress()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			logger.info("Party CSV file was created successfully");
		} catch (Exception e) {
			logger.error("Error in creating Party CSV File");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				logger.error("Error while flushing/closing fileWriter during creating Party CSV File");
				e.printStackTrace();
			}
		}
		return filePath;
	}

	// generate Material CSV file
	public String generateEntityFileTypeCSVMaterial(HttpServletRequest request){
		FileWriter fileWriter = null;
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String filePath = null;
		String FILE_HEADER = "MaterialId,MaterialName,MaterialType,MaterialPrice,MaterialUnit";
		try {
			String basePath = request.getSession().getServletContext().getInitParameter("saveFileLocation3") + "/CSV";
			String fileName = "material" + String.valueOf(System.currentTimeMillis()).substring(6) + ".csv";
			File directory = new File(basePath);
			if (!directory.exists())
				directory.mkdirs();
			
			filePath = basePath + "/" + fileName;
			fileWriter = new FileWriter(filePath);
			
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Material INFORMATION");
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append("Created By Admin");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("");
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			List<Materials> materiallist  = materialService.listMaterial();
			for (Materials student :materiallist) {
				fileWriter.append(String.valueOf(student.getId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getMaterialName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(student.getMaterialType());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getPrice()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(student.getUnit()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			logger.info("Material CSV file was created successfully");
		} catch (Exception e) {
			logger.error("Error in creating Material CSV File");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				logger.error("Error while flushing/closing fileWriter during creating Material CSV File");
				e.printStackTrace();
			}
		}
		return filePath;
	}

}